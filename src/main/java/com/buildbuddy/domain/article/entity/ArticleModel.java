package com.buildbuddy.domain.article.entity;

import java.time.LocalDateTime;

public interface ArticleModel {

    Integer getArticleId();
    String getUsername();
    String getTitle();
    String getPost();

    byte[] getImage();
    Integer getTotalLike();
    long getIsLikedByUser();
    LocalDateTime getCreatedTime();
    LocalDateTime getLastUpdateTime();

}
