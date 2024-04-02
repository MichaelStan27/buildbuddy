package com.buildbuddy.domain.forum.dto.param;

import com.buildbuddy.util.spesification.ParamFilter;
import com.buildbuddy.util.spesification.QueryOperator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequestParam {

    private String search;

    // Pagination
    @Builder.Default
    private boolean pagination = false;

    @Builder.Default
    private Integer pageNo = 0;

    @Builder.Default
    private Integer pageSize = 10;

    @Builder.Default
    private String sortBy = "lastUpdateTime";

    @Builder.Default
    private String sortDirection = "asc";

}
