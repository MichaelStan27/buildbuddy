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
@Table(name = "motherboard")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class MotherboardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "motherboard_id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "price")
    private String price;

    @Column(name = "product_link")
    private String productLink;

    @Column(name = "chipset")
    private String chipset;

    @Column(name = "socket_type")
    private String socketType;

    @Column(name = "form_factor")
    private String formFactor;

    @Column(name = "memory_slots")
    private String memorySlots;

    @Column(name = "max_memory")
    private String maxMemory;
}
