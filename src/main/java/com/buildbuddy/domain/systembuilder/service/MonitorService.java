package com.buildbuddy.domain.systembuilder.service;

import com.buildbuddy.audit.AuditorAwareImpl;
import com.buildbuddy.domain.systembuilder.dto.param.MonitorRequestParam;
import com.buildbuddy.domain.systembuilder.dto.request.MonitorRequestDto;
import com.buildbuddy.domain.systembuilder.dto.response.monitor.MonitorResponseDto;
import com.buildbuddy.domain.systembuilder.dto.response.monitor.MonitorResponseSchema;
import com.buildbuddy.domain.systembuilder.entity.MonitorEntity;
import com.buildbuddy.domain.systembuilder.repository.MonitorRepository;
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
public class MonitorService {

    @Autowired
    private AuditorAwareImpl audit;

    @Autowired
    private MonitorRepository monitorRepository;

    @Autowired
    private SpecificationCreator<MonitorEntity> specificationCreator;

    @Autowired
    private PaginationCreator paginationCreator;

    public DataResponse<MonitorResponseSchema> get(MonitorRequestParam requestParam){

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

        Page<MonitorEntity> dataPage = getMonitorFromDB(requestParam, pageable);
        List<MonitorEntity> monitorList = dataPage.getContent();

        List<MonitorResponseDto> monitorResponseDtos = monitorList.stream()
                .map(MonitorResponseDto::convertToDto)
                .toList();

        MonitorResponseSchema data = MonitorResponseSchema.builder()
                .monitorList(monitorResponseDtos)
                .pageNo(pageNo)
                .pageSize(pageSize)
                .totalPages(dataPage.getTotalPages())
                .totalData(dataPage.getTotalElements())
                .build();

        return DataResponse.<MonitorResponseSchema>builder()
                .timestamp(LocalDateTime.now())
                .httpStatus(HttpStatus.OK)
                .message("Success getting monitor")
                .data(data)
                .build();

    }

    private Page<MonitorEntity> getMonitorFromDB(MonitorRequestParam param, Pageable pageable){
        log.info("Getting monitor from DB...");

        List<ParamFilter> paramFilters = param.getFilters();
        Page<MonitorEntity> data = null;

        if(paramFilters.isEmpty())
            data = monitorRepository.findAll(pageable);
        else
            data = monitorRepository.findAll(specificationCreator.getSpecification(paramFilters), pageable);

        return data;
    }

    @Transactional
    public DataResponse<MonitorResponseDto> save(MonitorRequestDto monitorDto){
        log.info("monitor: {}", monitorDto);

        Integer id = monitorDto.getId();
        MonitorEntity monitor = null;

        if(id != null){
            UserEntity currentUser = audit.getCurrentAuditor().orElseThrow(() ->  new BadRequestException("Request not authenticated"));
            log.info("current authenticated user: {}", currentUser.getUsername());

            monitor = monitorRepository.findById(id).orElseThrow(() -> new BadRequestException("monitor not found"));
            monitor.setManufacturer(monitorDto.getManufacturer());
            monitor.setName(monitorDto.getName());
            monitor.setPrice(monitorDto.getPrice());
            monitor.setProductLink(monitorDto.getProductLink());
            monitor.setAspectRatio(monitorDto.getAspectRatio());
            monitor.setResolution(monitorDto.getResolution());
            monitor.setPanelType(monitorDto.getPanelType());
            monitor.setResponseTime(monitorDto.getResponseTime());
            monitor.setRefreshRate(monitorDto.getRefreshRate());
            monitor.setScreenSize(monitorDto.getScreenSize());

        }
        else{
            monitor = MonitorRequestDto.convertToEntity(monitorDto);
        }

        log.info("saving...");
        monitor = monitorRepository.saveAndFlush(monitor);
        log.info("saved..");

        MonitorResponseDto data = MonitorResponseDto.convertToDto(monitor);

        return DataResponse.<MonitorResponseDto>builder()
                .timestamp(LocalDateTime.now())
                .httpStatus(HttpStatus.OK)
                .message("Success saving monitor")
                .data(data)
                .build();

    }

    @Transactional
    public DataResponse<String> delete(Integer monitorId){
        log.info("deleting monitor with id: {}", monitorId);
        UserEntity currentUser = audit.getCurrentAuditor().orElseThrow(() ->  new BadRequestException("Request not authenticated"));
        log.info("current authenticated user: {}", currentUser.getUsername());

        MonitorEntity monitor = monitorRepository.findById(monitorId).orElseThrow(() -> new BadRequestException("Post Not Found"));

        log.info("deleting...");
        monitorRepository.delete(monitor);
        log.info("deleted.");

        return DataResponse.<String>builder()
                .timestamp(LocalDateTime.now())
                .httpStatus(HttpStatus.OK)
                .message("Success deleting monitor")
                .data("Monitor with id: " + monitorId + " deleted.")
                .build();
    }

}

