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
@Table(name = "graphics_card")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class GraphicsCardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "graphics_card_id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "product_link")
    private String productLink;

    @Column(name = "chipset")
    private String chipset;

    @Column(name = "memory_size")
    private String memorySize;

    @Column(name = "interface")
    private String graphicsInterface;

    @Column(name = "benchmark")
    private Integer benchmark;

    @Column(name = "image")
    private byte[] image;

}
