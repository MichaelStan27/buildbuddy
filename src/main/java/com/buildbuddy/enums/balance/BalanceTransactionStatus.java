package com.buildbuddy.enums.balance;

import com.buildbuddy.enums.consult.ConsultTransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BalanceTransactionStatus {

    PENDING("PENDING"),
    REJECTED("REJECTED"),
    ON_HOLD("ON_HOLD"),
    RETURNED("RETURNED"),
    DEDUCTED("DEDUCTED"),
    ADDED("ADDED");

    private String value;

    public static BalanceTransactionStatus getByValue(String value){
        for (BalanceTransactionStatus status : values()){
            if(status.getValue().equals(value))
                return status;
        }
        throw new RuntimeException("Balance Transaction Status not found for value: " + value);
    }
}
