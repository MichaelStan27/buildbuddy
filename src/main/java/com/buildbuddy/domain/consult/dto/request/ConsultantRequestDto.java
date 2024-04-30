package com.buildbuddy.domain.consult.dto.request;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConsultantRequestDto {

    private Integer requestId;

    private String username;

    private String email;

    private String gender;

    private Integer age;

    private String status;

    private String fileBase64;

}
