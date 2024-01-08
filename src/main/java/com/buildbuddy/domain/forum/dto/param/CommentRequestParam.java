package com.buildbuddy.domain.forum.dto.param;

import com.buildbuddy.util.spesification.ParamFilter;
import com.buildbuddy.util.spesification.QueryOperator;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class CommentRequestParam {

    @JsonProperty(value = "commentIdList")
    private List<Integer> commentIdList;

    @JsonProperty(value = "threadIdList")
    private List<Integer> threadIdList;

    @JsonProperty(value = "usernameList")
    private List<String> usernameList;

    @JsonProperty(value = "message")
    private String message;

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

        if(commentIdList != null && !commentIdList.isEmpty()){
            paramFilters.add(ParamFilter.builder()
                            .field("id")
                            .operator(QueryOperator.IN)
                            .values(commentIdList)
                    .build());
        }

        if(threadIdList != null && !threadIdList.isEmpty()){
            paramFilters.add(ParamFilter.builder()
                            .field("id")
                            .operator(QueryOperator.THREAD_ID)
                            .values(threadIdList)
                    .build());
        }

        if(usernameList != null && !usernameList.isEmpty()){
            paramFilters.add(ParamFilter.builder()
                    .field("username")
                    .operator(QueryOperator.USERNAME)
                    .values(usernameList)
                    .build());
        }

        if(message != null){
            paramFilters.add(ParamFilter.builder()
                    .field("message")
                    .operator(QueryOperator.LIKE)
                    .value(message)
                    .build());
        }

        return paramFilters;
    }

}
