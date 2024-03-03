package com.buildbuddy.domain.user.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BalanceReqDto {

    @JsonProperty("balanceTransactionId")
    private Integer balanceTransactionId;

    @JsonProperty("userId")
    private Integer userId;

    @JsonProperty("nominal")
    private BigDecimal nominal;

    @JsonProperty("status")
    private String status;
}
