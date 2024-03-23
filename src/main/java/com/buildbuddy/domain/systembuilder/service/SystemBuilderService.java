package com.buildbuddy.domain.systembuilder.service;

import com.buildbuddy.audit.AuditorAwareImpl;
import com.buildbuddy.domain.systembuilder.dto.param.ComputerSetupRequestParam;
import com.buildbuddy.domain.systembuilder.dto.request.ComputerSetupRequestDto;
import com.buildbuddy.domain.systembuilder.dto.response.computerSetup.ComputerSetupResponseDto;
import com.buildbuddy.domain.systembuilder.dto.response.computerSetup.ComputerSetupResponseSchema;
import com.buildbuddy.domain.systembuilder.entity.ComputerSetupEntity;
import com.buildbuddy.domain.systembuilder.repository.ComputerSetupRepository;
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
import java.util.List;

@Slf4j
@Service
public class SystemBuilderService {

    @Autowired
    private AuditorAwareImpl audit;

    @Autowired
    private ComputerSetupRepository computerSetupRepository;

    @Autowired
    private SpecificationCreator<ComputerSetupEntity> specificationCreator;

    @Autowired
    private PaginationCreator paginationCreator;

    public DataResponse<ComputerSetupResponseSchema> get(ComputerSetupRequestParam requestParam){

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

        Page<ComputerSetupEntity> dataPage = getComputerFromDB(requestParam, pageable);
        List<ComputerSetupEntity> computerSetupList = dataPage.getContent();

        List<ComputerSetupResponseDto> computerSetupResponseDtos = computerSetupList.stream()
                .map(ComputerSetupResponseDto::convertToDto)
                .toList();

        ComputerSetupResponseSchema data = ComputerSetupResponseSchema.builder()
                .computerSetupList(computerSetupResponseDtos)
                .pageNo(pageNo)
                .pageSize(pageSize)
                .totalPages(dataPage.getTotalPages())
                .totalData(dataPage.getTotalElements())
                .build();

        return DataResponse.<ComputerSetupResponseSchema>builder()
                .timestamp(LocalDateTime.now())
                .httpStatus(HttpStatus.OK)
                .message("Success getting computer")
                .data(data)
                .build();

    }

    private Page<ComputerSetupEntity> getComputerFromDB(ComputerSetupRequestParam param, Pageable pageable){

        log.info("Getting computer from DB...");

        List<ParamFilter> paramFilters = param.getFilters();
        Page<ComputerSetupEntity> data = null;

        if(paramFilters.isEmpty())
            data = computerSetupRepository.findAll(pageable);
        else
            data = computerSetupRepository.findAll(specificationCreator.getSpecification(paramFilters), pageable);

        return data;
    }

    @Transactional
    public DataResponse<ComputerSetupResponseDto> save(ComputerSetupRequestDto computerDto){

        log.info("computer: {}", computerDto);

        Integer id = computerDto.getId();
        ComputerSetupEntity computerSetup = null;

        if(id != null){
            UserEntity currentUser = audit.getCurrentAuditor().orElseThrow(() ->  new BadRequestException("Request not authenticated"));
            log.info("current authenticated user: {}", currentUser.getUsername());

            computerSetup = computerSetupRepository.findById(id).orElseThrow(() -> new BadRequestException("case not found"));

            computerSetup.setCaseId(computerDto.getCaseId());
            computerSetup.setCoolerId(computerDto.getCoolerId());
            computerSetup.setGraphicsCardId(computerDto.getGraphicsCardId());
            computerSetup.setMonitorId(computerDto.getMonitorId());
            computerSetup.setMotherboardId(computerDto.getMotherboardId());
            computerSetup.setPowersupplyId(computerDto.getPowersupplyId());
            computerSetup.setProcessorId(computerDto.getProcessorId());
            computerSetup.setRamId(computerDto.getRamId());
            computerSetup.setStorageId(computerDto.getStorageId());

        }
        else{
            computerSetup = ComputerSetupRequestDto.convertToEntity(computerDto);
        }

        log.info("saving...");
        computerSetup = computerSetupRepository.saveAndFlush(computerSetup);
        log.info("saved..");

        ComputerSetupResponseDto data = ComputerSetupResponseDto.convertToDto(computerSetup);

        return DataResponse.<ComputerSetupResponseDto>builder()
                .timestamp(LocalDateTime.now())
                .httpStatus(HttpStatus.OK)
                .message("Success saving computer")
                .data(data)
                .build();

    }

    @Transactional
    public DataResponse<String> delete(Integer computerId){

        log.info("deleting case with id: {}", computerId);
        UserEntity currentUser = audit.getCurrentAuditor().orElseThrow(() ->  new BadRequestException("Request not authenticated"));
        log.info("current authenticated user: {}", currentUser.getUsername());

        ComputerSetupEntity computerSetup = computerSetupRepository.findById(computerId).orElseThrow(() -> new BadRequestException("Computer Not Found"));

        log.info("deleting...");
        computerSetupRepository.delete(computerSetup);
        log.info("deleted.");

        return DataResponse.<String>builder()
                .timestamp(LocalDateTime.now())
                .httpStatus(HttpStatus.OK)
                .message("Success deleting computer")
                .data("Case with id: " + computerId + " deleted.")
                .build();

    }

}