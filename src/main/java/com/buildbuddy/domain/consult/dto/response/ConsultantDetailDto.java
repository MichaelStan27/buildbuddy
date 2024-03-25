package com.buildbuddy.domain.consult.dto.response;

import com.buildbuddy.domain.consult.entity.ConsultantModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.paypal.base.codec.binary.Base64;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConsultantDetailDto {

    @JsonProperty
    private Integer consultantId;

    @JsonProperty
    private String username;

    @JsonProperty
    private String profilePicture;

    @JsonProperty
    private String email;

    @JsonProperty
    private Integer age;

    @JsonProperty
    private String gender;

    @JsonProperty
    private String description;

    @JsonProperty
    private BigDecimal fee;

    @JsonProperty
    private Boolean isAvailable;

    public static ConsultantDetailDto convertToDto(ConsultantModel m){
        byte[] profile = m.getProfile();
        return ConsultantDetailDto.builder()
                .consultantId(m.getConsultantId())
                .username(m.getUsername())
                .profilePicture(profile != null ? Base64.encodeBase64String(profile) : null)
                .gender(m.getGender())
                .email(m.getEmail())
                .age(m.getAge())
                .description(m.getDescription())
                .fee(m.getFee())
                .isAvailable(m.getAvailable() != 0)
                .build();
    }

}
