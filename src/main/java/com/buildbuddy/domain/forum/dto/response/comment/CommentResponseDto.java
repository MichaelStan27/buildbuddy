package com.buildbuddy.domain.forum.dto.response.comment;

import com.buildbuddy.domain.forum.entity.CommentEntity;
import com.buildbuddy.domain.forum.entity.CommentModel;
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
public class CommentResponseDto {

    @JsonProperty
    private Integer commentId;

    @JsonProperty
    private String username;

    @JsonProperty
    private Integer userId;

    @JsonProperty
    private String userProfile;

    @JsonProperty
    private String message;

    @JsonProperty
    private Integer threadId;

    @JsonProperty
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdTime;

    @JsonProperty
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastUpdateTime;

    public static CommentResponseDto convertToDto(CommentEntity entity){
        return CommentResponseDto.builder()
                .commentId(entity.getId())
                .username(entity.getUser().getUsername())
                .userId(entity.getUser().getId())
                .message(entity.getMessage())
                .threadId(entity.getThread().getId())
                .createdTime(entity.getCreatedTime())
                .lastUpdateTime(entity.getLastUpdateTime())
                .build();
    }

    public static CommentResponseDto convertToDto(CommentModel entity){
        byte[] profile = entity.getUserProfile();
        return CommentResponseDto.builder()
                .commentId(entity.getCommentId())
                .username(entity.getUsername())
                .userId(entity.getUserId())
                .userProfile(profile != null ? Base64.getEncoder().encodeToString(profile) : null)
                .message(entity.getMessage())
                .threadId(entity.getThreadId())
                .createdTime(entity.getCreatedTime())
                .lastUpdateTime(entity.getLastUpdateTime())
                .build();
    }

}
