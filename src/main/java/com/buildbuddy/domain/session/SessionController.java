package com.buildbuddy.domain.session;

import com.buildbuddy.audit.AuditorAwareImpl;
import com.buildbuddy.domain.forum.dto.response.comment.CommentResponseSchema;
import com.buildbuddy.domain.user.dto.response.UserResponseDto;
import com.buildbuddy.domain.user.entity.UserEntity;
import com.buildbuddy.exception.BadRequestException;
import com.buildbuddy.jsonresponse.DataResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/session")
public class SessionController {

    @Autowired
    private AuditorAwareImpl audit;

    @GetMapping("/check")
    public ResponseEntity<Object> checkSession(HttpServletRequest request){
        log.info("Received Request on {} - {}", request.getServletPath(), request.getMethod());

        UserEntity currentUser = audit.getCurrentAuditor().orElseThrow(() -> new BadRequestException("Request not authenticated"));

        UserResponseDto data = UserResponseDto.convertToDto(currentUser);

        DataResponse<UserResponseDto> response = DataResponse.<UserResponseDto>builder()
                .timestamp(LocalDateTime.now())
                .httpStatus(HttpStatus.OK)
                .data(data)
                .message("Session valid")
                .build();

        log.info("Success Executing Request on {}", request.getServletPath());
        return new ResponseEntity<>(response, response.getHttpStatus());
    }
}
