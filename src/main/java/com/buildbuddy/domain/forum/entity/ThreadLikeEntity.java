package com.buildbuddy.domain.forum.entity;

import com.buildbuddy.domain.forum.entity.pk.ThreadLikePk;
import com.buildbuddy.domain.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@IdClass(ThreadLikePk.class)
@Table(name = "thread_like")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ThreadLikeEntity {

    @Id
    @Column(name = "thread_id")
    private Integer threadId;

    @ManyToOne
    @JoinColumn(name = "thread_id", insertable = false, updatable = false)
    private ThreadEntity thread;

    @Id
    @Column(name = "user_id")
    private Integer userId;

    @CreatedBy
    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private UserEntity user;

    @CreatedDate
    @Column(name = "created_time")
    private LocalDateTime createdTime;

}
