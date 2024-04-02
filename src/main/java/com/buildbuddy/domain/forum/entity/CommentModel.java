package com.buildbuddy.domain.forum.entity;

import java.time.LocalDateTime;

public interface CommentModel {

    Integer getCommentId();

    String getUsername();

    byte[] getUserProfile();

    String getMessage();

    Integer getThreadId();

    LocalDateTime getCreatedTime();

    LocalDateTime getLastUpdateTime();

}
