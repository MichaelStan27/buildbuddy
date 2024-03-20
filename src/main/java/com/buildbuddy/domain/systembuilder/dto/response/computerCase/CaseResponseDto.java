package com.buildbuddy.domain.systembuilder.dto.response.computerCase;

import com.buildbuddy.domain.systembuilder.entity.CaseEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CaseResponseDto {

    @JsonProperty(value = "caseId")
    private Integer caseId;

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "manufacturer")
    private String manufacturer;

    @JsonProperty(value = "price")
    private String price;

    @JsonProperty(value = "product_link")
    private String productLink;

    @JsonProperty(value = "side_panel")
    private String sidePanel;

    @JsonProperty(value = "cabinet_type")
    private String cabinetType;

    @JsonProperty(value = "color")
    private String color;

    public static CaseResponseDto convertToDto(CaseEntity entity){
        return CaseResponseDto.builder()
                .caseId(entity.getId())
                .name(entity.getName())
                .manufacturer(entity.getManufacturer())
                .price(entity.getPrice())
                .productLink(entity.getProductLink())
                .sidePanel(entity.getSidePanel())
                .cabinetType(entity.getCabinetType())
                .color(entity.getColor())
                .build();
    }

}
