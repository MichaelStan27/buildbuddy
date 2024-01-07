package com.buildbuddy.audit;

import com.buildbuddy.domain.user.entity.UserEntity;
import com.buildbuddy.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditorAwareImpl implements AuditorAware<UserEntity> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<UserEntity> getCurrentAuditor() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null) throw new RuntimeException("Auth Object is null");

        if(!authentication.isAuthenticated()) throw new RuntimeException("Auth Object is not authenticated");

        if(!(authentication.getPrincipal() instanceof User)) throw new RuntimeException("Auth Object is not recognized");

        User user = (User) authentication.getPrincipal();

        return userRepository.findByUsername(user.getUsername());
    }
}
