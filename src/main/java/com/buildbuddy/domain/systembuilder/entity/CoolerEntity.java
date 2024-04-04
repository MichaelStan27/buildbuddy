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
@Table(name = "cooler")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class CoolerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cooler_id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "price")
    private String price;

    @Column(name = "product_link")
    private String productLink;

    @Column(name = "cooler_type")
    private String coolerType;

    @Column(name = "color")
    private String color;

    @Column(name = "image")
    private byte[] image;

}
