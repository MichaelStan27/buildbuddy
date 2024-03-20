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
@Table(name = "power_supply")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class PowerSupplyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "power_supply_id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "price")
    private String price;

    @Column(name = "product_link")
    private String productLink;

    @Column(name = "power")
    private String power;

    @Column(name = "color")
    private String color;

    @Column(name = "form_factor")
    private String formFactor;

    @Column(name = "efficiency")
    private String efficiency;

}
