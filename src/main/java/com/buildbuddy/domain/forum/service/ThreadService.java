package com.buildbuddy.domain.forum.service;

import com.buildbuddy.audit.AuditorAwareImpl;
import com.buildbuddy.domain.forum.dto.param.ThreadRequestParam;
import com.buildbuddy.domain.forum.dto.request.ThreadLikeReqDto;
import com.buildbuddy.domain.forum.dto.request.ThreadRequestDto;
import com.buildbuddy.domain.forum.dto.response.thread.ThreadResponseDto;
import com.buildbuddy.domain.forum.dto.response.thread.ThreadResponseSchema;
import com.buildbuddy.domain.forum.entity.ThreadEntity;
import com.buildbuddy.domain.forum.entity.ThreadLikeEntity;
import com.buildbuddy.domain.forum.entity.ThreadModel;
import com.buildbuddy.domain.forum.repository.ThreadLikeRepository;
import com.buildbuddy.domain.forum.repository.ThreadRepository;
import com.buildbuddy.domain.user.entity.UserEntity;
import com.buildbuddy.exception.BadRequestException;
import com.buildbuddy.jsonresponse.DataResponse;
import com.buildbuddy.util.PaginationCreator;
import com.buildbuddy.util.spesification.ParamFilter;
import com.buildbuddy.util.spesification.SpecificationCreator;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ThreadService {

    @Autowired
    private AuditorAwareImpl audit;

    @Autowired
    private ThreadRepository threadRepository;

    @Autowired
    private ThreadLikeRepository threadLikeRepository;

    @Autowired
    private PaginationCreator paginationCreator;

    public DataResponse<ThreadResponseSchema> get(ThreadRequestParam requestParam){

        UserEntity user = audit.getCurrentAuditor().orElseThrow(() -> new RuntimeException("User Not Found"));

        boolean isPaginated = requestParam.isPagination();
        Integer pageNo = requestParam.getPageNo();
        Integer pageSize = requestParam.getPageSize();
        String sortBy = requestParam.getSortBy();
        String sortDirection = requestParam.getSortDirection();
        String search = requestParam.getSearch() != null ? "%" + requestParam.getSearch() + "%" : null;

        Sort sort = paginationCreator.createAliasesSort(sortDirection, sortBy);

        Pageable pageable = paginationCreator.createPageable(isPaginated, sort, pageNo, pageSize);

        Page<ThreadModel> dataPage = threadRepository.getByCustomParam(user.getId(),requestParam.getThreadId(), search, pageable);
        List<ThreadModel> threadList = dataPage.getContent();

        List<ThreadResponseDto> threadResponseDtos = threadList.stream()
                .map(ThreadResponseDto::convertToDto)
                .toList();

        ThreadResponseSchema data = ThreadResponseSchema.builder()
                .threadList(threadResponseDtos)
                .pageNo(pageNo)
                .pageSize(pageSize)
                .totalPages(dataPage.getTotalPages())
                .totalData(dataPage.getTotalElements())
                .build();

        return DataResponse.<ThreadResponseSchema>builder()
                .timestamp(LocalDateTime.now())
                .httpStatus(HttpStatus.OK)
                .message("Success getting thread")
                .data(data)
                .build();
    }

    @Transactional
    public DataResponse<ThreadResponseDto> save(ThreadRequestDto threadDto){
        log.info("thread: {}", threadDto);

        Integer id = threadDto.getId();
        ThreadEntity thread = null;

        if(id != null){
            UserEntity currentUser = audit.getCurrentAuditor().orElseThrow(() ->  new BadRequestException("Request not authenticated"));
            log.info("current authenticated user: {}", currentUser.getUsername());

            thread = threadRepository.findByIdAndUserId(id, currentUser.getId()).orElseThrow(() -> new BadRequestException("Post Not Found"));
            thread.setPost(threadDto.getPost());
        }
        else{
            thread = ThreadRequestDto.convertToEntity(threadDto);
        }

        log.info("saving...");
        thread = threadRepository.saveAndFlush(thread);
        log.info("saved...");

        ThreadResponseDto data = ThreadResponseDto.convertToDto(thread);

        return DataResponse.<ThreadResponseDto>builder()
                .timestamp(LocalDateTime.now())
                .httpStatus(HttpStatus.OK)
                .message("Success saving thread")
                .data(data)
                .build();
    }

    @Transactional
    public DataResponse<String> delete(Integer threadId){
        log.info("deleting thread with id: {}", threadId);
        UserEntity currentUser = audit.getCurrentAuditor().orElseThrow(() ->  new BadRequestException("Request not authenticated"));
        log.info("current authenticated user: {}", currentUser.getUsername());

        ThreadEntity thread = threadRepository.findByIdAndUserId(threadId, currentUser.getId()).orElseThrow(() -> new BadRequestException("Post Not Found"));

        log.info("deleting...");
        threadRepository.delete(thread);
        log.info("deleted.");

        return DataResponse.<String>builder()
                .timestamp(LocalDateTime.now())
                .httpStatus(HttpStatus.OK)
                .message("Success deleting thread")
                .data("Thread with id: " + threadId + " deleted.")
                .build();
    }

    @Transactional
    public DataResponse<Object> like(ThreadLikeReqDto dto){

        UserEntity user = audit.getCurrentAuditor().orElseThrow(() -> new RuntimeException("User Not Found"));

        String message = "";

        threadRepository.findById(dto.getThreadId()).orElseThrow(() -> new RuntimeException("Thread not found"));
        Optional<ThreadLikeEntity> threadLikeOpt = threadLikeRepository.findByThreadIdAndUserId(dto.getThreadId(), user.getId());

        if(threadLikeOpt.isPresent()){
            log.info("Unliking forum");

            threadLikeRepository.delete(threadLikeOpt.get());

            message = "Forum Unliked.";
        }
        else{
            log.info("liking forum");
            ThreadLikeEntity entity = ThreadLikeEntity.builder()
                    .threadId(dto.getThreadId())
                    .userId(user.getId())
                    .build();
            threadLikeRepository.saveAndFlush(entity);
            message = "Forum liked.";
        }

        log.info("success....");

        return DataResponse.<Object>builder()
                .timestamp(LocalDateTime.now())
                .httpStatus(HttpStatus.OK)
                .message(message)
                .build();
    }

}
