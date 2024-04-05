package com.buildbuddy.domain.systembuilder.service;

import com.buildbuddy.audit.AuditorAwareImpl;
import com.buildbuddy.domain.systembuilder.dto.param.MotherboardRequestParam;
import com.buildbuddy.domain.systembuilder.dto.request.MotherboardRequestDto;
import com.buildbuddy.domain.systembuilder.dto.response.motherboard.MotherboardResponseDto;
import com.buildbuddy.domain.systembuilder.dto.response.motherboard.MotherboardResponseDto;
import com.buildbuddy.domain.systembuilder.dto.response.motherboard.MotherboardResponseSchema;
import com.buildbuddy.domain.systembuilder.entity.MotherboardEntity;
import com.buildbuddy.domain.systembuilder.repository.MotherboardRepository;
import com.buildbuddy.domain.systembuilder.entity.MotherboardEntity;
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
public class MotherboardService {

    @Autowired
    private AuditorAwareImpl audit;

    @Autowired
    private MotherboardRepository motherboardRepository;

    @Autowired
    private SpecificationCreator<MotherboardEntity> specificationCreator;

    @Autowired
    private PaginationCreator paginationCreator;

    public DataResponse<MotherboardResponseSchema> get(MotherboardRequestParam requestParam){

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

        Page<MotherboardEntity> dataPage = getMotherboardFromDB(requestParam, pageable);
        List<MotherboardEntity> motherboardList = dataPage.getContent();

        List<MotherboardResponseDto> motherboardResponseDtos = motherboardList.stream()
                .map(MotherboardResponseDto::convertToDto)
                .toList();

        MotherboardResponseSchema data = MotherboardResponseSchema.builder()
                .motherboardList(motherboardResponseDtos)
                .pageNo(pageNo)
                .pageSize(pageSize)
                .totalPages(dataPage.getTotalPages())
                .totalData(dataPage.getTotalElements())
                .build();

        return DataResponse.<MotherboardResponseSchema>builder()
                .timestamp(LocalDateTime.now())
                .httpStatus(HttpStatus.OK)
                .message("Success getting motherboard")
                .data(data)
                .build();

    }

    private Page<MotherboardEntity> getMotherboardFromDB(MotherboardRequestParam param, Pageable pageable){
        log.info("Getting motherboard from DB...");

        List<ParamFilter> paramFilters = param.getFilters();
        Page<MotherboardEntity> data = null;

        if(paramFilters.isEmpty())
            data = motherboardRepository.findAll(pageable);
        else
            data = motherboardRepository.findAll(specificationCreator.getSpecification(paramFilters), pageable);

        return data;
    }

    @Transactional
    public DataResponse<MotherboardResponseDto> save(MotherboardRequestDto motherboardDto){
        log.info("motherboard: {}", motherboardDto);

        Integer id = motherboardDto.getId();
        MotherboardEntity motherboard = null;

        if(id != null){
            UserEntity currentUser = audit.getCurrentAuditor().orElseThrow(() ->  new BadRequestException("Request not authenticated"));
            log.info("current authenticated user: {}", currentUser.getUsername());

            motherboard = motherboardRepository.findById(id).orElseThrow(() -> new BadRequestException("motherboard not found"));
            motherboard.setName(motherboardDto.getName());
            motherboard.setChipset(motherboardDto.getChipset());
            motherboard.setManufacturer(motherboardDto.getManufacturer());
            motherboard.setPrice(motherboardDto.getPrice());
            motherboard.setProductLink(motherboardDto.getProductLink());
            motherboard.setFormFactor(motherboardDto.getFormFactor());
            motherboard.setMaxMemory(motherboardDto.getMaxMemory());
            motherboard.setMemorySlots(motherboardDto.getMemorySlots());
            motherboard.setSocketType(motherboardDto.getSocketType());
            motherboard.setRamType(motherboardDto.getRamType());
            motherboard.setImage(motherboardDto.getImage() != null ? Base64.getDecoder().decode(motherboardDto.getImage()) : null);

        }
        else{
            motherboard = MotherboardRequestDto.convertToEntity(motherboardDto);
        }

        log.info("saving...");
        motherboard = motherboardRepository.saveAndFlush(motherboard);
        log.info("saved..");

        MotherboardResponseDto data = MotherboardResponseDto.convertToDto(motherboard);

        return DataResponse.<MotherboardResponseDto>builder()
                .timestamp(LocalDateTime.now())
                .httpStatus(HttpStatus.OK)
                .message("Success saving motherboard")
                .data(data)
                .build();

    }

    @Transactional
    public DataResponse<String> delete(Integer motherboardId){
        log.info("deleting motherboard with id: {}", motherboardId);
        UserEntity currentUser = audit.getCurrentAuditor().orElseThrow(() ->  new BadRequestException("Request not authenticated"));
        log.info("current authenticated user: {}", currentUser.getUsername());

        MotherboardEntity motherboard = motherboardRepository.findById(motherboardId).orElseThrow(() -> new BadRequestException("Post Not Found"));

        log.info("deleting...");
        motherboardRepository.delete(motherboard);
        log.info("deleted.");

        return DataResponse.<String>builder()
                .timestamp(LocalDateTime.now())
                .httpStatus(HttpStatus.OK)
                .message("Success deleting motherboard")
                .data("Motherboard with id: " + motherboardId + " deleted.")
                .build();
    }

}
