package com.buildbuddy.domain.forum.controller;

import com.buildbuddy.domain.forum.dto.request.CommentRequestDto;
import com.buildbuddy.domain.forum.dto.response.comment.CommentResponseDto;
import com.buildbuddy.domain.forum.service.CommentService;
import com.buildbuddy.jsonresponse.DataResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/save")
    public ResponseEntity<Object> saveComment(@RequestBody CommentRequestDto body,
            HttpServletRequest request) {
        log.info("Received Request on {} - {}", request.getServletPath(), request.getMethod());

        DataResponse<CommentResponseDto> response = commentService.save(body);

        log.info("Success Executing Request on {}", request.getServletPath());
        return new ResponseEntity<>(response, response.getHttpStatus());
    }
}
