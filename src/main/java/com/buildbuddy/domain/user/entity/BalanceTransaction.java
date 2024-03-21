package com.buildbuddy.domain.user.entity;

import com.buildbuddy.domain.consult.entity.ConsultTransaction;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "balance_transaction")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BalanceTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id", updatable = false, nullable = false)
    private Integer transactionId;

    @Column(name = "user_id", insertable = false, updatable = false)
    private Integer userId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @OneToOne
    @JoinColumn(name = "consult_transaction_id")
    private ConsultTransaction consultTransaction;

    @Column(name = "nominal")
    private BigDecimal nominal;

    @Column(name = "status")
    private String status;

    @Column(name = "transaction_type")
    private String transactionType;

    @CreatedDate
    @Column(name = "created_time")
    private LocalDateTime createdTime;

    @LastModifiedDate
    @Column(name = "last_update_time")
    private LocalDateTime lastUpdateTime;
}
