package com.buildbuddy.domain.article.entity;

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
@Table(name = "article")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ArticleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    private Integer id;

    @CreatedBy
    @ManyToOne
    @JoinColumn(name = "user_id", updatable = false)
    private UserEntity user;

    @Column(name = "title")
    private String title;

    @Column(name = "post")
    private String post;

    @Column(name = "image")
    private byte[] image;

    @CreatedDate
    @Column(name = "created_time")
    private LocalDateTime createdTime;

    @LastModifiedDate
    @Column(name = "last_update_time")
    private LocalDateTime lastUpdateTime;

}
