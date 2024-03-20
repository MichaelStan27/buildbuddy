package com.buildbuddy.domain.systembuilder.dto.param;

import com.buildbuddy.util.spesification.ParamFilter;
import com.buildbuddy.util.spesification.QueryOperator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComputerSetupRequestParam {

    private List<Integer> computerSetupIdList;
    private List<String> usernameList;
//    private List<Integer> caseList;
//    private List<Integer> coolerList;
//    private List<Integer> graphicsCardList;
//    private List<Integer> monitorList;
//    private List<Integer> motherboardList;
//    private List<Integer> powersupplyList;
//    private List<Integer> processorList;
//    private List<Integer> ramList;
//    private List<Integer> storageList;

    // Pagination
    @Builder.Default
    private boolean pagination = false;

    @Builder.Default
    private Integer pageNo = 0;

    @Builder.Default
    private Integer pageSize = 10;

    @Builder.Default
    private String sortBy = "lastUpdateTime";

    @Builder.Default
    private String sortDirection = "asc";

    public List<ParamFilter> getFilters(){

        List<ParamFilter> paramFilters = new ArrayList<>();

        if(computerSetupIdList != null && !computerSetupIdList.isEmpty()){
            paramFilters.add(ParamFilter.builder()
                    .field("id")
                    .operator(QueryOperator.IN)
                    .values(computerSetupIdList)
                    .build());
        }

        if(usernameList != null && !usernameList.isEmpty()){
            paramFilters.add(ParamFilter.builder()
                    .field("username")
                    .operator(QueryOperator.USERNAME)
                    .values(usernameList)
                    .build());
        }

//        if(caseList != null && !caseList.isEmpty()){
//            paramFilters.add(ParamFilter.builder()
//                    .field("coolerId")
//                    .operator(QueryOperator.IN)
//                    .values(caseList)
//                    .build());
//        }
//
//        if(coolerList != null && !coolerList.isEmpty()){
//            paramFilters.add(ParamFilter.builder()
//                    .field("coolerId")
//                    .operator(QueryOperator.IN)
//                    .values(coolerList)
//                    .build());
//        }
//
//        if(graphicsCardList != null && !graphicsCardList.isEmpty()){
//            paramFilters.add(ParamFilter.builder()
//                    .field("graphicsCardId")
//                    .operator(QueryOperator.IN)
//                    .values(graphicsCardList)
//                    .build());
//        }
//
//        if(monitorList != null && !monitorList.isEmpty()){
//            paramFilters.add(ParamFilter.builder()
//                    .field("monitorId")
//                    .operator(QueryOperator.IN)
//                    .values(monitorList)
//                    .build());
//        }
//
//        if(motherboardList != null && !motherboardList.isEmpty()){
//            paramFilters.add(ParamFilter.builder()
//                    .field("motherboardId")
//                    .operator(QueryOperator.IN)
//                    .values(motherboardList)
//                    .build());
//        }
//
//        if(powersupplyList != null && !powersupplyList.isEmpty()){
//            paramFilters.add(ParamFilter.builder()
//                    .field("powersupplyId")
//                    .operator(QueryOperator.IN)
//                    .values(powersupplyList)
//                    .build());
//        }
//
//        if(processorList != null && !processorList.isEmpty()){
//            paramFilters.add(ParamFilter.builder()
//                    .field("processorId")
//                    .operator(QueryOperator.IN)
//                    .values(processorList)
//                    .build());
//        }
//
//        if(ramList != null && !ramList.isEmpty()){
//            paramFilters.add(ParamFilter.builder()
//                    .field("ramId")
//                    .operator(QueryOperator.IN)
//                    .values(ramList)
//                    .build());
//        }
//
//        if(storageList != null && !storageList.isEmpty()){
//            paramFilters.add(ParamFilter.builder()
//                    .field("storageId")
//                    .operator(QueryOperator.IN)
//                    .values(storageList)
//                    .build());
//        }

        return paramFilters;

    }


}
