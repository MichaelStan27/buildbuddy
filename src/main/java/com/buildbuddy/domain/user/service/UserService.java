package com.buildbuddy.domain.user.service;

import com.buildbuddy.audit.AuditorAwareImpl;
import com.buildbuddy.domain.user.dto.request.BalanceReqDto;
import com.buildbuddy.domain.user.dto.request.UserRequestDto;
import com.buildbuddy.domain.user.dto.response.BalanceResDto;
import com.buildbuddy.domain.user.dto.response.UserResponseDto;
import com.buildbuddy.domain.user.entity.BalanceTransaction;
import com.buildbuddy.domain.user.entity.UserEntity;
import com.buildbuddy.domain.user.repository.BalanceTransactionRepository;
import com.buildbuddy.domain.user.repository.UserRepository;
import com.buildbuddy.enums.UserRole;
import com.buildbuddy.enums.balance.BalanceTransactionStatus;
import com.buildbuddy.enums.balance.BalanceTransactionType;
import com.buildbuddy.enums.balance.BalanceTransactionWorkflow;
import com.buildbuddy.jsonresponse.DataResponse;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BalanceTransactionRepository balanceTransactionRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuditorAwareImpl auditorAware;

    public DataResponse<UserResponseDto> getUserByUsername(String username){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userDetails = (User) authentication.getPrincipal();
         log.info("{}", userDetails.getUsername());

        Optional<UserEntity> userOpt = userRepository.findByUsername(username);

        UserEntity user = userOpt.orElseThrow(() -> new UsernameNotFoundException("User Not Found"));

        UserResponseDto data = UserResponseDto.convertToDto(user);

        return DataResponse.<UserResponseDto>builder()
                .timestamp(LocalDateTime.now())
                .httpStatus(HttpStatus.OK)
                .message("Success getting user")
                .data(data)
                .build();
    }

    public DataResponse<Object> getUser(Integer userId){

        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user not found"));

        UserResponseDto data = UserResponseDto.convertToDto(user);

        return DataResponse.<Object>builder()
                .timestamp(LocalDateTime.now())
                .httpStatus(HttpStatus.OK)
                .data(data)
                .message("Success getting user")
                .build();
    }

    @Transactional
    public DataResponse<UserResponseDto> createUser(UserRequestDto userDto){

        String pass = passwordEncoder.encode(userDto.getPassword());

        UserEntity user = UserEntity.builder()
                .username(userDto.getUsername())
                .email(userDto.getEmail())
                .password(pass)
                .role("user")
                .age(userDto.getAge())
                .gender(userDto.getGender())
                .balance(BigDecimal.ZERO)
                .createdTime(LocalDateTime.now())
                .build();

        user = userRepository.saveAndFlush(user);

        UserResponseDto data = UserResponseDto.convertToDto(user);

        return DataResponse.<UserResponseDto>builder()
                .timestamp(LocalDateTime.now())
                .httpStatus(HttpStatus.CREATED)
                .message("Success creating user")
                .data(data)
                .build();
    }

    @Transactional
    public DataResponse<Object> topup(BalanceReqDto dto){

        UserEntity user = auditorAware.getCurrentAuditor().orElseThrow(() -> new RuntimeException("user not found"));
        BalanceTransaction balanceTransaction = null;
        BalanceTransactionStatus status = null;

        /*
        create transaction must authenticated with user
        approve/reject transaction must authenticated with admin
         */

        if(dto.getBalanceTransactionId() == null){
            status = BalanceTransactionStatus.PENDING;
            balanceTransaction = BalanceTransaction.builder()
                    .user(user)
                    .nominal(dto.getNominal())
                    .status(status.getValue())
                    .transactionType(BalanceTransactionType.TOP_UP.getValue())
                    .build();
        }
        else{

            if(!user.getRole().equals(UserRole.ADMIN.getValue()))
                throw new RuntimeException("Cant approved or reject transaction user is not admin");

            status = BalanceTransactionStatus.getByValue(dto.getStatus());
            balanceTransaction = balanceTransactionRepository.findByTransactionIdAndUserId(dto.getBalanceTransactionId(), dto.getUserId(), BalanceTransactionType.TOP_UP.getValue()).orElseThrow(() -> new RuntimeException("Balance transaction not found with id: " + dto.getBalanceTransactionId()));
            BalanceTransactionStatus oldStatus = BalanceTransactionStatus.getByValue(balanceTransaction.getStatus());
            BalanceTransactionWorkflow wf = BalanceTransactionWorkflow.getByTypeAndStatus(BalanceTransactionType.TOP_UP, oldStatus);
            List<BalanceTransactionStatus> allowedStatus = wf.getNextStatus();

            if(!allowedStatus.contains(status))
                throw new RuntimeException("Cant change transaction status from " + oldStatus + " to " + status);

            balanceTransaction.setStatus(status.getValue());
        }

        switch (status) {
            case ADDED -> {
                UserEntity userEntity = userRepository.findUserById(dto.getUserId()).orElseThrow(() -> new RuntimeException("user not found"));
                userEntity.setBalance(userEntity.getBalance().add(balanceTransaction.getNominal()));
                userRepository.saveAndFlush(userEntity);
            }
            case REJECTED -> {
                log.info("Top Up Balance Rejected");
            }
        }

        balanceTransactionRepository.saveAndFlush(balanceTransaction);

        BalanceResDto data = BalanceResDto.builder()
                .balanceTransactionId(balanceTransaction.getTransactionId())
                .nominal(dto.getNominal())
                .status(dto.getStatus())
                .userId(dto.getBalanceTransactionId() == null ? user.getId() : dto.getUserId())
                .build();

        return DataResponse.builder()
                .message("Success Executing Topup Workflow")
                .httpStatus(HttpStatus.OK)
                .timestamp(LocalDateTime.now())
                .data(data)
                .build();
    }

}
