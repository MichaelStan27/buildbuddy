package com.buildbuddy.domain.systembuilder.dto.response.powersupply;

import com.buildbuddy.domain.systembuilder.entity.PowerSupplyEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Base64;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PowerSupplyResponseDto {

    @JsonProperty(value = "power_supply_id")
    private Integer powerSupplyId;

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "manufacturer")
    private String manufacturer;

    @JsonProperty(value = "price")
    private String price;

    @JsonProperty(value = "product_link")
    private String productLink;

    @JsonProperty(value = "power")
    private String power;

    @JsonProperty(value = "color")
    private String color;

    @JsonProperty(value = "form_factor")
    private String formFactor;

    @JsonProperty(value = "efficiency")
    private String efficiency;

    @JsonProperty
    private String image;

    public static PowerSupplyResponseDto convertToDto(PowerSupplyEntity entity){
        byte[] image = entity.getImage();
        return PowerSupplyResponseDto.builder()
                .powerSupplyId(entity.getId())
                .name(entity.getName())
                .manufacturer(entity.getManufacturer())
                .price(entity.getPrice())
                .productLink(entity.getProductLink())
                .power(entity.getPower())
                .color(entity.getColor())
                .formFactor(entity.getFormFactor())
                .efficiency(entity.getEfficiency())
                .image(image != null ? Base64.getEncoder().encodeToString(image) : null)
                .build();
    }

}
