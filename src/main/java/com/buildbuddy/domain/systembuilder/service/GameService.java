package com.buildbuddy.domain.systembuilder.service;

import com.buildbuddy.audit.AuditorAwareImpl;
import com.buildbuddy.domain.systembuilder.dto.param.GameRequestParam;
import com.buildbuddy.domain.systembuilder.dto.request.GameRequestDto;
import com.buildbuddy.domain.systembuilder.dto.response.game.GameResponseDto;
import com.buildbuddy.domain.systembuilder.dto.response.game.GameResponseSchema;
import com.buildbuddy.domain.systembuilder.entity.GameEntity;
import com.buildbuddy.domain.systembuilder.repository.GameRepository;
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
public class GameService {
    
    @Autowired
    private AuditorAwareImpl audit;
    
    @Autowired
    private SpecificationCreator<GameEntity> specificationCreator;
    
    @Autowired
    private GameRepository gameRepository;
    
    @Autowired
    private PaginationCreator paginationCreator;
    
    public DataResponse<GameResponseSchema> get(GameRequestParam requestParam){

        boolean isPaginated = requestParam.isPagination();
        Integer pageNo = requestParam.getPageNo();
        Integer pageSize = requestParam.getPageSize();
        String sortBy = requestParam.getSortBy();
        String sortDirection = requestParam.getSortDirection();

        Sort sort = paginationCreator.createSort(sortDirection, sortBy);

        Pageable pageable = paginationCreator.createPageable(isPaginated, sort, pageNo, pageSize);

        Page<GameEntity> dataPage = getGameFormDB(requestParam, pageable);
        List<GameEntity> gameList = dataPage.getContent();
        
        List<GameResponseDto> gameResponseDtos = gameList.stream()
                .map(GameResponseDto::convertToDto)
                .toList();
        
        GameResponseSchema data = GameResponseSchema.builder()
                .gameList(gameResponseDtos)
                .pageNo(pageNo)
                .pageSize(pageSize)
                .totalPages(dataPage.getTotalPages())
                .totalData(dataPage.getTotalElements())
                .build();
        
        return DataResponse.<GameResponseSchema>builder()
                .timestamp(LocalDateTime.now())
                .httpStatus(HttpStatus.OK)
                .message("Success getting game")
                .data(data)
                .build();

    }
    
    public Page<GameEntity> getGameFormDB(GameRequestParam param, Pageable pageable){
        
        log.info("Getting game form DB...");

        List<ParamFilter> paramFilters = param.getFilters();
        Page<GameEntity> data = null;
        
        if(paramFilters.isEmpty())
            data = gameRepository.findAll(pageable);
        else 
            data = gameRepository.findAll(specificationCreator.getSpecification(paramFilters), pageable);
        
        return data;
        
    }

    @Transactional
    public DataResponse<GameResponseDto> save(GameRequestDto gameDto){
        log.info("game: {}", gameDto);

        Integer id = gameDto.getId();
        GameEntity game = null;

        if(id != null){
            UserEntity currentUser = audit.getCurrentAuditor().orElseThrow(() ->  new BadRequestException("Request not authenticated"));
            log.info("current authenticated user: {}", currentUser.getUsername());

            game = gameRepository.findByIdAndUserId(id, currentUser.getId()).orElseThrow(() -> new BadRequestException("Game Not Found"));
            game.setName(gameDto.getName());
            game.setMemory(gameDto.getMemory());
            game.setGraphicsCardBenchmark(gameDto.getGraphicsCardBenchmark());
            game.setGraphicsCard(gameDto.getGraphicsCard());
            game.setCpuBenchmark(gameDto.getCpuBenchmark());
            game.setCpu(gameDto.getCpu());
            game.setFileSize(gameDto.getFileSize());
            game.setImage(gameDto.getImage() != null ? Base64.getDecoder().decode(gameDto.getImage()) : null);
        }
        else{
            game = GameRequestDto.convertToEntity(gameDto);
        }

        log.info("saving...");
        game = gameRepository.saveAndFlush(game);
        log.info("saved...");

        GameResponseDto data = GameResponseDto.convertToDto(game);

        return DataResponse.<GameResponseDto>builder()
                .timestamp(LocalDateTime.now())
                .httpStatus(HttpStatus.OK)
                .message("Success saving game")
                .data(data)
                .build();
    }

    @Transactional
    public DataResponse<String> delete(Integer gameId){
        log.info("deleting game with id: {}", gameId);
        UserEntity currentUser = audit.getCurrentAuditor().orElseThrow(() ->  new BadRequestException("Request not authenticated"));
        log.info("current authenticated user: {}", currentUser.getUsername());

        GameEntity game = gameRepository.findByIdAndUserId(gameId, currentUser.getId()).orElseThrow(() -> new BadRequestException("Game Not Found"));

        log.info("deleting...");
        gameRepository.delete(game);
        log.info("deleted.");

        return DataResponse.<String>builder()
                .timestamp(LocalDateTime.now())
                .httpStatus(HttpStatus.OK)
                .message("Success deleting game")
                .data("Game with id: " + gameId + " deleted.")
                .build();
    }
    
}
