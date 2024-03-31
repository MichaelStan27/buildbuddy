package com.buildbuddy.domain.article.dto.response.article;

import com.buildbuddy.domain.article.entity.ArticleEntity;
import com.buildbuddy.domain.article.entity.ArticleModel;
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
public class ArticleResponseDto {

    @JsonProperty
    private Integer articleId;

    @JsonProperty
    private String title;

    @JsonProperty
    private String post;

    @JsonProperty
    private String image;

    @JsonProperty
    private String username;

    @JsonProperty
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdTime;

    @JsonProperty
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastUpdateTime;

    public static ArticleResponseDto convertToDto(ArticleEntity entity){
        byte[] image = entity.getImage();
        return ArticleResponseDto.builder()
                .articleId(entity.getId())
                .title(entity.getTitle())
                .post(entity.getPost())
                .image(image != null ? Base64.getEncoder().encodeToString(image) : null)
                .username(entity.getUser().getUsername())
                .createdTime(entity.getCreatedTime())
                .lastUpdateTime(entity.getLastUpdateTime())
                .build();
    }

    public static ArticleResponseDto convertToDto(ArticleModel entity){
        byte[] image = entity.getImage();
        return ArticleResponseDto.builder()
                .articleId(entity.getArticleId())
                .title(entity.getTitle())
                .post(entity.getPost())
                .image(image != null ? Base64.getEncoder().encodeToString(image) : null)
                .username(entity.getUsername())
                .createdTime(entity.getCreatedTime())
                .lastUpdateTime(entity.getLastUpdateTime())
                .build();
    }
}
