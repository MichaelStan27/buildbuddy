package com.buildbuddy.domain.article.dto.request;

import com.buildbuddy.domain.article.entity.ArticleEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Base64;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleRequestDto {

    private Integer id;

    private String title;

    private String post;

    private String image;

    private String status;

    public static ArticleEntity convertToEntity(ArticleRequestDto dto){
        String image = dto.getImage();
        return ArticleEntity.builder()
                .title(dto.getTitle())
                .post(dto.getPost())
                .status(dto.getStatus())
                .image(image != null ? Base64.getDecoder().decode(image) : null)
                .build();
    }

}
