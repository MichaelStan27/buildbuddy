package com.buildbuddy.domain.systembuilder.dto.request;

import com.buildbuddy.domain.systembuilder.entity.MotherboardEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MotherboardRequestDto {

    private Integer id;
    private String name;
    private String manufacturer;
    private String price;
    private String productLink;
    private String chipset;
    private String socketType;
    private String formFactor;
    private String memorySlots;
    private String maxMemory;

    public static MotherboardEntity convertToEntity(MotherboardRequestDto dto){
        return MotherboardEntity.builder()
                .name(dto.getName())
                .manufacturer(dto.getManufacturer())
                .price(dto.getPrice())
                .productLink(dto.getProductLink())
                .chipset(dto.getChipset())
                .socketType(dto.getSocketType())
                .formFactor(dto.getFormFactor())
                .memorySlots(dto.getMemorySlots())
                .maxMemory(dto.getMaxMemory())
                .build();
    }

}
