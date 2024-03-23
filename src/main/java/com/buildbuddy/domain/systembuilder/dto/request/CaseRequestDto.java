package com.buildbuddy.domain.systembuilder.dto.request;

import com.buildbuddy.domain.systembuilder.entity.CaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CaseRequestDto {

    private Integer id;

    private String name;

    private String manufacturer;

    private String price;

    private String productLink;

    private String sidePanel;

    private String cabinetType;

    private String color;

    public static CaseEntity convertToEntity(CaseRequestDto dto){
        return CaseEntity.builder()
                .name(dto.getName())
                .manufacturer(dto.getManufacturer())
                .price(dto.getPrice())
                .productLink(dto.getProductLink())
                .sidePanel(dto.getSidePanel())
                .cabinetType(dto.getCabinetType())
                .color(dto.getColor())
                .build();
    }

}
