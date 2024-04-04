package com.buildbuddy.domain.systembuilder.dto.request;

import com.buildbuddy.domain.systembuilder.entity.GameEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Base64;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameRequestDto {

    private Integer id;
    private String name;
    private Integer memory;
    private Integer graphicsCardBenchmark;
    private String graphicsCard;
    private Integer cpuBenchmark;
    private String cpu;
    private Integer fileSize;
    private String image;

    public static GameEntity convertToEntity(GameRequestDto dto){
        String image = dto.getImage();
        return GameEntity.builder()
                .name(dto.getName())
                .memory(dto.getMemory())
                .graphicsCardBenchmark(dto.getGraphicsCardBenchmark())
                .graphicsCard(dto.getGraphicsCard())
                .cpuBenchmark(dto.getCpuBenchmark())
                .cpu(dto.getCpu())
                .fileSize(dto.getFileSize())
                .image(image != null ? Base64.getDecoder().decode(image) : null)
                .build();
    }

}
