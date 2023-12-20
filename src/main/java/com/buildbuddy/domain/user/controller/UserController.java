package com.buildbuddy.domain.user.controller;

import com.buildbuddy.domain.user.dto.request.UserRequestDto;
import com.buildbuddy.domain.user.dto.response.UserResponseDto;
import com.buildbuddy.domain.user.service.UserService;
import com.buildbuddy.jsonresponse.DataResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/get-by-username")
    public ResponseEntity<Object> getUserByUsername(@RequestParam String username,
                                            HttpServletRequest request){
        log.info("Received Request on {} - {}", request.getServletPath(), request.getMethod());
        log.info("param: {}", username);

        DataResponse<UserResponseDto> response = userService.getUserByUsername(username);

        log.info("Success Executing Request on {}", request.getServletPath());
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody UserRequestDto userDto,
            HttpServletRequest request){
        log.info("Received Request on {} - {}", request.getServletPath(), request.getMethod());
        log.info("param: {}", userDto.getUsername());

        DataResponse<UserResponseDto> response = userService.createUser(userDto);

        log.info("Success Executing Request on {}", request.getServletPath());
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

}
