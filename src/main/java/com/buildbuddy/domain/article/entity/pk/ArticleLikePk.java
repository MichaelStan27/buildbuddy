package com.buildbuddy.domain.article.entity.pk;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ArticleLikePk implements Serializable {
    private Integer articleId;
    private Integer userId;
}
