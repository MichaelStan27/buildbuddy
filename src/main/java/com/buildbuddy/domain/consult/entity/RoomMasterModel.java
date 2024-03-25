package com.buildbuddy.domain.consult.entity;

import java.time.LocalDateTime;

public interface RoomMasterModel {
    String getRoomId();
    String getUsername();
    byte[] getUserProfile();
    String getConsultantName();
    byte[] getConsultantProfile();
    LocalDateTime getCreatedTime();
}
