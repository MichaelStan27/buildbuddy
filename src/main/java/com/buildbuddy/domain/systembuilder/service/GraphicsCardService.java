package com.buildbuddy.domain.systembuilder.service;

import com.buildbuddy.audit.AuditorAwareImpl;
import com.buildbuddy.domain.systembuilder.dto.param.GraphicsCardRequestParam;
import com.buildbuddy.domain.systembuilder.dto.request.GraphicsCardRequestDto;
import com.buildbuddy.domain.systembuilder.dto.response.graphicsCard.GraphicsCardResponseDto;
import com.buildbuddy.domain.systembuilder.dto.response.graphicsCard.GraphicsCardResponseDto;
import com.buildbuddy.domain.systembuilder.dto.response.graphicsCard.GraphicsCardResponseSchema;
import com.buildbuddy.domain.systembuilder.entity.GraphicsCardEntity;
import com.buildbuddy.domain.systembuilder.entity.GraphicsCardEntity;
import com.buildbuddy.domain.systembuilder.repository.GraphicsCardRepository;
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
public class GraphicsCardService {

    @Autowired
    private AuditorAwareImpl audit;

    @Autowired
    private GraphicsCardRepository graphicsCardRepository;

    @Autowired
    private SpecificationCreator<GraphicsCardEntity> specificationCreator;

    @Autowired
    private PaginationCreator paginationCreator;

    public DataResponse<GraphicsCardResponseSchema> get(GraphicsCardRequestParam requestParam){

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

        Page<GraphicsCardEntity> dataPage = getGraphicsCardFromDB(requestParam, pageable);
        List<GraphicsCardEntity> graphicsCardList = dataPage.getContent();

        List<GraphicsCardResponseDto> graphicsCardResponseDtos = graphicsCardList.stream()
                .map(GraphicsCardResponseDto::convertToDto)
                .toList();

        GraphicsCardResponseSchema data = GraphicsCardResponseSchema.builder()
                .graphicsCardList(graphicsCardResponseDtos)
                .pageNo(pageNo)
                .pageSize(pageSize)
                .totalPages(dataPage.getTotalPages())
                .totalData(dataPage.getTotalElements())
                .build();

        return DataResponse.<GraphicsCardResponseSchema>builder()
                .timestamp(LocalDateTime.now())
                .httpStatus(HttpStatus.OK)
                .message("Success getting graphicsCard")
                .data(data)
                .build();
    }

    private Page<GraphicsCardEntity> getGraphicsCardFromDB(GraphicsCardRequestParam param, Pageable pageable){

        log.info("Getting graphics card from DB...");

        List<ParamFilter> paramFilters = param.getFilters();
        Page<GraphicsCardEntity> data = null;

        if(paramFilters.isEmpty())
            data = graphicsCardRepository.findAll(pageable);
        else
            data = graphicsCardRepository.findAll(specificationCreator.getSpecification(paramFilters), pageable);

        return data;

    }

    @Transactional
    public DataResponse<GraphicsCardResponseDto> save(GraphicsCardRequestDto graphicsCardDto){
        log.info("graphicsCard: {}", graphicsCardDto);

        Integer id = graphicsCardDto.getId();
        GraphicsCardEntity graphicsCard = null;

        if(id != null){
            UserEntity currentUser = audit.getCurrentAuditor().orElseThrow(() ->  new BadRequestException("Request not authenticated"));
            log.info("current authenticated user: {}", currentUser.getUsername());

            graphicsCard = graphicsCardRepository.findById(id).orElseThrow(() -> new BadRequestException("graphicsCard not found"));
            graphicsCard.setName(graphicsCardDto.getName());
            graphicsCard.setGraphicsInterface(graphicsCardDto.getGraphicsInterface());
            graphicsCard.setManufacturer(graphicsCardDto.getManufacturer());
            graphicsCard.setPrice(graphicsCardDto.getPrice());
            graphicsCard.setChipset(graphicsCardDto.getChipset());
            graphicsCard.setProductLink(graphicsCardDto.getProductLink());
            graphicsCard.setMemorySize(graphicsCardDto.getMemorySize());
            graphicsCard.setBenchmark(graphicsCardDto.getBenchmark());
            graphicsCard.setImage(graphicsCardDto.getImage() != null ? Base64.getDecoder().decode(graphicsCardDto.getImage()) : null);

        }
        else{
            graphicsCard = GraphicsCardRequestDto.convertToEntity(graphicsCardDto);
        }

        log.info("saving...");
        graphicsCard = graphicsCardRepository.saveAndFlush(graphicsCard);
        log.info("saved..");

        GraphicsCardResponseDto data = GraphicsCardResponseDto.convertToDto(graphicsCard);

        return DataResponse.<GraphicsCardResponseDto>builder()
                .timestamp(LocalDateTime.now())
                .httpStatus(HttpStatus.OK)
                .message("Success saving graphicsCard")
                .data(data)
                .build();

    }

    @Transactional
    public DataResponse<String> delete(Integer graphicsCardId){
        log.info("deleting graphicsCard with id: {}", graphicsCardId);
        UserEntity currentUser = audit.getCurrentAuditor().orElseThrow(() ->  new BadRequestException("Request not authenticated"));
        log.info("current authenticated user: {}", currentUser.getUsername());

        GraphicsCardEntity graphicsCard = graphicsCardRepository.findById(graphicsCardId).orElseThrow(() -> new BadRequestException("Post Not Found"));

        log.info("deleting...");
        graphicsCardRepository.delete(graphicsCard);
        log.info("deleted.");

        return DataResponse.<String>builder()
                .timestamp(LocalDateTime.now())
                .httpStatus(HttpStatus.OK)
                .message("Success deleting graphicsCard")
                .data("GraphicsCard with id: " + graphicsCardId + " deleted.")
                .build();
    }

}
