package com.buildbuddy.domain.article.entity;


import com.buildbuddy.domain.forum.entity.ThreadEntity;
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
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "article_comment")
@EntityListeners(AuditingEntityListener.class)
public class ArticleCommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Integer id;

    @CreatedBy
    @ManyToOne
    @JoinColumn(name = "user_id", updatable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "article_id", updatable = false)
    private ArticleEntity article;

    @Column(name = "message")
    private String message;

    @CreatedDate
    @Column(name = "created_time")
    private LocalDateTime createdTime;

    @LastModifiedDate
    @Column(name = "last_update_time")
    private LocalDateTime lastUpdateTime;

}
