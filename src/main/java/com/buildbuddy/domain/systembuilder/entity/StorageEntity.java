package com.buildbuddy.domain.systembuilder.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@Entity
@Builder
@Table(name = "storage")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class StorageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "storage_id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "price")
    private String price;

    @Column(name = "product_link")
    private String productLink;

    @Column(name = "storage_type")
    private String storageType;

    @Column(name = "capacity")
    private Integer capacity;

    @Column(name = "form_factor")
    private String formFactor;

    @Column(name = "rpm")
    private String rpm;

    @Column(name = "interface")
    private String storageInterface;

    @Column(name = "cache_memory")
    private String cacheMemory;

    @Column(name = "image")
    private byte[] image;
}
