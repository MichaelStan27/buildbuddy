package com.buildbuddy.domain.systembuilder.dto.request;

import com.buildbuddy.domain.systembuilder.entity.CoolerEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Base64;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CoolerRequestDto {

    private Integer id;

    private String name;

    private String manufacturer;

    private String price;

    private String productLink;

    private String coolerType;

    private String color;

    private String image;

    public static CoolerEntity convertToEntity(CoolerRequestDto dto){
        String image = dto.getImage();
        return CoolerEntity.builder()
                .name(dto.getName())
                .manufacturer(dto.getManufacturer())
                .price(dto.getPrice())
                .productLink(dto.getProductLink())
                .coolerType(dto.getCoolerType())
                .color(dto.getColor())
                .image(image != null ? Base64.getDecoder().decode(image) : null)
                .build();
    }

}
