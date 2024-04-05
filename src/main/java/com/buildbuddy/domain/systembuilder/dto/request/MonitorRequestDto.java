package com.buildbuddy.domain.systembuilder.dto.request;

import com.buildbuddy.domain.systembuilder.entity.MonitorEntity;
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
public class MonitorRequestDto {

    private Integer id;
    private String name;
    private String manufacturer;
    private BigDecimal price;
    private String productLink;
    private String screenSize;
    private String resolution;
    private String aspectRatio;
    private String responseTime;
    private String refreshRate;
    private String panelType;
    private String image;

    public static MonitorEntity convertToEntity(MonitorRequestDto dto){
        String image = dto.getImage();
        return MonitorEntity.builder()
                .name(dto.getName())
                .manufacturer(dto.getManufacturer())
                .price(dto.getPrice())
                .productLink(dto.getProductLink())
                .screenSize(dto.getScreenSize())
                .resolution(dto.getResolution())
                .aspectRatio(dto.getAspectRatio())
                .responseTime(dto.getResponseTime())
                .refreshRate(dto.getRefreshRate())
                .panelType(dto.getPanelType())
                .image(image != null ? Base64.getDecoder().decode(image) : null)
                .build();
    }

}
