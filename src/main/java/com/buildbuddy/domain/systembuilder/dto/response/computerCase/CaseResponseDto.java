package com.buildbuddy.domain.systembuilder.dto.response.computerCase;

import com.buildbuddy.domain.systembuilder.entity.CaseEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class CaseResponseDto {

    @JsonProperty(value = "caseId")
    private Integer caseId;

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "manufacturer")
    private String manufacturer;

    @JsonProperty(value = "price")
    private BigDecimal price;

    @JsonProperty(value = "product_link")
    private String productLink;

    @JsonProperty(value = "side_panel")
    private String sidePanel;

    @JsonProperty(value = "cabinet_type")
    private String cabinetType;

    @JsonProperty(value = "color")
    private String color;

    @JsonProperty
    private String image;

    public static CaseResponseDto convertToDto(CaseEntity entity){
        byte[] image = entity.getImage();
        return CaseResponseDto.builder()
                .caseId(entity.getId())
                .name(entity.getName())
                .manufacturer(entity.getManufacturer())
                .price(entity.getPrice())
                .productLink(entity.getProductLink())
                .sidePanel(entity.getSidePanel())
                .cabinetType(entity.getCabinetType())
                .color(entity.getColor())
                .image(image != null ? Base64.getEncoder().encodeToString(image) : null)
                .build();
    }

}
