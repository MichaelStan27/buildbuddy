package com.buildbuddy.domain.systembuilder.dto.response.graphicsCard;

import com.buildbuddy.domain.systembuilder.entity.GraphicsCardEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class GraphicsCardResponseDto {

    @JsonProperty(value = "graphics_id")
    private Integer graphicsId;

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "manufacturer")
    private String manufacturer;

    @JsonProperty(value = "price")
    private BigDecimal price;

    @JsonProperty(value = "product_link")
    private String productLink;

    @JsonProperty(value = "chipset")
    private String chipset;

    @JsonProperty(value = "memory_size")
    private String memorySize;

    @JsonProperty(value = "interface")
    private String graphicsInterface;

    @JsonProperty(value = "benchmark")
    private Integer benchmark;

    @JsonProperty
    private String image;

    public static GraphicsCardResponseDto convertToDto(GraphicsCardEntity entity){
        byte[] image = entity.getImage();
        return GraphicsCardResponseDto.builder()
                .graphicsId(entity.getId())
                .name(entity.getName())
                .manufacturer(entity.getManufacturer())
                .price(entity.getPrice())
                .productLink(entity.getProductLink())
                .chipset(entity.getChipset())
                .memorySize(entity.getMemorySize())
                .graphicsInterface(entity.getGraphicsInterface())
                .benchmark(entity.getBenchmark())
                .image(image != null ? Base64.getEncoder().encodeToString(image) : null)
                .build();
    }

}
