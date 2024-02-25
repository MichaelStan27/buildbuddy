package com.buildbuddy.domain.consult.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionReqDto {

    @JsonProperty("transactionId")
    private Integer transactionId;

    @JsonProperty("userId")
    private Integer userId;

    @JsonProperty("consultantId")
    private Integer consultantId;

    @JsonProperty("status")
    private String status;
}
