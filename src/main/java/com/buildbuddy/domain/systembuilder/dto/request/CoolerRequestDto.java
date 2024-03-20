package com.buildbuddy.domain.systembuilder.dto.request;

import com.buildbuddy.domain.systembuilder.entity.CoolerEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    public static CoolerEntity convertToEntity(CoolerRequestDto dto){
        return CoolerEntity.builder()
                .name(dto.getName())
                .manufacturer(dto.getManufacturer())
                .price(dto.getPrice())
                .productLink(dto.getProductLink())
                .coolerType(dto.getCoolerType())
                .color(dto.getColor())
                .build();
    }

}
