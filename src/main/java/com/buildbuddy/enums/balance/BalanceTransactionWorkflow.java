package com.buildbuddy.enums.balance;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public enum BalanceTransactionWorkflow {

    WF_PENDING(BalanceTransactionType.TOP_UP,
            BalanceTransactionStatus.PENDING,
            List.of(BalanceTransactionStatus.REJECTED, BalanceTransactionStatus.ADDED));

    private BalanceTransactionType type;
    private BalanceTransactionStatus status;
    private List<BalanceTransactionStatus> nextStatus;

    public static BalanceTransactionWorkflow getByTypeAndStatus(BalanceTransactionType type, BalanceTransactionStatus status){
        for(BalanceTransactionWorkflow wf : values()){
            if(wf.getType().equals(type) && wf.getStatus().equals(status))
                return wf;
        }
        throw new RuntimeException("balance wf not found for status: " + status + " and type: " + type);
    }
}
