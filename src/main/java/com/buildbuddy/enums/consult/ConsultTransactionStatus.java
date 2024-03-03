package com.buildbuddy.enums.consult;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum ConsultTransactionStatus {
    PENDING("PENDING"),
    ON_PROGRESS("ON_PROGRESS"),
    REJECTED("REJECTED"),
    COMPLETED("COMPLETED");

    private String value;

    public static ConsultTransactionStatus getByValue(String value){
        for (ConsultTransactionStatus status : values()){
            if(status.getValue().equals(value))
                return status;
        }
        throw new RuntimeException("Transaction Status not found for value: " + value);
    }

    public static Stream<ConsultTransactionStatus> stream(){
        return Arrays.stream(values());
    }
}
