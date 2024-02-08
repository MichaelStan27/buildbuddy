package com.buildbuddy.domain.consult.dto.response;

import com.buildbuddy.domain.consult.entity.ConsultantModel;
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
public class ConsultantDetailDto {

    @JsonProperty(value = "username")
    private String username;

    @JsonProperty(value = "email")
    private String email;

    @JsonProperty(value = "age")
    private Integer age;

    @JsonProperty(value = "gender")
    private String gender;

    @JsonProperty(value = "description")
    private String description;

    @JsonProperty(value = "fee")
    private BigDecimal fee;

    @JsonProperty(value = "isAvailable")
    private Boolean isAvailable;

    public static ConsultantDetailDto convertToDto(ConsultantModel m){
        return ConsultantDetailDto.builder()
                .username(m.getUsername())
                .gender(m.getGender())
                .email(m.getEmail())
                .age(m.getAge())
                .description(m.getDescription())
                .fee(m.getFee())
                .isAvailable(m.getAvailable() != 0)
                .build();
    }

}
