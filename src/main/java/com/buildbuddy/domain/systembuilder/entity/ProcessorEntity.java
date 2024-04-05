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
@Table(name = "processor")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ProcessorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "processor_id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "product_link")
    private String productLink;

    @Column(name = "socket")
    private String socket;

    @Column(name = "series")
    private String series;

    @Column(name = "core")
    private String core;

    @Column(name = "integrated_graphics")
    private String integratedGraphics;

    @Column(name = "micro_architecture")
    private String microArchitecture;

    @Column(name = "benchmark")
    private Integer benchmark;

    @Column(name = "image")
    private byte[] image;

}
