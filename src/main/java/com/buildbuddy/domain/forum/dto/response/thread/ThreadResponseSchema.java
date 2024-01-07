package com.buildbuddy.domain.forum.dto.response.thread;

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
public class ThreadResponseSchema {

    @JsonProperty(value = "threadList")
    private List<ThreadResponseDto> threadList;

    @JsonProperty(value = "pageNo")
    private Integer pageNo;

    @JsonProperty(value = "pageSize")
    private Integer pageSize;

    @JsonProperty(value = "totalPages")
    private long totalPages;

    @JsonProperty(value = "totalData")
    private long totalData;
}
