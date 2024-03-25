package com.buildbuddy.domain.consult.dto.response;

import com.buildbuddy.domain.consult.entity.RoomMaster;
import com.buildbuddy.domain.consult.entity.RoomMasterModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Base64;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomMasterDto {

    @JsonProperty
    private String roomId;

    @JsonProperty
    private String user;

    @JsonProperty
    private String userProfile;

    @JsonProperty
    private String consultant;

    @JsonProperty
    private String consultantProfile;

    @JsonProperty
    @JsonFormat(pattern = "dd-MM-yyyy, HH:mm:ss")
    private LocalDateTime createdTime;

    @JsonProperty
    @JsonFormat(pattern = "dd-MM-yyyy, HH:mm:ss")
    private LocalDateTime expiredTime;

    @JsonProperty
    private Boolean isExpired;

    public static RoomMasterDto convertToDto(RoomMasterModel m){
        byte[] userProfile = m.getUserProfile();
        byte[] consultantProfile = m.getConsultantProfile();
        return RoomMasterDto.builder()
                .roomId(m.getRoomId())
                .user(m.getUsername())
                .userProfile(userProfile != null ? Base64.getEncoder().encodeToString(userProfile) : null)
                .consultant(m.getConsultantName())
                .consultantProfile(consultantProfile != null ? Base64.getEncoder().encodeToString(consultantProfile) : null)
                .createdTime(m.getCreatedTime())
                .build();
    }

}
