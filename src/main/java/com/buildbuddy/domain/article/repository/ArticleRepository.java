package com.buildbuddy.domain.article.repository;

import com.buildbuddy.domain.article.entity.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface ArticleRepository extends JpaRepository<ArticleEntity, Integer>, JpaSpecificationExecutor<ArticleEntity> {

        Optional<ArticleEntity> findByIdAndUserId(Integer id, Integer userId);

}
