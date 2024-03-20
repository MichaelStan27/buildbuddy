package com.buildbuddy.domain.systembuilder.dto.response.monitor;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MonitorResponseSchema {

    @JsonProperty(value = "monitorList")
    private List<MonitorResponseDto> monitorList;

    @JsonProperty(value = "pageNo")
    private Integer pageNo;

    @JsonProperty(value = "pageSize")
    private Integer pageSize;

    @JsonProperty(value = "totalPages")
    private long totalPages;

    @JsonProperty(value = "totalData")
    private long totalData;

}
