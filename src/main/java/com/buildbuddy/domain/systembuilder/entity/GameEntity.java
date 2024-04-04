package com.buildbuddy.domain.systembuilder.entity;

import com.buildbuddy.domain.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@Table(name = "game")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class GameEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_id")
    private Integer id;

    @CreatedBy
    @ManyToOne
    @JoinColumn(name = "user_id", updatable = false)
    private UserEntity user;

    @Column(name = "name")
    private String name;

    @Column(name = "memory")
    private Integer memory;

    @Column(name = "graphics_card_benchmark")
    private Integer graphicsCardBenchmark;

    @Column(name = "graphics_card")
    private String graphicsCard;

    @Column(name = "cpu_benchmark")
    private Integer cpuBenchmark;

    @Column(name = "cpu")
    private String cpu;

    @Column(name = "file_size")
    private Integer fileSize;

    @CreatedDate
    @Column(name = "created_time")
    private LocalDateTime createdTime;

    @LastModifiedDate
    @Column(name = "last_update_time")
    private LocalDateTime lastUpdateTime;

    @Column(name = "image")
    private byte[] image;

}
