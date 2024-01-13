package com.buildbuddy.security;

import com.buildbuddy.jsonresponse.DataResponse;
import com.buildbuddy.jsonresponse.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class CustomAuthenticationEntrypoint implements AuthenticationEntryPoint {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        DataResponse<String> dataResponse = DataResponse.<String>builder()
                .timestamp(LocalDateTime.now())
                .httpStatus(HttpStatus.UNAUTHORIZED)
                .message("UNAUTHORIZED")
                .error(ErrorResponse.builder()
                        .errorMessage(authException.getMessage())
                        .exceptionClass(authException.getClass().toString())
                        .build())
                .build();


        response.getWriter().write(objectMapper.writeValueAsString(dataResponse));

    }
}
