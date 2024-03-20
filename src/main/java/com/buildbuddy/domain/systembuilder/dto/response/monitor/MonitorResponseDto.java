package com.buildbuddy.domain.systembuilder.dto.response.monitor;

import com.buildbuddy.domain.systembuilder.entity.MonitorEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MonitorResponseDto {

    @JsonProperty(value = "monitor_id")
    private Integer monitorId;

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "manufacturer")
    private String manufacturer;

    @JsonProperty(value = "price")
    private String price;

    @JsonProperty(value = "product_link")
    private String productLink;

    @JsonProperty(value = "screen_size")
    private String screenSize;

    @JsonProperty(value = "resolution")
    private String resolution;

    @JsonProperty(value = "aspect_ratio")
    private String aspectRatio;

    @JsonProperty(value = "response_time")
    private String responseTime;

    @JsonProperty(value = "refresh_rate")
    private String refreshRate;

    @JsonProperty(value = "panel_type")
    private String panelType;

    public static MonitorResponseDto convertToDto(MonitorEntity entity){
        return MonitorResponseDto.builder()
                .monitorId(entity.getId())
                .name(entity.getName())
                .manufacturer(entity.getManufacturer())
                .price(entity.getPrice())
                .productLink(entity.getProductLink())
                .screenSize(entity.getScreenSize())
                .resolution(entity.getResolution())
                .aspectRatio(entity.getAspectRatio())
                .responseTime(entity.getResponseTime())
                .refreshRate(entity.getRefreshRate())
                .panelType(entity.getPanelType())
                .build();
    }

}
