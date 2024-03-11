package com.buildbuddy.domain.consult.entity;

import com.buildbuddy.domain.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "chat")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_id", updatable = false, nullable = false)
    private Integer chatId;

    @Column(name = "room_id", insertable = false, updatable = false)
    private String roomId;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private RoomMaster roomMaster;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity sender;

    @Column(name = "message")
    private String message;

    @CreatedDate
    @Column(name = "send_time")
    private LocalDateTime sendTime;

}
