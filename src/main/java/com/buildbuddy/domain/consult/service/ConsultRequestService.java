package com.buildbuddy.domain.consult.service;

import com.buildbuddy.audit.AuditorAwareImpl;
import com.buildbuddy.domain.consult.dto.EmailDto;
import com.buildbuddy.domain.consult.dto.param.ConsultantRequestReqParam;
import com.buildbuddy.domain.consult.dto.request.ConsultantRequestDto;
import com.buildbuddy.domain.consult.dto.response.ConsultantRequestResDto;
import com.buildbuddy.domain.consult.dto.response.ConsultantRequestSchema;
import com.buildbuddy.domain.consult.entity.ConsultantRequest;
import com.buildbuddy.domain.consult.repository.ConsultantRequestRepository;
import com.buildbuddy.domain.user.entity.UserEntity;
import com.buildbuddy.enums.consultrequest.ConsultantRequestStatus;
import com.buildbuddy.jsonresponse.DataResponse;
import com.buildbuddy.util.PaginationCreator;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ConsultRequestService {

    @Autowired
    private AuditorAwareImpl audit;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ConsultantRequestRepository consultantRequestRepository;

    @Autowired
    private PaginationCreator paginationCreator;

    @Transactional
    public DataResponse<Object> create(ConsultantRequestDto dto){

        if(dto.getUsername() == null || dto.getEmail() == null)
            throw new RuntimeException("Must Fill Username and Email");

        Optional<String> username = consultantRequestRepository.getByUsername(dto.getUsername());
        if(username.isPresent())
            throw new RuntimeException("Username is already taken, please choose another one");

        Optional<String> email = consultantRequestRepository.getByEmail(dto.getEmail());
        if(email.isPresent())
            throw new RuntimeException("Email is already taken, please choose another one");

        ConsultantRequest consultantRequest = ConsultantRequest.convertToEntity(dto);

        log.info("saving...");
        consultantRequestRepository.saveAndFlush(consultantRequest);

        return DataResponse.builder()
                .timestamp(LocalDateTime.now())
                .httpStatus(HttpStatus.CREATED)
                .message("Consultant Appliance Requested")
                .build();
    }

    @Transactional
    public DataResponse<Object> approval(ConsultantRequestDto dto){

        String message = "";

        String approval = dto.getStatus();

        if(!approval.equals(ConsultantRequestStatus.APPROVED.getValue()) && !approval.equals(ConsultantRequestStatus.REJECTED.getValue()))
            throw new RuntimeException("Approval Status Not Valid");

        ConsultantRequest consultantRequest = consultantRequestRepository.findById(dto.getRequestId()).orElseThrow(() ->  new RuntimeException("Request Not Found"));

        if(!consultantRequest.getStatus().equals(ConsultantRequestStatus.PENDING.getValue()))
            throw new RuntimeException("Request is already reviewed");

        UserEntity admin = audit.getCurrentAuditor().orElseThrow(() -> new RuntimeException("user not found"));

        consultantRequest.setStatus(approval);
        consultantRequest.setLastUpdateTime(LocalDateTime.now());
        consultantRequest.setReviewedBy(admin.getUsername());

        consultantRequestRepository.saveAndFlush(consultantRequest);

        EmailDto emailDto = EmailDto.builder()
                .to(consultantRequest.getEmail())
                .username(consultantRequest.getUsername())
                .body("Your Appliance For Consultant is " + approval + " by admin")
                .build();

        if(approval.equals(ConsultantRequestStatus.APPROVED.getValue()))
            message = "request approved";
        else
            message = "request rejected";
        try {
            emailService.sendEmail(emailDto);
        } catch (MessagingException | IOException e) {
            throw new RuntimeException(e);
        }

        return DataResponse.builder()
                .timestamp(LocalDateTime.now())
                .httpStatus(HttpStatus.OK)
                .message(message)
                .build();
    }


    public DataResponse<Object> get(ConsultantRequestReqParam param){

        log.info("Getting transaction by: {}", param);

        boolean isPaginated = param.isPagination();
        Integer pageNo = param.getPageNo();
        Integer pageSize = param.getPageSize();
        String sortBy = param.getSortBy();
        String sortDirection = param.getSortDirection();

        Sort sort = paginationCreator.createSort(sortDirection, sortBy);

        Pageable pageable = paginationCreator.createPageable(isPaginated, sort, pageNo, pageSize);

        String search = param.getSearch() != null ? "%" + param.getSearch() + "%" : null;

        Page<ConsultantRequest> dataPage = consultantRequestRepository.getByCustomParam(search, pageable);
        List<ConsultantRequest> requestList = dataPage.getContent();

        List<ConsultantRequestResDto> dtoList = requestList.stream()
                .map(ConsultantRequestResDto::convertToDto)
                .toList();

        ConsultantRequestSchema data = ConsultantRequestSchema.builder()
                .requestList(dtoList)
                .totalData(dataPage.getTotalElements())
                .totalPages(dataPage.getTotalPages())
                .pageNo(pageNo)
                .pageSize(pageSize)
                .build();

        return DataResponse.builder()
                .timestamp(LocalDateTime.now())
                .httpStatus(HttpStatus.OK)
                .message("success getting consultant request")
                .data(data)
                .build();
    }

}
