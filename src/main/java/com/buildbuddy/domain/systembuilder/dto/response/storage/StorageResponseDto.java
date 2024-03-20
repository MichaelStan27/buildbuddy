package com.buildbuddy.domain.systembuilder.dto.response.storage;

import com.buildbuddy.domain.systembuilder.entity.StorageEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StorageResponseDto {

    @JsonProperty(value = "storage_id")
    private Integer storageId;

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "manufacturer")
    private String manufacturer;

    @JsonProperty(value = "price")
    private String price;

    @JsonProperty(value = "product_link")
    private String productLink;

    @JsonProperty(value = "storage_type")
    private String storageType;

    @JsonProperty(value = "capacity")
    private Integer capacity;

    @JsonProperty(value = "form_factor")
    private String formFactor;

    @JsonProperty(value = "rpm")
    private String rpm;

    @JsonProperty(value = "interface")
    private String storageInterface;

    @JsonProperty(value = "cache_memory")
    private String cacheMemory;

    public static StorageResponseDto convertToDto(StorageEntity entity){
        return StorageResponseDto.builder()
                .storageId(entity.getId())
                .name(entity.getName())
                .manufacturer(entity.getManufacturer())
                .price(entity.getPrice())
                .productLink(entity.getProductLink())
                .storageType(entity.getStorageType())
                .capacity(entity.getCapacity())
                .formFactor(entity.getFormFactor())
                .rpm(entity.getRpm())
                .storageInterface(entity.getStorageInterface())
                .cacheMemory(entity.getCacheMemory())
                .build();
    }

}
