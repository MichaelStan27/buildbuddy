package com.buildbuddy.domain.article.repository;

import com.buildbuddy.domain.article.entity.ArticleLikeEntity;
import com.buildbuddy.domain.article.entity.pk.ArticleLikePk;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArticleLikeRepository extends JpaRepository<ArticleLikeEntity, ArticleLikePk> {

    Optional<ArticleLikeEntity> findByArticleIdAndUserId(Integer articleId, Integer userId);

}
