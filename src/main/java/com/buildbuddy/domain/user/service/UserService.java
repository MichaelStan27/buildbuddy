package com.buildbuddy.domain.user.service;

import com.buildbuddy.domain.user.entity.UserEntity;
import com.buildbuddy.domain.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public String getUserByUsername(String username){

        log.info("username: {}", username);
        UserEntity user = userRepository.findByUsername(username);

        if (user == null) throw new RuntimeException("user not found");

        return user.getUsername() + " " + user.getEmail() + " " + user.getAge();
    }

}
