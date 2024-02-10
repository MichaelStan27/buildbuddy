package com.buildbuddy.domain.consult.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConsultantReqDto {

    @JsonProperty(value = "description")
    private String description;

    @JsonProperty(value = "fee")
    private BigDecimal fee;
}
