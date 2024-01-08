package com.buildbuddy.domain.forum.dto.response.comment;

import com.buildbuddy.domain.forum.entity.CommentEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponseDto {

    @JsonProperty(value = "commentId")
    private Integer commentId;

    @JsonProperty(value = "username")
    private String username;

    @JsonProperty(value = "message")
    private String message;

    @JsonProperty(value = "threadId")
    private Integer threadId;

    @JsonProperty(value = "createdTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdTime;

    @JsonProperty(value = "lastUpdateTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastUpdateTime;

    public static CommentResponseDto convertToDto(CommentEntity entity){
        return CommentResponseDto.builder()
                .commentId(entity.getId())
                .username(entity.getUser().getUsername())
                .message(entity.getMessage())
                .threadId(entity.getThread().getId())
                .createdTime(entity.getCreatedTime())
                .lastUpdateTime(entity.getLastUpdateTime())
                .build();
    }

}
