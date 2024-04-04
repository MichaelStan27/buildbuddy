package com.buildbuddy.domain.systembuilder.service;

import com.buildbuddy.audit.AuditorAwareImpl;
import com.buildbuddy.domain.systembuilder.dto.param.RamRequestParam;
import com.buildbuddy.domain.systembuilder.dto.request.RamRequestDto;
import com.buildbuddy.domain.systembuilder.dto.response.ram.RamResponseDto;
import com.buildbuddy.domain.systembuilder.dto.response.ram.RamResponseSchema;
import com.buildbuddy.domain.systembuilder.repository.RamRepository;
import com.buildbuddy.domain.systembuilder.entity.RamEntity;
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
public class RamService {

    @Autowired
    private AuditorAwareImpl audit;

    @Autowired
    private RamRepository ramRepository;

    @Autowired
    private SpecificationCreator<RamEntity> specificationCreator;

    @Autowired
    private PaginationCreator paginationCreator;

    public DataResponse<RamResponseSchema> get(RamRequestParam requestParam){

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

        Page<RamEntity> dataPage = getRamFromDB(requestParam, pageable);
        List<RamEntity> ramList = dataPage.getContent();

        List<RamResponseDto> ramResponseDtos = ramList.stream()
                .map(RamResponseDto::convertToDto)
                .toList();

        RamResponseSchema data = RamResponseSchema.builder()
                .ramList(ramResponseDtos)
                .pageNo(pageNo)
                .pageSize(pageSize)
                .totalPages(dataPage.getTotalPages())
                .totalData(dataPage.getTotalElements())
                .build();

        return DataResponse.<RamResponseSchema>builder()
                .timestamp(LocalDateTime.now())
                .httpStatus(HttpStatus.OK)
                .message("Success getting ram")
                .data(data)
                .build();

    }

    private Page<RamEntity> getRamFromDB(RamRequestParam param, Pageable pageable){
        log.info("Getting ram from DB...");

        List<ParamFilter> paramFilters = param.getFilters();
        Page<RamEntity> data = null;

        if(paramFilters.isEmpty())
            data = ramRepository.findAll(pageable);
        else
            data = ramRepository.findAll(specificationCreator.getSpecification(paramFilters), pageable);

        return data;
    }

    @Transactional
    public DataResponse<RamResponseDto> save(RamRequestDto ramDto){
        log.info("ram: {}", ramDto);

        Integer id = ramDto.getId();
        RamEntity ram = null;

        if(id != null){
            UserEntity currentUser = audit.getCurrentAuditor().orElseThrow(() ->  new BadRequestException("Request not authenticated"));
            log.info("current authenticated user: {}", currentUser.getUsername());

            ram = ramRepository.findById(id).orElseThrow(() -> new BadRequestException("ram not found"));
            ram.setName(ramDto.getName());
            ram.setManufacturer(ramDto.getManufacturer());
            ram.setPrice(ramDto.getPrice());
            ram.setRamQuantity(ramDto.getRamQuantity());
            ram.setRamSize(ramDto.getRamSize());
            ram.setRamType(ramDto.getRamType());
            ram.setProductLink(ramDto.getProductLink());
            ram.setRamSpeed(ramDto.getRamSpeed());
            ram.setCasLatency(ramDto.getCasLatency());
            ram.setImage(ramDto.getImage() != null ? Base64.getDecoder().decode(ramDto.getImage()) : null);

        }
        else{
            ram = RamRequestDto.convertToEntity(ramDto);
        }

        log.info("saving...");
        ram = ramRepository.saveAndFlush(ram);
        log.info("saved..");

        RamResponseDto data = RamResponseDto.convertToDto(ram);

        return DataResponse.<RamResponseDto>builder()
                .timestamp(LocalDateTime.now())
                .httpStatus(HttpStatus.OK)
                .message("Success saving ram")
                .data(data)
                .build();

    }

    @Transactional
    public DataResponse<String> delete(Integer ramId){
        log.info("deleting ram with id: {}", ramId);
        UserEntity currentUser = audit.getCurrentAuditor().orElseThrow(() ->  new BadRequestException("Request not authenticated"));
        log.info("current authenticated user: {}", currentUser.getUsername());

        RamEntity ram = ramRepository.findById(ramId).orElseThrow(() -> new BadRequestException("Post Not Found"));

        log.info("deleting...");
        ramRepository.delete(ram);
        log.info("deleted.");

        return DataResponse.<String>builder()
                .timestamp(LocalDateTime.now())
                .httpStatus(HttpStatus.OK)
                .message("Success deleting ram")
                .data("Ram with id: " + ramId + " deleted.")
                .build();
    }

}
