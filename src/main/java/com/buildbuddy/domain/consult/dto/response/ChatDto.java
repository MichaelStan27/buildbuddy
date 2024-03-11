package com.buildbuddy.domain.consult.dto.response;

import com.buildbuddy.domain.consult.entity.Chat;
import com.buildbuddy.domain.user.entity.UserEntity;
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
public class ChatDto {

    @JsonProperty
    private Integer userId;

    @JsonProperty
    private String username;

    @JsonProperty
    private String message;

    @JsonProperty
    @JsonFormat(pattern = "dd-MM-yyyy, HH:mm:ss")
    private LocalDateTime sendTime;

    public static ChatDto convertToDto(Chat e){
        UserEntity sender = e.getSender();
        return ChatDto.builder()
                .userId(sender.getId())
                .username(sender.getUsername())
                .message(e.getMessage())
                .sendTime(e.getSendTime())
                .build();
    }

}
