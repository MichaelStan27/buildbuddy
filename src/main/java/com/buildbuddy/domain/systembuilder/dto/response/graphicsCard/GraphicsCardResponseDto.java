package com.buildbuddy.domain.systembuilder.dto.response.graphicsCard;

import com.buildbuddy.domain.systembuilder.entity.GraphicsCardEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String price;

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

    public static GraphicsCardResponseDto convertToDto(GraphicsCardEntity entity){
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
                .build();
    }

}
