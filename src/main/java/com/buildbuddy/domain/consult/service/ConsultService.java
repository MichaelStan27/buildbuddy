package com.buildbuddy.domain.consult.service;

import com.buildbuddy.audit.AuditorAwareImpl;
import com.buildbuddy.domain.consult.dto.request.ConsultantReqDto;
import com.buildbuddy.domain.consult.dto.response.ConsultantDetailDto;
import com.buildbuddy.domain.consult.entity.ConsultantDetail;
import com.buildbuddy.domain.consult.repository.ConsultantDetailRepository;
import com.buildbuddy.domain.user.entity.UserEntity;
import com.buildbuddy.domain.user.repository.UserRepository;
import com.buildbuddy.enums.UserRole;
import com.buildbuddy.exception.BadRequestException;
import com.buildbuddy.jsonresponse.DataResponse;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Slf4j
@Service
public class ConsultService {

    @Autowired
    private AuditorAwareImpl audit;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConsultantDetailRepository consultantDetailRepository;

    @Transactional
    public DataResponse<ConsultantDetailDto> save(ConsultantReqDto consultantReqDto){

        BigDecimal fee = consultantReqDto.getFee();
        String desc = consultantReqDto.getDescription();

        UserEntity currentUser = audit.getCurrentAuditor().orElseThrow(() ->  new BadRequestException("Request not authenticated"));
        log.info("current authenticated user: {}", currentUser.getUsername());

        if(!currentUser.getRole().equals(UserRole.CONSULTANT.getValue()))
            throw new RuntimeException("authenticated user is not a consultant");

        ConsultantDetail userDetail = currentUser.getConsultantDetail();

        if(userDetail == null){
            ConsultantDetail detail = ConsultantDetail.builder()
                    .user(currentUser)
                    .fee(fee)
                    .description(desc)
                    .available(0)
                    .build();

            currentUser.setConsultantDetail(detail);
        }
        else{
            userDetail.setFee(fee);
            userDetail.setDescription(desc);
        }

        currentUser = userRepository.saveAndFlush(currentUser);

        ConsultantDetail savedDetail = currentUser.getConsultantDetail();

        ConsultantDetailDto response = ConsultantDetailDto.builder()
                .username(currentUser.getUsername())
                .email(currentUser.getEmail())
                .age(currentUser.getAge())
                .gender(currentUser.getGender())
                .description(savedDetail.getDescription())
                .fee(savedDetail.getFee())
                .isAvailable(savedDetail.getAvailable() != 0)
                .build();

        return DataResponse.<ConsultantDetailDto>builder()
                .timestamp(LocalDateTime.now())
                .httpStatus(HttpStatus.OK)
                .message("Success saving consultant detail")
                .data(response)
                .build();
    }

}
