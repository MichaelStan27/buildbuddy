package com.buildbuddy.domain.consult.entity;

import java.time.LocalDateTime;

public interface RoomMasterModel {
    String getRoomId();
    String getUsername();
    String getConsultantName();
    LocalDateTime getCreatedTime();
}
