package com.buildbuddy.domain.article.entity;

import com.buildbuddy.domain.article.entity.pk.ArticleLikePk;
import com.buildbuddy.domain.forum.entity.ThreadEntity;
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
@IdClass(ArticleLikePk.class)
@Table(name = "article_like")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ArticleLikeEntity {

    @Id
    @Column(name = "article_id")
    private Integer articleId;

    @ManyToOne
    @JoinColumn(name = "article_id", insertable = false, updatable = false)
    private ArticleEntity article;

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
