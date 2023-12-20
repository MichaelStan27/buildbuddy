package com.buildbuddy.security;

import com.buildbuddy.domain.user.entity.UserEntity;
import com.buildbuddy.domain.user.repository.UserRepository;
import com.buildbuddy.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserDetailServiceImpl {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    public UserDetails loadUserByUsernameAndPassword(String username, String password) throws UsernameNotFoundException {

        Optional<UserEntity> userOpt = userRepository.findByUsername(username);

        UserEntity userEntity = userOpt.orElseThrow(() -> new UsernameNotFoundException("Invalid Username or password"));

        if(!passwordEncoder.matches(password, userEntity.getPassword())) throw new BadCredentialsException("Invalid username or password");

        String roles = userEntity.getRole();
        List<String> authorities = Arrays.stream(roles.split(",")).toList();
        Set<GrantedAuthority> grantedAuthorities = authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());

        return new User(username, userEntity.getPassword(), grantedAuthorities);
    }
}
