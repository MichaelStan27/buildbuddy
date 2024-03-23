package com.buildbuddy.domain.systembuilder.dto.response.cooler;

import com.buildbuddy.domain.systembuilder.entity.CoolerEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CoolerResponseDto {

    @JsonProperty(value = "coolerId")
    private Integer coolerId;

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "manufacturer")
    private String manufacturer;

    @JsonProperty(value = "price")
    private String price;

    @JsonProperty(value = "product_link")
    private String productLink;

    @JsonProperty(value = "cooler_type")
    private String coolerType;

    @JsonProperty(value = "color")
    private String color;

    public static CoolerResponseDto convertToDto(CoolerEntity entity){
        return CoolerResponseDto.builder()
                .coolerId(entity.getId())
                .name(entity.getName())
                .manufacturer(entity.getManufacturer())
                .price(entity.getPrice())
                .productLink(entity.getProductLink())
                .coolerType(entity.getCoolerType())
                .color(entity.getColor())
                .build();
    }
    
}
