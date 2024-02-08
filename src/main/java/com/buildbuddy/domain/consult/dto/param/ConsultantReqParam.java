package com.buildbuddy.domain.consult.dto.param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConsultantReqParam {

    private String username;

    private String gender;

    private String description;

    private Integer available;

    // Pagination
    @Builder.Default
    private boolean pagination = false;

    @Builder.Default
    private Integer pageNo = 0;

    @Builder.Default
    private Integer pageSize = 10;

    @Builder.Default
    private String sortBy = "username";

    @Builder.Default
    private String sortDirection = "asc";

}
