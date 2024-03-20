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

    @JsonProperty
    private Integer userId;

    @JsonProperty
    private String username;

    @JsonProperty
    private String email;

    @JsonProperty
    private Integer age;

    @JsonProperty
    private String gender;

    @JsonProperty
    private String role;

    @JsonProperty
    private BigDecimal balance;

    @JsonProperty
    private String formattedBalance;

    @JsonProperty
    private String description;

    @JsonProperty
    private BigDecimal fee;

    @JsonProperty
    private String formattedFee;

    @JsonProperty
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
                .formattedBalance(String.format("%,.2f", entity.getBalance()))
                .description(consultantDetail != null ? consultantDetail.getDescription() : null)
                .fee(consultantDetail != null ? consultantDetail.getFee() : null)
                .formattedFee(consultantDetail != null ? String.format("%,.2f", consultantDetail.getFee()) : null)
                .isAvailable(consultantDetail != null ? consultantDetail.getAvailable() != 0 : null)
                .build();
    }

}
