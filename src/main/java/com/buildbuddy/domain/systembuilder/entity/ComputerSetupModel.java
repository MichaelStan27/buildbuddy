package com.buildbuddy.domain.systembuilder.entity;

import jakarta.persistence.Column;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface ComputerSetupModel {

    Integer getComputerSetupId();
    Integer getCaseId();
    String getCaseName();
    byte[] getCaseImage();

    Integer getCoolerId();
    String getCoolerName();
    byte[] getCoolerImage();

    Integer getGraphicsCardId();
    String getGraphicsCardName();
    Integer getGraphicsCardBenchmark();
    byte[] getGraphicsCardImage();

    Integer getMonitorId();
    String getMonitorName();
    byte[] getMonitorImage();

    Integer getMotherboardId();
    String getMotherboardName();
    byte[] getMotherboardImage();

    Integer getPowersupplyId();
    String getPowersupplyName();
    byte[] getPowersupplyImage();

    Integer getProcessorId();
    String getProcessorName();
    Integer getProcessorBenchmark();
    byte[] getProcessorImage();

    Integer getRamId();
    String getRamName();
    Integer getRamSize();
    byte[] getRamImage();

    Integer getStorageId();
    String getStorageName();
    Integer getStorageSize();
    byte[] getStorageImage();

    String getUsername();

    BigDecimal getTotalPrice();

    Integer getTotalBenchmark();

    LocalDateTime getCreatedTime();

    LocalDateTime getLastUpdateTime();

}
