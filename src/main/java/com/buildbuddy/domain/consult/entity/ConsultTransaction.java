package com.buildbuddy.domain.consult.entity;

import com.buildbuddy.domain.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "consult_transaction")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConsultTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id", updatable = false, nullable = false)
    private Integer transactionId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "consultant_id")
    private UserEntity consultant;

    @OneToOne
    @JoinColumn(name = "room_id")
    private RoomMaster roomMaster;

    @Column(name = "status")
    private String status;

    @CreatedDate
    @Column(name = "created_time")
    private LocalDateTime createdTime;

    @LastModifiedDate
    @Column(name = "last_update_time")
    private LocalDateTime lastUpdateTime;
}
