package com.buildbuddy.domain.user.dto.response;

import com.buildbuddy.domain.consult.entity.ConsultantDetail;
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

    @JsonProperty(value = "description")
    private String description;

    @JsonProperty(value = "fee")
    private BigDecimal fee;

    @JsonProperty(value = "isAvailable")
    private Boolean isAvailable;

    public static UserResponseDto convertToDto(UserEntity entity){
        ConsultantDetail consultantDetail = entity.getConsultantDetail();

        return UserResponseDto.builder()
                .userId(entity.getId())
                .username(entity.getUsername())
                .email(entity.getEmail())
                .age(entity.getAge())
                .gender(entity.getGender())
                .role(entity.getRole())
                .balance(entity.getBalance())
                .description(consultantDetail != null ? consultantDetail.getDescription() : null)
                .fee(consultantDetail != null ? consultantDetail.getFee() : null)
                .isAvailable(consultantDetail != null ? consultantDetail.getAvailable() != 0 : null)
                .build();
    }

}
