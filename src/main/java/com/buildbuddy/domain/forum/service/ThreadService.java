package com.buildbuddy.domain.forum.service;

import com.buildbuddy.audit.AuditorAwareImpl;
import com.buildbuddy.domain.forum.dto.param.ThreadRequestParam;
import com.buildbuddy.domain.forum.dto.request.ThreadRequestDto;
import com.buildbuddy.domain.forum.dto.response.thread.ThreadResponseDto;
import com.buildbuddy.domain.forum.dto.response.thread.ThreadResponseSchema;
import com.buildbuddy.domain.forum.entity.ThreadEntity;
import com.buildbuddy.domain.forum.entity.ThreadModel;
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

@Slf4j
@Service
public class ThreadService {

    @Autowired
    private AuditorAwareImpl audit;

    @Autowired
    private SpecificationCreator<ThreadEntity> specificationCreator;

    @Autowired
    private ThreadRepository threadRepository;

    @Autowired
    private PaginationCreator paginationCreator;

    public DataResponse<ThreadResponseSchema> get(ThreadRequestParam requestParam){

        boolean isPaginated = requestParam.isPagination();
        Integer pageNo = requestParam.getPageNo();
        Integer pageSize = requestParam.getPageSize();
        String sortBy = requestParam.getSortBy();
        String sortDirection = requestParam.getSortDirection();
        String search = requestParam.getSearch() != null ? "%" + requestParam.getSearch() + "%" : null;

        Sort sort = paginationCreator.createAliasesSort(sortDirection, sortBy);

        Pageable pageable = paginationCreator.createPageable(isPaginated, sort, pageNo, pageSize);

        Page<ThreadModel> dataPage = threadRepository.getByCustomParam(requestParam.getThreadId(), search, pageable);
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

}
