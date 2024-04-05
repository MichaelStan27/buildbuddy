package com.buildbuddy.domain.systembuilder.dto.request;

import com.buildbuddy.domain.systembuilder.entity.PowerSupplyEntity;
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
public class PowerSupplyRequestDto {

    private Integer id;
    private String name;
    private String manufacturer;
    private BigDecimal price;
    private String productLink;
    private String power;
    private String color;
    private String formFactor;
    private String efficiency;
    private String image;

    public static PowerSupplyEntity convertToEntity(PowerSupplyRequestDto dto){
        String image = dto.getImage();
        return PowerSupplyEntity.builder()
                .name(dto.getName())
                .manufacturer(dto.getManufacturer())
                .price(dto.getPrice())
                .productLink(dto.getProductLink())
                .power(dto.getPower())
                .color(dto.getColor())
                .formFactor(dto.getFormFactor())
                .efficiency(dto.getEfficiency())
                .image(image != null ? Base64.getDecoder().decode(image) : null)
                .build();
    }

}
