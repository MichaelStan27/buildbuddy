package com.buildbuddy.domain.consult.dto.param;

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
public class RoomChatReqParam {

    private List<String> roomIdList;

    private List<Integer> userIdList;

    private List<Integer> consultantIdList;

    @Builder.Default
    private boolean expired = false;

    @Builder.Default
    private boolean pagination = false;

    @Builder.Default
    private Integer pageNo = 0;

    @Builder.Default
    private Integer pageSize = 10;

    @Builder.Default
    private String sortBy = "createdTime";

    @Builder.Default
    private String sortDirection = "asc";

    public List<ParamFilter> getFilter(){
        List<ParamFilter> filters = new ArrayList<>();

        if(roomIdList != null && !roomIdList.isEmpty()){
            filters.add(ParamFilter.builder()
                            .field("roomId")
                            .operator(QueryOperator.IN)
                            .values(roomIdList)
                    .build());
        }

        if(userIdList != null && !userIdList.isEmpty()){
            filters.add(ParamFilter.builder()
                    .field("userId")
                    .operator(QueryOperator.IN)
                    .values(userIdList)
                    .build());
        }

        if(consultantIdList != null && !consultantIdList.isEmpty()){
            filters.add(ParamFilter.builder()
                    .field("consultantId")
                    .operator(QueryOperator.IN)
                    .values(consultantIdList)
                    .build());
        }

        return filters;
    }

}
