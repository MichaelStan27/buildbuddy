package com.buildbuddy.domain.consult.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailDto {

    private String username;
    private String to;
    private String body;

}
