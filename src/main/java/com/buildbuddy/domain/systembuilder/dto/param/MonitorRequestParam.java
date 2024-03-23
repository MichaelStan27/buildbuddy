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
public class MonitorRequestParam {

    private List<Integer> monitorIdList;
    private String name;
    private String manufacturer;
    private String price;
    private String productLink;
    private String screenSize;
    private String resolution;
    private String aspectRatio;
    private String responseTime;
    private String refreshRate;
    private String panelType;

    // Pagination
    @Builder.Default
    private boolean pagination = false;

    @Builder.Default
    private Integer pageNo = 0;

    @Builder.Default
    private Integer pageSize = 10;

    @Builder.Default
    private String sortBy = "name";

    @Builder.Default
    private String sortDirection = "asc";

    public List<ParamFilter> getFilters(){

        List<ParamFilter> paramFilters = new ArrayList<>();

        if(monitorIdList != null && !monitorIdList.isEmpty()){
            paramFilters.add(ParamFilter.builder()
                    .field("id")
                    .operator(QueryOperator.IN)
                    .values(monitorIdList)
                    .build());
        }

        if(name != null){
            paramFilters.add(ParamFilter.builder()
                    .field("name")
                    .operator(QueryOperator.LIKE)
                    .value(name)
                    .build());
        }

        if(manufacturer != null){
            paramFilters.add(ParamFilter.builder()
                    .field("manufacturer")
                    .operator(QueryOperator.LIKE)
                    .value(manufacturer)
                    .build());
        }

        if(price != null){
            paramFilters.add(ParamFilter.builder()
                    .field("price")
                    .operator(QueryOperator.LIKE)
                    .value(price)
                    .build());
        }

        if(productLink != null){
            paramFilters.add(ParamFilter.builder()
                    .field("productLink")
                    .operator(QueryOperator.LIKE)
                    .value(productLink)
                    .build());
        }

        if(screenSize != null){
            paramFilters.add(ParamFilter.builder()
                    .field("screenSize")
                    .operator(QueryOperator.LIKE)
                    .value(screenSize)
                    .build());
        }

        if(resolution != null){
            paramFilters.add(ParamFilter.builder()
                    .field("resolution")
                    .operator(QueryOperator.LIKE)
                    .value(resolution)
                    .build());
        }

        if(aspectRatio != null){
            paramFilters.add(ParamFilter.builder()
                    .field("aspectRatio")
                    .operator(QueryOperator.LIKE)
                    .value(aspectRatio)
                    .build());
        }

        if(responseTime != null){
            paramFilters.add(ParamFilter.builder()
                    .field("responseTime")
                    .operator(QueryOperator.LIKE)
                    .value(responseTime)
                    .build());
        }

        if(refreshRate != null){
            paramFilters.add(ParamFilter.builder()
                    .field("refreshRate")
                    .operator(QueryOperator.LIKE)
                    .value(refreshRate)
                    .build());
        }

        if(panelType != null){
            paramFilters.add(ParamFilter.builder()
                    .field("panelType")
                    .operator(QueryOperator.LIKE)
                    .value(panelType)
                    .build());
        }

        return paramFilters;

    }

}
