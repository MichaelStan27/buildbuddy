package com.buildbuddy.enums.balance;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BalanceTransactionStatus {

    ON_HOLD("ON_HOLD"),
    RETURNED("RETURNED"),
    DEDUCTED("DEDUCTED"),
    ADDED("ADDED");

    private String value;
}
