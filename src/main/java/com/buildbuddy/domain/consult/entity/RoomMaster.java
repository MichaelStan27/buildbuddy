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
import java.util.List;

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

    @Column(name = "user_id", insertable = false, updatable = false)
    private Integer userId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "consultant_id", insertable = false, updatable = false)
    private Integer consultantId;

    @ManyToOne
    @JoinColumn(name = "consultant_id")
    private UserEntity consultant;

    @OneToMany(mappedBy = "roomMaster", orphanRemoval = true)
    private List<Chat> chatList;

    @CreatedDate
    @Column(name = "created_time")
    private LocalDateTime createdTime;

}
