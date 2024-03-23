package com.buildbuddy.domain.systembuilder.dto.request;

import com.buildbuddy.domain.systembuilder.entity.PowerSupplyEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PowerSupplyRequestDto {

    private Integer id;
    private String name;
    private String manufacturer;
    private String price;
    private String productLink;
    private String power;
    private String color;
    private String formFactor;
    private String efficiency;

    public static PowerSupplyEntity convertToEntity(PowerSupplyRequestDto dto){
        return PowerSupplyEntity.builder()
                .name(dto.getName())
                .manufacturer(dto.getManufacturer())
                .price(dto.getPrice())
                .productLink(dto.getProductLink())
                .power(dto.getPower())
                .color(dto.getColor())
                .formFactor(dto.getFormFactor())
                .efficiency(dto.getEfficiency())
                .build();
    }

}
