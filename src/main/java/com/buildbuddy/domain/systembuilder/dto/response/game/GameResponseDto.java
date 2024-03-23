package com.buildbuddy.domain.systembuilder.dto.response.game;

import com.buildbuddy.domain.systembuilder.entity.GameEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameResponseDto {

    @JsonProperty(value = "game_id")
    private Integer gameId;

    @JsonProperty(value = "username")
    private String username;
    
    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "memory")
    private Integer memory;

    @JsonProperty(value = "graphics_card_benchmark")
    private Integer graphicsCardBenchmark;

    @JsonProperty(value = "graphics_card")
    private String graphicsCard;

    @JsonProperty(value = "cpu_benchmark")
    private Integer cpuBenchmark;

    @JsonProperty(value = "cpu")
    private String cpu;

    @JsonProperty(value = "file_size")
    private Integer fileSize;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty(value = "createdTime")
    private LocalDateTime createdTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty(value = "lastUpdateTime")
    private LocalDateTime lastUpdateTime;

    public static GameResponseDto convertToDto(GameEntity entity){
        return GameResponseDto.builder()
                .gameId(entity.getId())
                .username(entity.getUser().getUsername())
                .name(entity.getName())
                .graphicsCardBenchmark(entity.getGraphicsCardBenchmark())
                .graphicsCard(entity.getGraphicsCard())
                .cpuBenchmark(entity.getCpuBenchmark())
                .cpu(entity.getCpu())
                .fileSize(entity.getFileSize())
                .createdTime(entity.getCreatedTime())
                .lastUpdateTime(entity.getLastUpdateTime())
                .build();
    }

}
