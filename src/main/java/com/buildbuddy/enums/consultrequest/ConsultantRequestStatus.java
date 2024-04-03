package com.buildbuddy.enums.consultrequest;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ConsultantRequestStatus {

    PENDING("PENDING"),
    REJECTED("REJECTED"),
    APPROVED("APPROVED");

    private String value;

}
