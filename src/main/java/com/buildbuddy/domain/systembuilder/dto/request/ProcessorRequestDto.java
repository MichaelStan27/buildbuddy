package com.buildbuddy.domain.systembuilder.dto.request;

import com.buildbuddy.domain.systembuilder.entity.ProcessorEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Base64;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProcessorRequestDto {

    private Integer id;
    private String name;
    private String manufacturer;
    private BigDecimal price;
    private String productLink;
    private String socket;
    private String series;
    private String core;
    private String integratedGraphics;
    private String microArchitecture;
    private Integer benchmark;
    private String image;

    public static ProcessorEntity convertToEntity(ProcessorRequestDto dto){
        String image = dto.getImage();
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
                .image(image != null ? Base64.getDecoder().decode(image) : null)
                .build();
    }

}
