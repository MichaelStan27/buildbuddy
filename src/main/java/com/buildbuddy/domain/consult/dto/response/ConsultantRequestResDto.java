package com.buildbuddy.domain.consult.dto.response;

import com.buildbuddy.domain.consult.entity.ConsultantRequest;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConsultantRequestResDto {

    @JsonProperty
    private Integer requestId;

    @JsonProperty
    private Integer userId;

    @JsonProperty
    private String username;

    @JsonProperty
    private String email;

    @JsonProperty
    private String gender;

    @JsonProperty
    private Integer age;

    @JsonProperty
    private String status;

    @JsonProperty
    @JsonFormat(pattern = "dd-MM-yyyy, HH:mm:ss")
    private LocalDateTime createdTime;

    @JsonProperty
    @JsonFormat(pattern = "dd-MM-yyyy, HH:mm:ss")
    private LocalDateTime lastUpdateTime;

    @JsonProperty
    private String reviewedBy;

    public static ConsultantRequestResDto convertToDto(ConsultantRequest entity){
        return ConsultantRequestResDto.builder()
                .requestId(entity.getId())
                .userId(entity.getUserId())
                .username(entity.getUsername())
                .email(entity.getEmail())
                .age(entity.getAge())
                .gender(entity.getGender())
                .status(entity.getStatus())
                .createdTime(entity.getCreatedTime())
                .lastUpdateTime(entity.getLastUpdateTime())
                .reviewedBy(entity.getReviewedBy())
                .build();
    }

}
