package com.buildbuddy.domain.user.service;

import com.buildbuddy.domain.user.entity.UserEntity;
import com.buildbuddy.domain.user.repository.UserRepository;
import com.buildbuddy.exception.BadRequestException;
import com.buildbuddy.jsonresponse.DataResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public DataResponse<String> getUserByUsername(String username){
        UserEntity user = userRepository.findByUsername(username);

        if (user == null) throw new BadRequestException("user not found");

        String data = user.getUsername() + " " + user.getEmail() + " " + user.getAge();

        return DataResponse.<String>builder()
                .timestamp(LocalDateTime.now())
                .httpStatus(HttpStatus.OK)
                .message("Success getting user")
                .data(data)
                .build();
    }

}
