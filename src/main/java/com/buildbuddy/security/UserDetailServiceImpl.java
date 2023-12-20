package com.buildbuddy.security;

import com.buildbuddy.domain.user.entity.UserEntity;
import com.buildbuddy.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<UserEntity> userOpt = userRepository.findByUsername(username);

        UserEntity userEntity = userOpt.orElseThrow(() -> new UsernameNotFoundException("Username not found"));

        String roles = userEntity.getRole();
        List<String> authorities = Arrays.stream(roles.split(",")).toList();
        Set<GrantedAuthority> grantedAuthorities = authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());

        return new User(username, userEntity.getPassword(), grantedAuthorities);
    }
}
