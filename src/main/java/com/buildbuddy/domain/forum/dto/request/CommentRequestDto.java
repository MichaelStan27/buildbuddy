package com.buildbuddy.domain.forum.dto.request;

import com.buildbuddy.domain.forum.entity.CommentEntity;
import com.buildbuddy.domain.forum.entity.ThreadEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequestDto {

    @JsonProperty(value = "commentId")
    private Integer commentId;

    @JsonProperty(value = "threadId")
    private Integer threadId;

    @JsonProperty(value = "message")
    private String message;

    public static CommentEntity convertToEntity(CommentRequestDto dto, ThreadEntity thread){
        return CommentEntity.builder()
                .message(dto.getMessage())
                .thread(thread)
                .build();
    }
}
