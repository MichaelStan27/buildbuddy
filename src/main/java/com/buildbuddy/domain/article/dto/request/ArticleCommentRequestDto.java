package com.buildbuddy.domain.article.dto.request;

import com.buildbuddy.domain.article.entity.ArticleCommentEntity;
import com.buildbuddy.domain.article.entity.ArticleEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleCommentRequestDto {

    private Integer commentId;

    private Integer articleId;

    private String message;

    public static ArticleCommentEntity convertToEntity(ArticleCommentRequestDto dto, ArticleEntity article){
        return ArticleCommentEntity.builder()
                .message(dto.getMessage())
                .article(article)
                .build();
    }

}
