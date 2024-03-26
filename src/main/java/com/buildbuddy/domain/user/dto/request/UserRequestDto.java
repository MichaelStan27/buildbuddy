package com.buildbuddy.domain.user.dto.request;

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
public class UserRequestDto {

    @JsonProperty
    private Integer userId;

    @JsonProperty
    private String username;

    @JsonProperty
    private String role;

    @JsonProperty
    private String email;

    @JsonProperty
    private String password;

    @JsonProperty
    private Integer age;

    @JsonProperty
    private String gender;

    @JsonProperty
    private String description;

    @JsonProperty
    private BigDecimal fee;

    @JsonProperty
    private Integer available;
}
