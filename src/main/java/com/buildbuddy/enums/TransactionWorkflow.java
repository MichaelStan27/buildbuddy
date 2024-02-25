package com.buildbuddy.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public enum TransactionWorkflow {
    WF_PENDING(TransactionStatus.PENDING, List.of(TransactionStatus.ON_PROGRESS, TransactionStatus.REJECTED)),
    WF_ON_PROGRESS(TransactionStatus.ON_PROGRESS, List.of(TransactionStatus.COMPLETED));

    private TransactionStatus status;
    private List<TransactionStatus> nextStatus;

    public static TransactionWorkflow getByTransactionStatus(TransactionStatus status){
        for (TransactionWorkflow wf : values()){
            if(wf.getStatus().equals(status))
                return wf;
        }
        throw new RuntimeException("Transaction wf not found for status: " + status);
    }
}
