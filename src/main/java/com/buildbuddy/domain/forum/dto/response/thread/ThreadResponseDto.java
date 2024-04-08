package com.buildbuddy.domain.forum.dto.response.thread;

import com.buildbuddy.domain.forum.entity.ThreadEntity;
import com.buildbuddy.domain.forum.entity.ThreadModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Base64;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ThreadResponseDto {

    @JsonProperty
    private Integer threadId;

    @JsonProperty
    private String post;

    @JsonProperty
    private String username;

    @JsonProperty
    private String userProfile;

    @JsonProperty
    private Integer totalLike;

    @JsonProperty
    private Boolean isLikedByUser;

    @JsonProperty
    private Integer userId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastUpdateTime;

    public static ThreadResponseDto convertToDto(ThreadEntity entity){
        byte[] profile = entity.getUser().getProfilePicture();
        return ThreadResponseDto.builder()
                .threadId(entity.getId())
                .post(entity.getPost())
                .username(entity.getUser().getUsername())
                .userProfile(profile != null ? Base64.getEncoder().encodeToString(profile) : null)
                .userId(entity.getUser().getId())
                .createdTime(entity.getCreatedTime())
                .lastUpdateTime(entity.getLastUpdateTime())
                .totalLike(0)
                .isLikedByUser(false)
                .build();
    }

    public static ThreadResponseDto convertToDto(ThreadModel entity){
        byte[] profile = entity.getUserProfile();
        return ThreadResponseDto.builder()
                .threadId(entity.getThreadId())
                .post(entity.getPost())
                .username(entity.getUsername())
                .userId(entity.getUserId())
                .userProfile(profile != null ? Base64.getEncoder().encodeToString(profile) : null)
                .createdTime(entity.getCreatedTime())
                .lastUpdateTime(entity.getLastUpdateTime())
                .totalLike(entity.getTotalLike())
                .isLikedByUser(entity.getIsLikedByUser() != 0)
                .build();
    }
}
