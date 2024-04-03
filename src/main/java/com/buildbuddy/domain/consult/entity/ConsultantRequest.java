package com.buildbuddy.domain.consult.entity;

import com.buildbuddy.domain.consult.dto.request.ConsultantRequestDto;
import com.buildbuddy.enums.consultrequest.ConsultantRequestStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "consultant_request")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConsultantRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id", updatable = false, nullable = false)
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "gender")
    private String gender;

    @Column(name = "age")
    private Integer age;

    @Column(name = "status")
    private String status;

    @CreatedDate
    @Column(name = "created_time")
    private LocalDateTime createdTime;

    @LastModifiedDate
    @Column(name = "last_update_time")
    private LocalDateTime lastUpdateTime;

    @Column(name = "reviewed_by")
    private String reviewedBy;

    public static ConsultantRequest convertToEntity(ConsultantRequestDto dto){
        LocalDateTime time = LocalDateTime.now();
        return ConsultantRequest.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .gender(dto.getGender())
                .age(dto.getAge())
                .status(ConsultantRequestStatus.PENDING.getValue())
                .createdTime(time)
                .lastUpdateTime(time)
                .build();
    }
}
