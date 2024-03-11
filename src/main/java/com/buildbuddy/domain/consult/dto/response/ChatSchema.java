package com.buildbuddy.domain.consult.dto.response;

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
public class ChatSchema {

    @JsonProperty
    private List<ChatDto> chatList;

    @JsonProperty
    private Integer pageNo;

    @JsonProperty
    private Integer pageSize;

    @JsonProperty
    private long totalPages;

    @JsonProperty
    private long totalData;

}
