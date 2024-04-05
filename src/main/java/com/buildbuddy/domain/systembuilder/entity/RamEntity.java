package com.buildbuddy.domain.systembuilder.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;

@Data
@Entity
@Builder
@Table(name = "ram")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class RamEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ram_id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "product_link")
    private String productLink;

    @Column(name = "ram_size")
    private Integer ramSize;

    @Column(name = "ram_quantity")
    private Integer ramQuantity;

    @Column(name = "ram_speed")
    private String ramSpeed;

    @Column(name = "ram_type")
    private String ramType;

    @Column(name = "cas_latency")
    private String casLatency;

    @Column(name = "image")
    private byte[] image;

}
