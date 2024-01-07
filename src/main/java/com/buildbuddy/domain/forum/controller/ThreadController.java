package com.buildbuddy.domain.forum.controller;

import com.buildbuddy.domain.forum.dto.param.ThreadRequestParam;
import com.buildbuddy.domain.forum.dto.request.ThreadRequestDto;
import com.buildbuddy.domain.forum.dto.response.ThreadResponseDto;
import com.buildbuddy.domain.forum.dto.response.ThreadResponseSchema;
import com.buildbuddy.domain.forum.service.ThreadService;
import com.buildbuddy.jsonresponse.DataResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/thread")
public class ThreadController {

    @Autowired
    private ThreadService threadService;

    @GetMapping("/get")
    public ResponseEntity<Object> getThread(ThreadRequestParam param,
            HttpServletRequest request){
        log.info("Received Request on {} - {}", request.getServletPath(), request.getMethod());
        log.info("param: {}", param);

        DataResponse<ThreadResponseSchema> response = threadService.get(param);

        log.info("Success Executing Request on {}", request.getServletPath());
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @PostMapping("/save")
    public ResponseEntity<Object> saveThread(@RequestBody ThreadRequestDto body,
            HttpServletRequest request){
        log.info("Received Request on {} - {}", request.getServletPath(), request.getMethod());

        DataResponse<ThreadResponseDto> response = threadService.save(body);

        log.info("Success Executing Request on {}", request.getServletPath());
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> deleteThread(@RequestParam("threadId") Integer threadId,
            HttpServletRequest request){
        log.info("Received Request on {} - {}", request.getServletPath(), request.getMethod());

        DataResponse<String> response = threadService.delete(threadId);

        log.info("Success Executing Request on {}", request.getServletPath());
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

}
