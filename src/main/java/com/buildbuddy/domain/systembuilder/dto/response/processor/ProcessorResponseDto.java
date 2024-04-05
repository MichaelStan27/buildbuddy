package com.buildbuddy.domain.systembuilder.dto.response.processor;

import com.buildbuddy.domain.systembuilder.entity.ProcessorEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class ProcessorResponseDto {

    @JsonProperty(value = "processor_id")
    private Integer processorId;

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "manufacturer")
    private String manufacturer;

    @JsonProperty(value = "price")
    private BigDecimal price;

    @JsonProperty(value = "product_link")
    private String productLink;

    @JsonProperty(value = "socket")
    private String socket;

    @JsonProperty(value = "series")
    private String series;

    @JsonProperty(value = "core")
    private String core;

    @JsonProperty(value = "integrated_graphics")
    private String integratedGraphics;

    @JsonProperty(value = "micro_architecture")
    private String microArchitecture;

    @JsonProperty(value = "benchmark")
    private Integer benchmark;

    @JsonProperty
    private String image;

    public static ProcessorResponseDto convertToDto(ProcessorEntity entity){
        byte[] image = entity.getImage();
        return ProcessorResponseDto.builder()
                .processorId(entity.getId())
                .name(entity.getName())
                .manufacturer(entity.getManufacturer())
                .price(entity.getPrice())
                .productLink(entity.getProductLink())
                .socket(entity.getSocket())
                .series(entity.getSeries())
                .core(entity.getCore())
                .integratedGraphics(entity.getIntegratedGraphics())
                .microArchitecture(entity.getMicroArchitecture())
                .benchmark(entity.getBenchmark())
                .image(image != null ? Base64.getEncoder().encodeToString(image) : null)
                .build();
    }

    
}
