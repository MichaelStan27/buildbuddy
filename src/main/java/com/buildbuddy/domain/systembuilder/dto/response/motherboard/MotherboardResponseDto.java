package com.buildbuddy.domain.systembuilder.dto.response.motherboard;

import com.buildbuddy.domain.systembuilder.entity.MotherboardEntity;
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
public class MotherboardResponseDto {

    @JsonProperty(value = "motherboard_id")
    private Integer motherboardId;

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

    @JsonProperty(value = "socket_type")
    private String socketType;

    @JsonProperty(value = "form_factor")
    private String formFactor;

    @JsonProperty(value = "memory_slots")
    private String memorySlots;

    @JsonProperty(value = "max_memory")
    private String maxMemory;

    @JsonProperty
    private String image;

    @JsonProperty
    private String ramType;

    public static MotherboardResponseDto convertToDto(MotherboardEntity entity){
        byte[] image = entity.getImage();
        return MotherboardResponseDto.builder()
                .motherboardId(entity.getId())
                .name(entity.getName())
                .manufacturer(entity.getManufacturer())
                .price(entity.getPrice())
                .productLink(entity.getProductLink())
                .chipset(entity.getChipset())
                .socketType(entity.getSocketType())
                .formFactor(entity.getFormFactor())
                .memorySlots(entity.getMemorySlots())
                .maxMemory(entity.getMaxMemory())
                .ramType(entity.getRamType())
                .image(image != null ? Base64.getEncoder().encodeToString(image) : null)
                .build();
    }

}
