package com.buildbuddy.domain.forum.entity.pk;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ThreadLikePk implements Serializable {
    private Integer threadId;
    private Integer userId;
}
