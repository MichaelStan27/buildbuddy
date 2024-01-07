package com.buildbuddy.domain.forum.service;

import com.buildbuddy.audit.AuditorAwareImpl;
import com.buildbuddy.domain.forum.dto.request.ThreadRequestDto;
import com.buildbuddy.domain.forum.dto.response.ThreadResponseDto;
import com.buildbuddy.domain.forum.entity.ThreadEntity;
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
public class ThreadService {

    @Autowired
    private AuditorAwareImpl audit;

    @Autowired
    private ThreadRepository threadRepository;

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
        threadRepository.saveAndFlush(thread);
        log.info("saved...");

        ThreadResponseDto data = ThreadResponseDto.builder()
                .post(thread.getPost())
                .username(thread.getUser().getUsername())
                .createdTime(thread.getCreatedTime())
                .lastUpdateTime(thread.getLastUpdateTime())
                .build();

        return DataResponse.<ThreadResponseDto>builder()
                .timestamp(LocalDateTime.now())
                .httpStatus(HttpStatus.CREATED)
                .message("Success saving thread")
                .data(data)
                .build();
    }

}
