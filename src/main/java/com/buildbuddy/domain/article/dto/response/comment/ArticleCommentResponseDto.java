package com.buildbuddy.domain.article.dto.response.comment;

import com.buildbuddy.domain.article.entity.ArticleCommentEntity;
import com.buildbuddy.domain.article.entity.ArticleCommentModel;
import com.buildbuddy.domain.forum.dto.response.comment.CommentResponseDto;
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
public class ArticleCommentResponseDto {

    @JsonProperty
    private Integer commentId;

    @JsonProperty
    private String username;

    @JsonProperty
    private String userProfile;

    @JsonProperty
    private String message;

    @JsonProperty
    private Integer articleId;

    @JsonProperty
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdTime;

    @JsonProperty
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastUpdateTime;

    public static ArticleCommentResponseDto convertToDto(ArticleCommentEntity entity){
        byte[] profile = entity.getUser().getProfilePicture();
        return ArticleCommentResponseDto.builder()
                .commentId(entity.getId())
                .username(entity.getUser().getUsername())
                .userProfile(profile != null ? Base64.getEncoder().encodeToString(profile) : null)
                .message(entity.getMessage())
                .articleId(entity.getArticle().getId())
                .createdTime(entity.getCreatedTime())
                .lastUpdateTime(entity.getLastUpdateTime())
                .build();
    }

    public static ArticleCommentResponseDto convertToDto(ArticleCommentModel entity){
        byte[] profile = entity.getUserProfile();
        return ArticleCommentResponseDto.builder()
                .commentId(entity.getCommentId())
                .username(entity.getUsername())
                .userProfile(profile != null ? Base64.getEncoder().encodeToString(profile) : null)
                .message(entity.getMessage())
                .articleId(entity.getArticleId())
                .createdTime(entity.getCreatedTime())
                .lastUpdateTime(entity.getLastUpdateTime())
                .build();
    }

}
