package com.buildbuddy.domain.systembuilder.dto.request;

import com.buildbuddy.domain.systembuilder.entity.ProcessorEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProcessorRequestDto {

    private Integer id;
    private String name;
    private String manufacturer;
    private String price;
    private String productLink;
    private String socket;
    private String series;
    private String core;
    private String integratedGraphics;
    private String microArchitecture;
    private Integer benchmark;

    public static ProcessorEntity convertToEntity(ProcessorRequestDto dto){
        return ProcessorEntity.builder()
                .name(dto.getName())
                .manufacturer(dto.getManufacturer())
                .price(dto.getPrice())
                .productLink(dto.getProductLink())
                .socket(dto.getSocket())
                .series(dto.getSeries())
                .core(dto.getCore())
                .integratedGraphics(dto.getIntegratedGraphics())
                .microArchitecture(dto.getMicroArchitecture())
                .benchmark(dto.getBenchmark())
                .build();
    }

}
