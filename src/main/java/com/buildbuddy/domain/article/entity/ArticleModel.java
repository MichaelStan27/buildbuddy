package com.buildbuddy.domain.article.entity;

import java.time.LocalDateTime;

public interface ArticleModel {

    Integer getArticleId();
    String getUsername();
    Integer getUserId();
    String getTitle();
    String getPost();
    String getStatus();
    byte[] getImage();
    Integer getTotalLike();
    long getIsLikedByUser();
    LocalDateTime getCreatedTime();
    LocalDateTime getLastUpdateTime();

}
