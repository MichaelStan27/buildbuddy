package com.buildbuddy.domain.systembuilder.dto.response.computerSetup;

import com.buildbuddy.domain.systembuilder.entity.ComputerSetupEntity;
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
public class ComputerSetupResponseDto {

    @JsonProperty(value = "computerSetupId")
    private Integer computerSetupId;

    @JsonProperty(value = "username")
    private String username;

    @JsonProperty(value = "computerCaseId")
    private Integer computerCaseId;

    @JsonProperty(value = "coolerId")
    private Integer coolerId;

    @JsonProperty(value = "graphicsCardId")
    private Integer graphicsCardId;

    @JsonProperty(value = "monitorId")
    private Integer monitorId;

    @JsonProperty(value = "motherboardId")
    private Integer motherboardId;

    @JsonProperty(value = "powersupplyId")
    private Integer powersupplyId;

    @JsonProperty(value = "processorId")
    private Integer processorId;

    @JsonProperty(value = "ramId")
    private Integer ramId;

    @JsonProperty(value = "storageId")
    private Integer storageId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty(value = "createdTime")
    private LocalDateTime createdTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty(value = "lastUpdateTime")
    private LocalDateTime lastUpdateTime;

    public static ComputerSetupResponseDto convertToDto(ComputerSetupEntity entity){
        return ComputerSetupResponseDto.builder()
                .computerSetupId(entity.getId())
                .computerCaseId(entity.getCaseId())
                .coolerId(entity.getCoolerId())
                .graphicsCardId(entity.getGraphicsCardId())
                .monitorId(entity.getMonitorId())
                .motherboardId(entity.getMotherboardId())
                .powersupplyId(entity.getPowersupplyId())
                .processorId(entity.getProcessorId())
                .ramId(entity.getRamId())
                .storageId(entity.getStorageId())
                .username(entity.getUser().getUsername())
                .createdTime(entity.getCreatedTime())
                .lastUpdateTime(entity.getLastUpdateTime())
                .build();
    }


}
