package com.buildbuddy.domain.forum.service;

import com.buildbuddy.audit.AuditorAwareImpl;
import com.buildbuddy.domain.forum.dto.request.CommentRequestDto;
import com.buildbuddy.domain.forum.dto.response.comment.CommentResponseDto;
import com.buildbuddy.domain.forum.entity.CommentEntity;
import com.buildbuddy.domain.forum.entity.ThreadEntity;
import com.buildbuddy.domain.forum.repository.CommentRepository;
import com.buildbuddy.domain.forum.repository.ThreadRepository;
import com.buildbuddy.domain.user.entity.UserEntity;
import com.buildbuddy.exception.BadRequestException;
import com.buildbuddy.jsonresponse.DataResponse;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class CommentService {

    @Autowired
    private AuditorAwareImpl audit;

    @Autowired
    private ThreadRepository threadRepository;

    @Autowired
    private CommentRepository commentRepository;

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
