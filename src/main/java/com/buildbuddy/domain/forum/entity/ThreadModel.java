package com.buildbuddy.domain.forum.entity;

import java.time.LocalDateTime;

public interface ThreadModel {
    Integer getThreadId();
    String getPost();
    String getUsername();
    byte[] getUserProfile();
    LocalDateTime getCreatedTime();
    LocalDateTime getLastUpdateTime();
}
