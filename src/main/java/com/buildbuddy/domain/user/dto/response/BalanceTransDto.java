package com.buildbuddy.domain.user.dto.response;

import com.buildbuddy.domain.user.entity.BalanceTransaction;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BalanceTransDto {

    @JsonProperty
    private String nominal;

    @JsonProperty
    private String status;

    @JsonProperty
    private String transactionType;

    @JsonProperty
    @JsonFormat(pattern = "dd-MM-yyyy, HH:mm:ss")
    private LocalDateTime lastUpdateTime;

    public static BalanceTransDto convertToDto(BalanceTransaction balanceTransaction){
        return BalanceTransDto.builder()
                .nominal(String.format("%,.2f", balanceTransaction.getNominal()))
                .status(balanceTransaction.getStatus())
                .transactionType(balanceTransaction.getTransactionType())
                .lastUpdateTime(balanceTransaction.getLastUpdateTime())
                .build();
    }
}
