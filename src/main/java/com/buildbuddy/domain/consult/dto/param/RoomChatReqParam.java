package com.buildbuddy.domain.consult.dto.param;

import com.buildbuddy.util.spesification.ParamFilter;
import com.buildbuddy.util.spesification.QueryOperator;
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
public class RoomChatReqParam {

    private Integer userId;

    private Integer consultantId;

    private String search;

    @Builder.Default
    private boolean active = false;

    @Builder.Default
    private boolean pagination = false;

    @Builder.Default
    private Integer pageNo = 0;

    @Builder.Default
    private Integer pageSize = 10;

    @Builder.Default
    private String sortBy = "createdTime";

    @Builder.Default
    private String sortDirection = "asc";

}
