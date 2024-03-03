package com.buildbuddy.enums.consult;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public enum ConsultTransactionWorkflow {
    WF_PENDING(ConsultTransactionStatus.PENDING, List.of(ConsultTransactionStatus.ON_PROGRESS, ConsultTransactionStatus.REJECTED)),
    WF_ON_PROGRESS(ConsultTransactionStatus.ON_PROGRESS, List.of(ConsultTransactionStatus.COMPLETED));

    private ConsultTransactionStatus status;
    private List<ConsultTransactionStatus> nextStatus;

    public static ConsultTransactionWorkflow getByTransactionStatus(ConsultTransactionStatus status){
        for (ConsultTransactionWorkflow wf : values()){
            if(wf.getStatus().equals(status))
                return wf;
        }
        throw new RuntimeException("Transaction wf not found for status: " + status);
    }
}
