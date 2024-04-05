package com.buildbuddy.domain.systembuilder.dto.request;

import com.buildbuddy.domain.systembuilder.entity.GraphicsCardEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Base64;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GraphicsCardRequestDto {

    private Integer id;
    private String name;
    private String manufacturer;
    private BigDecimal price;
    private String productLink;
    private String chipset;
    private String memorySize;
    private String graphicsInterface;
    private Integer benchmark;
    private String image;

    public static GraphicsCardEntity convertToEntity(GraphicsCardRequestDto dto){
        String image = dto.getImage();
        return GraphicsCardEntity.builder()
                .name(dto.getName())
                .manufacturer(dto.getManufacturer())
                .price(dto.getPrice())
                .productLink(dto.getProductLink())
                .chipset(dto.getChipset())
                .memorySize(dto.getMemorySize())
                .graphicsInterface(dto.getGraphicsInterface())
                .benchmark(dto.getBenchmark())
                .image(image != null ? Base64.getDecoder().decode(image) : null)
                .build();
    }

}
