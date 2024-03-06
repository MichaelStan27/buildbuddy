package com.buildbuddy.domain.user.dto.response;

import com.buildbuddy.domain.user.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {

    @JsonProperty("userId")
    private Integer userId;

    @JsonProperty(value = "username")
    private String username;

    @JsonProperty(value = "email")
    private String email;

    @JsonProperty(value = "age")
    private Integer age;

    @JsonProperty(value = "gender")
    private String gender;

    @JsonProperty(value = "role")
    private String role;

    @JsonProperty(value = "balance")
    private BigDecimal balance;

    public static UserResponseDto convertToDto(UserEntity entity){
        return UserResponseDto.builder()
                .userId(entity.getId())
                .username(entity.getUsername())
                .email(entity.getEmail())
                .age(entity.getAge())
                .gender(entity.getGender())
                .role(entity.getRole())
                .balance(entity.getBalance())
                .build();
    }

}
