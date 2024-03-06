package com.buildbuddy.domain.consult.dto.response;

import com.buildbuddy.domain.consult.entity.ConsultTransactionModel;
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
public class ConsultTransactionDto {

    @JsonProperty("transactionId")
    private Integer transactionId;

    @JsonProperty("username")
    private String username;

    @JsonProperty("consultantName")
    private String consultantName;

    @JsonProperty("roomId")
    private String roomId;

    @JsonProperty("status")
    private String status;

    @JsonProperty("createdTime")
    @JsonFormat(pattern = "dd-MM-yyyy, HH:mm:ss")
    private LocalDateTime createdTime;

    @JsonProperty("lastUpdateTime")
    @JsonFormat(pattern = "dd-MM-yyyy, HH:mm:ss")
    private LocalDateTime lastUpdateTime;

    public static ConsultTransactionDto convertToDto(ConsultTransactionModel m){
        return ConsultTransactionDto.builder()
                .transactionId(m.getTransactionId())
                .username(m.getUsername())
                .consultantName(m.getConsultantName())
                .roomId(m.getRoomId())
                .status(m.getStatus())
                .createdTime(m.getCreatedTime())
                .lastUpdateTime(m.getLastUpdateTime())
                .build();
    }

}
