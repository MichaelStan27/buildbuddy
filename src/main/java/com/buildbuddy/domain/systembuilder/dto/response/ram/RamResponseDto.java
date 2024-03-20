package com.buildbuddy.domain.systembuilder.dto.response.ram;

import com.buildbuddy.domain.systembuilder.entity.RamEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RamResponseDto {

    @JsonProperty(value = "ram_id")
    private Integer ramId;

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "manufacturer")
    private String manufacturer;

    @JsonProperty(value = "price")
    private String price;

    @JsonProperty(value = "product_link")
    private String productLink;

    @JsonProperty(value = "ram_size")
    private Integer ramSize;

    @JsonProperty(value = "ram_quantity")
    private Integer ramQuantity;

    @JsonProperty(value = "ram_speed")
    private String ramSpeed;

    @JsonProperty(value = "ram_type")
    private String ramType;

    @JsonProperty(value = "cas_latency")
    private String casLatency;

    public static RamResponseDto convertToDto(RamEntity entity){
        return RamResponseDto.builder()
                .ramId(entity.getId())
                .name(entity.getName())
                .manufacturer(entity.getManufacturer())
                .price(entity.getPrice())
                .productLink(entity.getProductLink())
                .ramSize(entity.getRamSize())
                .ramQuantity(entity.getRamQuantity())
                .ramSpeed(entity.getRamSpeed())
                .ramType(entity.getRamType())
                .casLatency(entity.getCasLatency())
                .build();
    }

}
