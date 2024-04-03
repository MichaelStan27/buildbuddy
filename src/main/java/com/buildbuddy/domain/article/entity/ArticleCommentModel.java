package com.buildbuddy.domain.article.entity;

import java.time.LocalDateTime;

public interface ArticleCommentModel {

    Integer getCommentId();

    String getUsername();

    byte[] getUserProfile();

    String getMessage();

    Integer getArticleId();

    LocalDateTime getCreatedTime();

    LocalDateTime getLastUpdateTime();

}
