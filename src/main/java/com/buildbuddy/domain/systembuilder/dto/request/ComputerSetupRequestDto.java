package com.buildbuddy.domain.systembuilder.dto.request;

import com.buildbuddy.domain.systembuilder.entity.ComputerSetupEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComputerSetupRequestDto {

    private Integer id;
    private Integer caseId;
    private Integer coolerId;
    private Integer graphicsCardId;
    private Integer monitorId;
    private Integer motherboardId;
    private Integer powersupplyId;
    private Integer processorId;
    private Integer ramId;
    private Integer storageId;

    public static ComputerSetupEntity convertToEntity(ComputerSetupRequestDto dto){
        return ComputerSetupEntity.builder()
                .caseId(dto.getCaseId())
                .coolerId(dto.getCoolerId())
                .graphicsCardId(dto.getGraphicsCardId())
                .monitorId(dto.getMonitorId())
                .motherboardId(dto.getMotherboardId())
                .powersupplyId(dto.getPowersupplyId())
                .processorId(dto.getProcessorId())
                .ramId(dto.getRamId())
                .storageId(dto.getStorageId())
                .build();
    }



}
