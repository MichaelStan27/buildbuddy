package com.buildbuddy.domain.user.dto.response;

import com.buildbuddy.domain.user.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {

    @JsonProperty(value = "username")
    private String username;

    @JsonProperty(value = "email")
    private String email;

    @JsonProperty(value = "age")
    private Integer age;

    @JsonProperty(value = "gender")
    private String gender;

    @JsonProperty(value = "balance")
    private Double balance;

    public static UserResponseDto convertToDto(UserEntity entity){
        return UserResponseDto.builder()
                .username(entity.getUsername())
                .email(entity.getEmail())
                .age(entity.getAge())
                .gender(entity.getGender())
                .balance(entity.getBalance())
                .build();
    }

}
