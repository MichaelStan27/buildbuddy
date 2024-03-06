package com.buildbuddy.domain.consult.entity;

import java.math.BigDecimal;

public interface ConsultantModel {

    Integer getConsultantId();

    String getUsername();

    String getEmail();

    Integer getAge();

    String getGender();

    String getDescription();

    BigDecimal getFee();

    Integer getAvailable();

}
