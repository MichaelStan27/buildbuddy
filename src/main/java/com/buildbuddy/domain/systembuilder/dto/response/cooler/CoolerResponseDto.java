package com.buildbuddy.domain.systembuilder.dto.response.cooler;

import com.buildbuddy.domain.systembuilder.entity.CoolerEntity;
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
public class CoolerResponseDto {

    @JsonProperty(value = "coolerId")
    private Integer coolerId;

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "manufacturer")
    private String manufacturer;

    @JsonProperty(value = "price")
    private BigDecimal price;

    @JsonProperty(value = "product_link")
    private String productLink;

    @JsonProperty(value = "cooler_type")
    private String coolerType;

    @JsonProperty(value = "color")
    private String color;

    @JsonProperty
    private String image;

    public static CoolerResponseDto convertToDto(CoolerEntity entity){
        byte[] image = entity.getImage();
        return CoolerResponseDto.builder()
                .coolerId(entity.getId())
                .name(entity.getName())
                .manufacturer(entity.getManufacturer())
                .price(entity.getPrice())
                .productLink(entity.getProductLink())
                .coolerType(entity.getCoolerType())
                .color(entity.getColor())
                .image(image != null ? Base64.getEncoder().encodeToString(image) : null)
                .build();
    }
    
}
