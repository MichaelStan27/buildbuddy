package com.buildbuddy.enums.balance;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BalanceTransactionType {

    PAYPAL("PAYPAL"),
    CONSULT("CONSULT");

    private String value;

    public static BalanceTransactionType getByValue(String value){
        for (BalanceTransactionType type : values()){
            if(type.getValue().equals(value))
                return type;
        }
        throw new RuntimeException("Balance Transaction Type not found for value: " + value);
    }

}
