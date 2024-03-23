package com.buildbuddy.domain.systembuilder.dto.request;

import com.buildbuddy.domain.systembuilder.entity.RamEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RamRequestDto {

    private Integer id;
    private String name;
    private String manufacturer;
    private String price;
    private String productLink;
    private Integer ramSize;
    private Integer ramQuantity;
    private String ramSpeed;
    private String ramType;
    private String casLatency;

    public static RamEntity convertToEntity(RamRequestDto dto){
        return RamEntity.builder()
                .name(dto.getName())
                .manufacturer(dto.getManufacturer())
                .price(dto.getPrice())
                .productLink(dto.getProductLink())
                .ramSize(dto.getRamSize())
                .ramQuantity(dto.getRamQuantity())
                .ramSpeed(dto.getRamSpeed())
                .ramType(dto.getRamType())
                .casLatency(dto.getCasLatency())
                .build();
    }

}
