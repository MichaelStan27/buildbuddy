package com.buildbuddy.exception;

import com.buildbuddy.jsonresponse.DataResponse;
import com.buildbuddy.jsonresponse.ErrorResponse;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> handleException(Exception e){

        log.error("EXCEPTION - {}", e.getMessage(), e);

        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        if(e instanceof BadRequestException || e instanceof UsernameNotFoundException || e instanceof BadCredentialsException)
            httpStatus = HttpStatus.BAD_REQUEST;

        ErrorResponse error = ErrorResponse.builder()
                .exceptionClass(e.getClass().toString())
                .errorMessage(e.getMessage())
                .build();

        DataResponse<Object> response = DataResponse.<Object>builder()
                .timestamp(LocalDateTime.now())
                .httpStatus(httpStatus)
                .error(error)
                .message("There is an error")
                .build();

        return new ResponseEntity<>(response, response.getHttpStatus());
    }

}
