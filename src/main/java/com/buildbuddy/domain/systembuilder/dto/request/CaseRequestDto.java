package com.buildbuddy.domain.systembuilder.dto.request;

import com.buildbuddy.domain.systembuilder.entity.CaseEntity;
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
public class CaseRequestDto {

    private Integer id;

    private String name;

    private String manufacturer;

    private BigDecimal price;

    private String productLink;

    private String sidePanel;

    private String cabinetType;

    private String color;

    private String image;

    public static CaseEntity convertToEntity(CaseRequestDto dto){
        String image = dto.getImage();
        return CaseEntity.builder()
                .name(dto.getName())
                .manufacturer(dto.getManufacturer())
                .price(dto.getPrice())
                .productLink(dto.getProductLink())
                .sidePanel(dto.getSidePanel())
                .cabinetType(dto.getCabinetType())
                .color(dto.getColor())
                .image(image != null ? Base64.getDecoder().decode(image) : null)
                .build();
    }

}
