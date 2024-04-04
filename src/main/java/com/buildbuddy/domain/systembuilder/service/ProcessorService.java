package com.buildbuddy.domain.systembuilder.service;

import com.buildbuddy.audit.AuditorAwareImpl;
import com.buildbuddy.domain.systembuilder.dto.param.ProcessorRequestParam;
import com.buildbuddy.domain.systembuilder.dto.request.ProcessorRequestDto;
import com.buildbuddy.domain.systembuilder.dto.response.processor.ProcessorResponseDto;
import com.buildbuddy.domain.systembuilder.dto.response.processor.ProcessorResponseSchema;
import com.buildbuddy.domain.systembuilder.repository.ProcessorRepository;
import com.buildbuddy.domain.systembuilder.entity.ProcessorEntity;
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
public class ProcessorService {

    @Autowired
    private AuditorAwareImpl audit;

    @Autowired
    private ProcessorRepository processorRepository;

    @Autowired
    private SpecificationCreator<ProcessorEntity> specificationCreator;

    @Autowired
    private PaginationCreator paginationCreator;

    public DataResponse<ProcessorResponseSchema> get(ProcessorRequestParam requestParam){

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

        Page<ProcessorEntity> dataPage = getProcessorFromDB(requestParam, pageable);
        List<ProcessorEntity> processorList = dataPage.getContent();

        List<ProcessorResponseDto> processorResponseDtos = processorList.stream()
                .map(ProcessorResponseDto::convertToDto)
                .toList();

        ProcessorResponseSchema data = ProcessorResponseSchema.builder()
                .processorList(processorResponseDtos)
                .pageNo(pageNo)
                .pageSize(pageSize)
                .totalPages(dataPage.getTotalPages())
                .totalData(dataPage.getTotalElements())
                .build();

        return DataResponse.<ProcessorResponseSchema>builder()
                .timestamp(LocalDateTime.now())
                .httpStatus(HttpStatus.OK)
                .message("Success getting processor")
                .data(data)
                .build();

    }

    private Page<ProcessorEntity> getProcessorFromDB(ProcessorRequestParam param, Pageable pageable){
        log.info("Getting processor from DB...");

        List<ParamFilter> paramFilters = param.getFilters();
        Page<ProcessorEntity> data = null;

        if(paramFilters.isEmpty())
            data = processorRepository.findAll(pageable);
        else
            data = processorRepository.findAll(specificationCreator.getSpecification(paramFilters), pageable);

        return data;
    }

    @Transactional
    public DataResponse<ProcessorResponseDto> save(ProcessorRequestDto processorDto){
        log.info("processor: {}", processorDto);

        Integer id = processorDto.getId();
        ProcessorEntity processor = null;

        if(id != null){
            UserEntity currentUser = audit.getCurrentAuditor().orElseThrow(() ->  new BadRequestException("Request not authenticated"));
            log.info("current authenticated user: {}", currentUser.getUsername());

            processor = processorRepository.findById(id).orElseThrow(() -> new BadRequestException("processor not found"));
            processor.setCore(processorDto.getCore());
            processor.setName(processorDto.getName());
            processor.setManufacturer(processorDto.getManufacturer());
            processor.setPrice(processorDto.getPrice());
            processor.setProductLink(processorDto.getProductLink());
            processor.setSocket(processorDto.getSocket());
            processor.setSeries(processorDto.getSeries());
            processor.setMicroArchitecture(processorDto.getMicroArchitecture());
            processor.setIntegratedGraphics(processorDto.getIntegratedGraphics());
            processor.setBenchmark(processorDto.getBenchmark());
            processor.setImage(processorDto.getImage() != null ? Base64.getDecoder().decode(processorDto.getImage()) : null);
        }
        else{
            processor = ProcessorRequestDto.convertToEntity(processorDto);
        }

        log.info("saving...");
        processor = processorRepository.saveAndFlush(processor);
        log.info("saved..");

        ProcessorResponseDto data = ProcessorResponseDto.convertToDto(processor);

        return DataResponse.<ProcessorResponseDto>builder()
                .timestamp(LocalDateTime.now())
                .httpStatus(HttpStatus.OK)
                .message("Success saving processor")
                .data(data)
                .build();

    }

    @Transactional
    public DataResponse<String> delete(Integer processorId){
        log.info("deleting processor with id: {}", processorId);
        UserEntity currentUser = audit.getCurrentAuditor().orElseThrow(() ->  new BadRequestException("Request not authenticated"));
        log.info("current authenticated user: {}", currentUser.getUsername());

        ProcessorEntity processor = processorRepository.findById(processorId).orElseThrow(() -> new BadRequestException("Post Not Found"));

        log.info("deleting...");
        processorRepository.delete(processor);
        log.info("deleted.");

        return DataResponse.<String>builder()
                .timestamp(LocalDateTime.now())
                .httpStatus(HttpStatus.OK)
                .message("Success deleting processor")
                .data("Processor with id: " + processorId + " deleted.")
                .build();
    }

}
