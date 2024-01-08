package com.buildbuddy.domain.forum.service;

import com.buildbuddy.audit.AuditorAwareImpl;
import com.buildbuddy.domain.forum.dto.param.CommentRequestParam;
import com.buildbuddy.domain.forum.dto.param.ThreadRequestParam;
import com.buildbuddy.domain.forum.dto.request.CommentRequestDto;
import com.buildbuddy.domain.forum.dto.response.comment.CommentResponseDto;
import com.buildbuddy.domain.forum.dto.response.comment.CommentResponseSchema;
import com.buildbuddy.domain.forum.entity.CommentEntity;
import com.buildbuddy.domain.forum.entity.ThreadEntity;
import com.buildbuddy.domain.forum.repository.CommentRepository;
import com.buildbuddy.domain.forum.repository.ThreadRepository;
import com.buildbuddy.domain.user.entity.UserEntity;
import com.buildbuddy.exception.BadRequestException;
import com.buildbuddy.jsonresponse.DataResponse;
import com.buildbuddy.util.spesification.ParamFilter;
import com.buildbuddy.util.spesification.SpecificationCreator;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CommentService {

    @Autowired
    private AuditorAwareImpl audit;

    @Autowired
    private ThreadRepository threadRepository;

    @Autowired
    private SpecificationCreator<CommentEntity> specificationCreator;

    @Autowired
    private CommentRepository commentRepository;

    public DataResponse<CommentResponseSchema> get(CommentRequestParam requestParam){
        log.info("param: {}", requestParam);

        boolean isPaginated = requestParam.isPagination();
        Integer pageNo = requestParam.getPageNo();
        Integer pageSize = requestParam.getPageSize();
        String sortBy = requestParam.getSortBy();
        String sortDirection = requestParam.getSortDirection();

        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);

        Pageable pageable = (isPaginated) ? PageRequest.of(pageNo, pageSize, sort) : PageRequest.of(0, Integer.MAX_VALUE, sort);

        Page<CommentEntity> dataPage = getCommentFromDB(requestParam, pageable);
        List<CommentEntity> commentList = dataPage.getContent();

        List<CommentResponseDto> dtoList = commentList.stream()
                .map(CommentResponseDto::convertToDto)
                .toList();

        CommentResponseSchema data = CommentResponseSchema.builder()
                .commentList(dtoList)
                .pageNo(pageNo)
                .pageSize(pageSize)
                .totalPages(dataPage.getTotalPages())
                .totalData(dataPage.getTotalElements())
                .build();

        return DataResponse.<CommentResponseSchema>builder()
                .httpStatus(HttpStatus.OK)
                .timestamp(LocalDateTime.now())
                .message("Success getting comment")
                .data(data)
                .build();
    }

    private Page<CommentEntity> getCommentFromDB(CommentRequestParam param, Pageable pageable){
        log.info("Getting comment from DB...");

        List<ParamFilter> paramFilters = param.getFilters();
        Page<CommentEntity> data = null;

        if(paramFilters.isEmpty())
            data = commentRepository.findAll(pageable);
        else
            data = commentRepository.findAll(specificationCreator.getSpecification(paramFilters), pageable);

        return data;
    }

    @Transactional
    public DataResponse<CommentResponseDto> save(CommentRequestDto dto){
        log.info("comment: {}", dto);

        ThreadEntity thread = threadRepository.findById(dto.getThreadId()).orElseThrow(() -> new BadRequestException("thread not found"));
        CommentEntity comment = null;

        if(dto.getCommentId() != null){
            UserEntity currentUser = audit.getCurrentAuditor().orElseThrow(() -> new BadRequestException("Request not authenticated"));
            log.info("authenticated user: {}", currentUser.getUsername());
            comment = commentRepository.findByIdAndUserIdAndThreadId(dto.getCommentId(), currentUser.getId(), thread.getId()).orElseThrow(() -> new BadRequestException("Comment Not Found"));
            comment.setMessage(dto.getMessage());
        }
        else{
            comment = CommentRequestDto.convertToEntity(dto, thread);
        }

        log.info("saving...");
        comment = commentRepository.saveAndFlush(comment);
        log.info("saved.");

        CommentResponseDto data = CommentResponseDto.convertToDto(comment);

        return DataResponse.<CommentResponseDto>builder()
                .httpStatus(HttpStatus.OK)
                .timestamp(LocalDateTime.now())
                .message("Success saving comment")
                .data(data)
                .build();
    }

    @Transactional
    public DataResponse<String> delete(Integer threadId, Integer commentId){
        log.info("Deleting comment by: comment id: {}, thread id: {}", commentId, threadId);

        UserEntity currentUser = audit.getCurrentAuditor().orElseThrow(() -> new BadRequestException("Request not authenticated"));

        CommentEntity comment = commentRepository.findByIdAndUserIdAndThreadId(commentId, currentUser.getId(), threadId).orElseThrow(() -> new BadRequestException("Comment Not Found"));

        log.info("Deleting...");
        commentRepository.delete(comment);
        log.info("Deleted.");

        return DataResponse.<String>builder()
                .httpStatus(HttpStatus.OK)
                .timestamp(LocalDateTime.now())
                .message("Success Deleting Comment")
                .data("Comment with id: " + commentId + " at thread id: " + threadId + " deleted.")
                .build();
    }

}
