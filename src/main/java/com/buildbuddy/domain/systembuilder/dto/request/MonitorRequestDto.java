package com.buildbuddy.domain.systembuilder.dto.request;

import com.buildbuddy.domain.systembuilder.entity.MonitorEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MonitorRequestDto {

    private Integer id;
    private String name;
    private String manufacturer;
    private String price;
    private String productLink;
    private String screenSize;
    private String resolution;
    private String aspectRatio;
    private String responseTime;
    private String refreshRate;
    private String panelType;

    public static MonitorEntity convertToEntity(MonitorRequestDto dto){
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
                .build();
    }

}
