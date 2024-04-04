package com.buildbuddy.domain.systembuilder.service;

import com.buildbuddy.audit.AuditorAwareImpl;
import com.buildbuddy.domain.systembuilder.dto.param.CaseRequestParam;
import com.buildbuddy.domain.systembuilder.dto.request.CaseRequestDto;
import com.buildbuddy.domain.systembuilder.dto.response.computerCase.CaseResponseDto;
import com.buildbuddy.domain.systembuilder.dto.response.computerCase.CaseResponseSchema;
import com.buildbuddy.domain.systembuilder.repository.CaseRepository;
import com.buildbuddy.domain.systembuilder.entity.CaseEntity;
import com.buildbuddy.domain.user.entity.UserEntity;
import com.buildbuddy.exception.BadRequestException;
import com.buildbuddy.jsonresponse.DataResponse;
import com.buildbuddy.util.PaginationCreator;
import com.buildbuddy.util.spesification.ParamFilter;
import com.buildbuddy.util.spesification.SpecificationCreator;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;

@Slf4j
@Service
public class CaseService {

    @Autowired
    private AuditorAwareImpl audit;

    @Autowired
    private CaseRepository caseRepository;

    @Autowired
    private SpecificationCreator<CaseEntity> specificationCreator;

    @Autowired
    private PaginationCreator paginationCreator;

    public DataResponse<CaseResponseSchema> get(CaseRequestParam requestParam){

        // pagination and sort
        // ===========
        boolean isPaginated = requestParam.isPagination();
        Integer pageNo = requestParam.getPageNo();
        Integer pageSize = requestParam.getPageSize();
        String sortBy = requestParam.getSortBy();
        String sortDirection = requestParam.getSortDirection();

        Sort sort = paginationCreator.createSort(sortDirection, sortBy);

        Pageable pageable = paginationCreator.createPageable(isPaginated, sort, pageNo, pageSize);
        // ===========

        Page<CaseEntity> dataPage = getCaseFromDB(requestParam, pageable);
        List<CaseEntity> caseList = dataPage.getContent();

        List<CaseResponseDto> caseResponseDtos = caseList.stream()
                .map(CaseResponseDto::convertToDto)
                .toList();

        CaseResponseSchema data = CaseResponseSchema.builder()
                .caseList(caseResponseDtos)
                .pageNo(pageNo)
                .pageSize(pageSize)
                .totalPages(dataPage.getTotalPages())
                .totalData(dataPage.getTotalElements())
                .build();

        return DataResponse.<CaseResponseSchema>builder()
                .timestamp(LocalDateTime.now())
                .httpStatus(HttpStatus.OK)
                .message("Success getting case")
                .data(data)
                .build();

    }

    private Page<CaseEntity> getCaseFromDB(CaseRequestParam param, Pageable pageable){
        log.info("Getting case from DB...");

        List<ParamFilter> paramFilters = param.getFilters();
        Page<CaseEntity> data = null;

        if(paramFilters.isEmpty())
            data = caseRepository.findAll(pageable);
        else
            data = caseRepository.findAll(specificationCreator.getSpecification(paramFilters), pageable);

        return data;
    }

    @Transactional
    public DataResponse<CaseResponseDto> save(CaseRequestDto caseDto){
        log.info("case: {}", caseDto);

        Integer id = caseDto.getId();
        CaseEntity computerCase = null;

        if(id != null){
            UserEntity currentUser = audit.getCurrentAuditor().orElseThrow(() ->  new BadRequestException("Request not authenticated"));
            log.info("current authenticated user: {}", currentUser.getUsername());

            computerCase = caseRepository.findById(id).orElseThrow(() -> new BadRequestException("Case not found"));
            computerCase.setName(caseDto.getName());
            computerCase.setPrice(caseDto.getPrice());
            computerCase.setColor(caseDto.getColor());
            computerCase.setManufacturer(caseDto.getManufacturer());
            computerCase.setCabinetType(caseDto.getCabinetType());
            computerCase.setProductLink(caseDto.getProductLink());
            computerCase.setSidePanel(caseDto.getSidePanel());
            computerCase.setImage(caseDto.getImage() != null ? Base64.getDecoder().decode(caseDto.getImage()) : null);

        }
        else{
            computerCase = CaseRequestDto.convertToEntity(caseDto);
        }

        log.info("saving...");
        log.info("{}",computerCase);
        computerCase = caseRepository.saveAndFlush(computerCase);
        log.info("saved..");

        CaseResponseDto data = CaseResponseDto.convertToDto(computerCase);

        return DataResponse.<CaseResponseDto>builder()
                .timestamp(LocalDateTime.now())
                .httpStatus(HttpStatus.OK)
                .message("Success saving case")
                .data(data)
                .build();

    }

    @Transactional
    public DataResponse<String> delete(Integer caseId){
        log.info("deleting case with id: {}", caseId);
        UserEntity currentUser = audit.getCurrentAuditor().orElseThrow(() ->  new BadRequestException("Request not authenticated"));
        log.info("current authenticated user: {}", currentUser.getUsername());

        CaseEntity computerCase = caseRepository.findById(caseId).orElseThrow(() -> new BadRequestException("Post Not Found"));

        log.info("deleting...");
        caseRepository.delete(computerCase);
        log.info("deleted.");

        return DataResponse.<String>builder()
                .timestamp(LocalDateTime.now())
                .httpStatus(HttpStatus.OK)
                .message("Success deleting case")
                .data("Case with id: " + caseId + " deleted.")
                .build();
    }

}
