package com.buildbuddy.domain.forum.dto.request;

import com.buildbuddy.domain.forum.entity.ThreadEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ThreadRequestDto {

    private String post;

    public static ThreadEntity convertToEntity(ThreadRequestDto dto){
        return ThreadEntity.builder()
                .post(dto.getPost())
                .build();
    }

}
