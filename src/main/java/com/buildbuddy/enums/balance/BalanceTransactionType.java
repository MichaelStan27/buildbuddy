package com.buildbuddy.enums.balance;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BalanceTransactionType {

    TOP_UP("TOP_UP"),
    CONSULT("CONSULT");

    private String value;

}
