package com.buildbuddy.domain.consult.entity;

import java.time.LocalDateTime;

public interface ConsultTransactionModel {

    Integer getTransactionId();

    String getUsername();

    String getConsultantName();

    String getRoomId();

    String getStatus();

    LocalDateTime getCreatedTime();

    LocalDateTime getLastUpdateTime();

}
