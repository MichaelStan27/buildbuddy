package com.buildbuddy.domain.user.controller;

import com.buildbuddy.domain.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

        String response = userService.getUserByUsername(username);

        log.info("Success Executing Request on {}", request.getServletPath());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
