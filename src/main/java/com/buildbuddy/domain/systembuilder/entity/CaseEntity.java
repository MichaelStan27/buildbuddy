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
@Table(name = "computer_case")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class CaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "computer_case_id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "price")
    private String price;

    @Column(name = "product_link")
    private String productLink;

    @Column(name = "side_panel")
    private String sidePanel;

    @Column(name = "cabinet_type")
    private String cabinetType;

    @Column(name = "color")
    private String color;

    @Column(name = "image")
    private byte[] image;

}
