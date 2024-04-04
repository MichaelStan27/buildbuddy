package com.buildbuddy.domain.systembuilder.dto.request;

import com.buildbuddy.domain.systembuilder.entity.StorageEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Base64;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StorageRequestDto {

    private Integer id;
    private String name;
    private String manufacturer;
    private String price;
    private String productLink;
    private String storageType;
    private Integer capacity;
    private String formFactor;
    private String rpm;
    private String storageInterface;
    private String cacheMemory;
    private String image;

    public static StorageEntity convertToEntity(StorageRequestDto dto){
        String image = dto.getImage();
        return StorageEntity.builder()
                .name(dto.getName())
                .manufacturer(dto.getManufacturer())
                .price(dto.getPrice())
                .productLink(dto.getProductLink())
                .storageType(dto.getStorageType())
                .capacity(dto.getCapacity())
                .formFactor(dto.getFormFactor())
                .rpm(dto.getRpm())
                .storageInterface(dto.getStorageInterface())
                .cacheMemory(dto.getCacheMemory())
                .image(image != null ? Base64.getDecoder().decode(image) : null)
                .build();
    }

}
