package com.buildbuddy.domain.consult.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {

    @JsonProperty("transactionId")
    private Integer transactionId;

    @JsonProperty("status")
    private String status;

    @JsonProperty("roomId")
    private String roomId;
}
