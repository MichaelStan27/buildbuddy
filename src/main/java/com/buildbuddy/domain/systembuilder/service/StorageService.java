package com.buildbuddy.domain.systembuilder.service;

import com.buildbuddy.audit.AuditorAwareImpl;
import com.buildbuddy.domain.systembuilder.dto.param.StorageRequestParam;
import com.buildbuddy.domain.systembuilder.dto.request.StorageRequestDto;
import com.buildbuddy.domain.systembuilder.dto.response.storage.StorageResponseDto;
import com.buildbuddy.domain.systembuilder.dto.response.storage.StorageResponseSchema;
import com.buildbuddy.domain.systembuilder.repository.StorageRepository;
import com.buildbuddy.domain.systembuilder.entity.StorageEntity;
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
public class StorageService {

    @Autowired
    private AuditorAwareImpl audit;

    @Autowired
    private StorageRepository storageRepository;

    @Autowired
    private SpecificationCreator<StorageEntity> specificationCreator;

    @Autowired
    private PaginationCreator paginationCreator;

    public DataResponse<StorageResponseSchema> get(StorageRequestParam requestParam){

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

        Page<StorageEntity> dataPage = getStorageFromDB(requestParam, pageable);
        List<StorageEntity> storageList = dataPage.getContent();

        List<StorageResponseDto> storageResponseDtos = storageList.stream()
                .map(StorageResponseDto::convertToDto)
                .toList();

        StorageResponseSchema data = StorageResponseSchema.builder()
                .storageList(storageResponseDtos)
                .pageNo(pageNo)
                .pageSize(pageSize)
                .totalPages(dataPage.getTotalPages())
                .totalData(dataPage.getTotalElements())
                .build();

        return DataResponse.<StorageResponseSchema>builder()
                .timestamp(LocalDateTime.now())
                .httpStatus(HttpStatus.OK)
                .message("Success getting storage")
                .data(data)
                .build();

    }

    private Page<StorageEntity> getStorageFromDB(StorageRequestParam param, Pageable pageable){
        log.info("Getting storage from DB...");

        List<ParamFilter> paramFilters = param.getFilters();
        Page<StorageEntity> data = null;

        if(paramFilters.isEmpty())
            data = storageRepository.findAll(pageable);
        else
            data = storageRepository.findAll(specificationCreator.getSpecification(paramFilters), pageable);

        return data;
    }

    @Transactional
    public DataResponse<StorageResponseDto> save(StorageRequestDto storageDto){
        log.info("storage: {}", storageDto);

        Integer id = storageDto.getId();
        StorageEntity storage = null;

        if(id != null){
            UserEntity currentUser = audit.getCurrentAuditor().orElseThrow(() ->  new BadRequestException("Request not authenticated"));
            log.info("current authenticated user: {}", currentUser.getUsername());

            storage = storageRepository.findById(id).orElseThrow(() -> new BadRequestException("storage not found"));
            storage.setName(storageDto.getName());
            storage.setManufacturer(storageDto.getManufacturer());
            storage.setPrice(storageDto.getPrice());
            storage.setStorageInterface(storageDto.getStorageInterface());
            storage.setCapacity(storageDto.getCapacity());
            storage.setStorageType(storageDto.getStorageType());
            storage.setRpm(storageDto.getRpm());
            storage.setFormFactor(storageDto.getFormFactor());
            storage.setCacheMemory(storageDto.getCacheMemory());
            storage.setProductLink(storageDto.getProductLink());
            storage.setImage(storageDto.getImage() != null ? Base64.getDecoder().decode(storageDto.getImage()) : null);

        }
        else{
            storage = StorageRequestDto.convertToEntity(storageDto);
        }

        log.info("saving...");
        storage = storageRepository.saveAndFlush(storage);
        log.info("saved..");

        StorageResponseDto data = StorageResponseDto.convertToDto(storage);

        return DataResponse.<StorageResponseDto>builder()
                .timestamp(LocalDateTime.now())
                .httpStatus(HttpStatus.OK)
                .message("Success saving storage")
                .data(data)
                .build();

    }

    @Transactional
    public DataResponse<String> delete(Integer storageId){
        log.info("deleting storage with id: {}", storageId);
        UserEntity currentUser = audit.getCurrentAuditor().orElseThrow(() ->  new BadRequestException("Request not authenticated"));
        log.info("current authenticated user: {}", currentUser.getUsername());

        StorageEntity storage = storageRepository.findById(storageId).orElseThrow(() -> new BadRequestException("Post Not Found"));

        log.info("deleting...");
        storageRepository.delete(storage);
        log.info("deleted.");

        return DataResponse.<String>builder()
                .timestamp(LocalDateTime.now())
                .httpStatus(HttpStatus.OK)
                .message("Success deleting storage")
                .data("Storage with id: " + storageId + " deleted.")
                .build();
    }

}
