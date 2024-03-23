package com.buildbuddy.domain.systembuilder.service;

import com.buildbuddy.audit.AuditorAwareImpl;
import com.buildbuddy.domain.systembuilder.dto.param.CoolerRequestParam;
import com.buildbuddy.domain.systembuilder.dto.request.CoolerRequestDto;
import com.buildbuddy.domain.systembuilder.dto.response.cooler.CoolerResponseDto;
import com.buildbuddy.domain.systembuilder.dto.response.cooler.CoolerResponseSchema;
import com.buildbuddy.domain.systembuilder.entity.CoolerEntity;
import com.buildbuddy.domain.systembuilder.repository.CoolerRepository;
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
public class CoolerService {

    @Autowired
    private AuditorAwareImpl audit;

    @Autowired
    private CoolerRepository coolerRepository;

    @Autowired
    private SpecificationCreator<CoolerEntity> specificationCreator;

    @Autowired
    private PaginationCreator paginationCreator ;

    public DataResponse<CoolerResponseSchema> get(CoolerRequestParam requestParam){

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

        Page<CoolerEntity> dataPage = getCoolerFromDB(requestParam, pageable);
        List<CoolerEntity> coolerList = dataPage.getContent();

        List<CoolerResponseDto> coolerResponseDtos = coolerList.stream()
                .map(CoolerResponseDto::convertToDto)
                .toList();

        CoolerResponseSchema data = CoolerResponseSchema.builder()
                .coolerList(coolerResponseDtos)
                .pageNo(pageNo)
                .pageSize(pageSize)
                .totalPages(dataPage.getTotalPages())
                .totalData(dataPage.getTotalElements())
                .build();

        return DataResponse.<CoolerResponseSchema>builder()
                .timestamp(LocalDateTime.now())
                .httpStatus(HttpStatus.OK)
                .message("Success getting cooler")
                .data(data)
                .build();

    }

    private Page<CoolerEntity> getCoolerFromDB(CoolerRequestParam param, Pageable pageable){
        log.info("Getting cooler from DB...");

        List<ParamFilter> paramFilters = param.getFilters();
        Page<CoolerEntity> data = null;

        if(paramFilters.isEmpty())
            data = coolerRepository.findAll(pageable);
        else
            data = coolerRepository.findAll(specificationCreator.getSpecification(paramFilters), pageable);

        return data;

    }

    @Transactional
    public DataResponse<CoolerResponseDto> save(CoolerRequestDto coolerDto){
        log.info("cooler: {}", coolerDto);

        Integer id = coolerDto.getId();
        CoolerEntity cooler = null;

        if(id != null){
            UserEntity currentUser = audit.getCurrentAuditor().orElseThrow(() ->  new BadRequestException("Request not authenticated"));
            log.info("current authenticated user: {}", currentUser.getUsername());

            cooler = coolerRepository.findById(id).orElseThrow(() -> new BadRequestException("cooler not found"));
            cooler.setName(coolerDto.getName());
            cooler.setCoolerType(coolerDto.getCoolerType());
            cooler.setManufacturer(coolerDto.getManufacturer());
            cooler.setPrice(coolerDto.getPrice());
            cooler.setColor(coolerDto.getColor());
            cooler.setProductLink(coolerDto.getProductLink());

        }
        else{
            cooler = CoolerRequestDto.convertToEntity(coolerDto);
        }

        log.info("saving...");
        cooler = coolerRepository.saveAndFlush(cooler);
        log.info("saved..");

        CoolerResponseDto data = CoolerResponseDto.convertToDto(cooler);

        return DataResponse.<CoolerResponseDto>builder()
                .timestamp(LocalDateTime.now())
                .httpStatus(HttpStatus.OK)
                .message("Success saving cooler")
                .data(data)
                .build();

    }

    @Transactional
    public DataResponse<String> delete(Integer coolerId){
        log.info("deleting cooler with id: {}", coolerId);
        UserEntity currentUser = audit.getCurrentAuditor().orElseThrow(() ->  new BadRequestException("Request not authenticated"));
        log.info("current authenticated user: {}", currentUser.getUsername());

        CoolerEntity cooler = coolerRepository.findById(coolerId).orElseThrow(() -> new BadRequestException("Post Not Found"));

        log.info("deleting...");
        coolerRepository.delete(cooler);
        log.info("deleted.");

        return DataResponse.<String>builder()
                .timestamp(LocalDateTime.now())
                .httpStatus(HttpStatus.OK)
                .message("Success deleting cooler")
                .data("Cooler with id: " + coolerId + " deleted.")
                .build();
    }

}
