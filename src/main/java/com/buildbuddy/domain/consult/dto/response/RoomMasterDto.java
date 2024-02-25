package com.buildbuddy.domain.consult.dto.response;

import com.buildbuddy.domain.consult.entity.RoomMaster;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
    private String consultant;

    @JsonProperty
    @JsonFormat(pattern = "dd-MM-yyyy, HH:mm:ss")
    private LocalDateTime createdTime;

    public static RoomMasterDto convertToDto(RoomMaster e){
        return RoomMasterDto.builder()
                .roomId(e.getRoomId())
                .user(e.getUser().getUsername())
                .consultant(e.getConsultant().getUsername())
                .createdTime(e.getCreatedTime())
                .build();
    }

}
