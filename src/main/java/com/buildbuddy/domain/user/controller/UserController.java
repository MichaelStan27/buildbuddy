package com.buildbuddy.domain.user.controller;

import com.buildbuddy.domain.user.dto.BalanceTransactionReqParam;
import com.buildbuddy.domain.user.dto.request.UserRequestDto;
import com.buildbuddy.domain.user.dto.response.UserResponseDto;
import com.buildbuddy.domain.user.service.UserService;
import com.buildbuddy.jsonresponse.DataResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

//    @PreAuthorize("hasAuthority('admin')")
    @GetMapping(value = "/get-by-username")
    public ResponseEntity<Object> getUserByUsername(@RequestParam String username,
                                            HttpServletRequest request){
        log.info("Received Request on {} - {}", request.getServletPath(), request.getMethod());
        log.info("param: {}", username);

        DataResponse<UserResponseDto> response = userService.getUserByUsername(username);

        log.info("Success Executing Request on {}", request.getServletPath());
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @GetMapping("/get-detail")
    public ResponseEntity<Object> getUser(@RequestParam Integer userId, HttpServletRequest request){
        log.info("Received Request on {} - {}", request.getServletPath(), request.getMethod());

        DataResponse<Object> response = userService.getUser(userId);

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

    @GetMapping("/get-balance-transaction")
    public ResponseEntity<Object> getBalanceTransaction(BalanceTransactionReqParam reqParam, HttpServletRequest request){
        log.info("Received Request on {} - {}", request.getServletPath(), request.getMethod());

        DataResponse<Object> response = userService.getBalanceTrans(reqParam);

        log.info("Success Executing Request on {}", request.getServletPath());
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @PostMapping("/upload-profile")
    public ResponseEntity<Object> updateProfile(@RequestParam Integer userId, @RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
        log.info("Received Request on {} - {}", request.getServletPath(), request.getMethod());

        DataResponse<Object> response = userService.uploadProfile(userId, file);

        log.info("Success Executing Request on {}", request.getServletPath());
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

}
