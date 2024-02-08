package com.buildbuddy.domain.consult.entity;

import com.buildbuddy.domain.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "consultant_detail")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConsultantDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "consultant_detail_id", updatable = false, nullable = false)
    private Integer consultantDetailId;

    @CreatedBy
    @ManyToOne
    @JoinColumn(name = "user_id", updatable = false)
    private UserEntity user;

    @Column(name = "description")
    private String description;

    @Column(name = "fee")
    private BigDecimal fee;

    @Column(name = "available")
    private Integer available;

}
