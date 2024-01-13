package com.buildbuddy.domain.article.dto.response.article;

import com.buildbuddy.domain.article.entity.ArticleEntity;
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
public class ArticleResponseDto {

    @JsonProperty(value = "articleId")
    private Integer articleId;

    @JsonProperty(value = "post")
    private String post;

    @JsonProperty(value = "username")
    private String username;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty(value = "createdTime")
    private LocalDateTime createdTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty(value = "lastUpdateTime")
    private LocalDateTime lastUpdateTime;

    public static ArticleResponseDto convertToDto(ArticleEntity entity){
        return ArticleResponseDto.builder()
                .articleId(entity.getId())
                .post(entity.getPost())
                .username(entity.getUser().getUsername())
                .createdTime(entity.getCreatedTime())
                .lastUpdateTime(entity.getLastUpdateTime())
                .build();
    }
}
