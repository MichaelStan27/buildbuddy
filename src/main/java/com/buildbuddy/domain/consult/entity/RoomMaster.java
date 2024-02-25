package com.buildbuddy.domain.consult.entity;

import com.buildbuddy.domain.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "room_master")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomMaster {

    @Id
    @Column(name = "room_id")
    private String roomId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "consultant_id")
    private UserEntity consultant;

    @CreatedDate
    @Column(name = "created_time")
    private LocalDateTime createdTime;

}
