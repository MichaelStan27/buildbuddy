package com.buildbuddy.domain.consult.service;

import com.buildbuddy.audit.AuditorAwareImpl;
import com.buildbuddy.domain.consult.dto.param.ConsultantReqParam;
import com.buildbuddy.domain.consult.dto.request.ConsultantReqDto;
import com.buildbuddy.domain.consult.dto.response.ConsultantDetailDto;
import com.buildbuddy.domain.consult.dto.response.ConsultantSchema;
import com.buildbuddy.domain.consult.entity.ConsultantDetail;
import com.buildbuddy.domain.consult.entity.ConsultantModel;
import com.buildbuddy.domain.consult.repository.ConsultantDetailRepository;
import com.buildbuddy.domain.user.entity.UserEntity;
import com.buildbuddy.domain.user.repository.UserRepository;
import com.buildbuddy.enums.UserRole;
import com.buildbuddy.exception.BadRequestException;
import com.buildbuddy.jsonresponse.DataResponse;
import com.buildbuddy.util.PaginationCreator;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ConsultService {

    @Autowired
    private AuditorAwareImpl audit;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConsultantDetailRepository consultantDetailRepository;

    @Autowired
    private PaginationCreator paginationCreator;

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

    public DataResponse<ConsultantSchema> get(ConsultantReqParam param){
        log.info("get consultant by param: {}", param);

        boolean isPaginated = param.isPagination();
        Integer pageNo = param.getPageNo();
        Integer pageSize = param.getPageSize();
        String sortBy = param.getSortBy();
        String sortDirection = param.getSortDirection();

        Sort sort = paginationCreator.createAliasesSort(sortDirection, sortBy);

        Pageable pageable = paginationCreator.createPageable(isPaginated, sort, pageNo, pageSize);

        String username = createLikeKeyword(param.getUsername());
        String gender = param.getGender() != null ? param.getGender().toUpperCase() : null;
        String desc = createLikeKeyword(param.getDescription());
        Integer avail = param.getAvailable();

        Page<ConsultantModel> consultantModelPage = consultantDetailRepository.getConsultantByCustomParam(username, gender, desc, avail, pageable);

        List<ConsultantDetailDto> dtoList = consultantModelPage.getContent().stream()
                .map(ConsultantDetailDto::convertToDto)
                .toList();

        ConsultantSchema data = ConsultantSchema.builder()
                .consultantList(dtoList)
                .pageNo(pageNo)
                .pageSize(pageSize)
                .totalPages(consultantModelPage.getTotalPages())
                .totalData(consultantModelPage.getTotalElements())
                .build();

        return DataResponse.<ConsultantSchema>builder()
                .timestamp(LocalDateTime.now())
                .httpStatus(HttpStatus.OK)
                .message("Success getting consultant list")
                .data(data)
                .build();
    }

    private String createLikeKeyword(String word){
        return word != null ? "%" + word.toUpperCase() + "%" : null;
    }
}
