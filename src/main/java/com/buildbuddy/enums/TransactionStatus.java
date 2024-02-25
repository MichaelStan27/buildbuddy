package com.buildbuddy.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum TransactionStatus {
    PENDING("PENDING"),
    ON_PROGRESS("ON_PROGRESS"),
    REJECTED("REJECTED"),
    COMPLETED("COMPLETED");

    private String value;

    public static TransactionStatus getByValue(String value){
        for (TransactionStatus status : values()){
            if(status.getValue().equals(value))
                return status;
        }
        throw new RuntimeException("Transaction Status not found for value: " + value);
    }

    public static Stream<TransactionStatus> stream(){
        return Arrays.stream(values());
    }
}
