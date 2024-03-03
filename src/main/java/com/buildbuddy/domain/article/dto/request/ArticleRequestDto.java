package com.buildbuddy.domain.article.dto.request;

import com.buildbuddy.domain.article.entity.ArticleEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleRequestDto {

    private Integer id;

    private  String post;

    public static ArticleEntity convertToEntity(ArticleRequestDto dto){
        return ArticleEntity.builder()
                .post(dto.getPost())
                .build();
    }

}
