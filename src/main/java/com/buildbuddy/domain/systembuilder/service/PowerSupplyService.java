package com.buildbuddy.domain.systembuilder.service;

import com.buildbuddy.audit.AuditorAwareImpl;
import com.buildbuddy.domain.systembuilder.dto.param.PowerSupplyRequestParam;
import com.buildbuddy.domain.systembuilder.dto.request.PowerSupplyRequestDto;
import com.buildbuddy.domain.systembuilder.dto.response.powersupply.PowerSupplyResponseDto;
import com.buildbuddy.domain.systembuilder.dto.response.powersupply.PowerSupplyResponseSchema;
import com.buildbuddy.domain.systembuilder.entity.PowerSupplyEntity;
import com.buildbuddy.domain.systembuilder.repository.PowerSupplyRepository;
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
public class PowerSupplyService {

    @Autowired
    private AuditorAwareImpl audit;

    @Autowired
    private PowerSupplyRepository powerSupplyRepository;

    @Autowired
    private SpecificationCreator<PowerSupplyEntity> specificationCreator;

    @Autowired
    private PaginationCreator paginationCreator;

    public DataResponse<PowerSupplyResponseSchema> get(PowerSupplyRequestParam requestParam){

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

        Page<PowerSupplyEntity> dataPage = getPowerSupplyFromDB(requestParam, pageable);
        List<PowerSupplyEntity> powerSupplyList = dataPage.getContent();

        List<PowerSupplyResponseDto> powerSupplyResponseDtos = powerSupplyList.stream()
                .map(PowerSupplyResponseDto::convertToDto)
                .toList();

        PowerSupplyResponseSchema data = PowerSupplyResponseSchema.builder()
                .powerSupplyList(powerSupplyResponseDtos)
                .pageNo(pageNo)
                .pageSize(pageSize)
                .totalPages(dataPage.getTotalPages())
                .totalData(dataPage.getTotalElements())
                .build();

        return DataResponse.<PowerSupplyResponseSchema>builder()
                .timestamp(LocalDateTime.now())
                .httpStatus(HttpStatus.OK)
                .message("Success getting powerSupply")
                .data(data)
                .build();

    }

    private Page<PowerSupplyEntity> getPowerSupplyFromDB(PowerSupplyRequestParam param, Pageable pageable){
        log.info("Getting powerSupply from DB...");

        List<ParamFilter> paramFilters = param.getFilters();
        Page<PowerSupplyEntity> data = null;

        if(paramFilters.isEmpty())
            data = powerSupplyRepository.findAll(pageable);
        else
            data = powerSupplyRepository.findAll(specificationCreator.getSpecification(paramFilters), pageable);

        return data;
    }

    @Transactional
    public DataResponse<PowerSupplyResponseDto> save(PowerSupplyRequestDto powerSupplyDto){
        log.info("powerSupply: {}", powerSupplyDto);

        Integer id = powerSupplyDto.getId();
        PowerSupplyEntity powerSupply = null;

        if(id != null){
            UserEntity currentUser = audit.getCurrentAuditor().orElseThrow(() ->  new BadRequestException("Request not authenticated"));
            log.info("current authenticated user: {}", currentUser.getUsername());

            powerSupply = powerSupplyRepository.findById(id).orElseThrow(() -> new BadRequestException("power supply not found"));
            powerSupply.setName(powerSupplyDto.getName());
            powerSupply.setPower(powerSupplyDto.getPower());
            powerSupply.setManufacturer(powerSupplyDto.getManufacturer());
            powerSupply.setColor(powerSupplyDto.getColor());
            powerSupply.setPrice(powerSupplyDto.getPrice());
            powerSupply.setEfficiency(powerSupplyDto.getEfficiency());
            powerSupply.setProductLink(powerSupplyDto.getProductLink());
            powerSupply.setFormFactor(powerSupplyDto.getFormFactor());
            powerSupply.setImage(powerSupplyDto.getImage() != null ? Base64.getDecoder().decode(powerSupplyDto.getImage()) : null);

        }
        else{
            powerSupply = PowerSupplyRequestDto.convertToEntity(powerSupplyDto);
        }

        log.info("saving...");
        powerSupply = powerSupplyRepository.saveAndFlush(powerSupply);
        log.info("saved..");

        PowerSupplyResponseDto data = PowerSupplyResponseDto.convertToDto(powerSupply);

        return DataResponse.<PowerSupplyResponseDto>builder()
                .timestamp(LocalDateTime.now())
                .httpStatus(HttpStatus.OK)
                .message("Success saving powerSupply")
                .data(data)
                .build();

    }

    @Transactional
    public DataResponse<String> delete(Integer powerSupplyId){
        log.info("deleting powerSupply with id: {}", powerSupplyId);
        UserEntity currentUser = audit.getCurrentAuditor().orElseThrow(() ->  new BadRequestException("Request not authenticated"));
        log.info("current authenticated user: {}", currentUser.getUsername());

        PowerSupplyEntity powerSupply = powerSupplyRepository.findById(powerSupplyId).orElseThrow(() -> new BadRequestException("Post Not Found"));

        log.info("deleting...");
        powerSupplyRepository.delete(powerSupply);
        log.info("deleted.");

        return DataResponse.<String>builder()
                .timestamp(LocalDateTime.now())
                .httpStatus(HttpStatus.OK)
                .message("Success deleting powerSupply")
                .data("PowerSupply with id: " + powerSupplyId + " deleted.")
                .build();
    }

}
