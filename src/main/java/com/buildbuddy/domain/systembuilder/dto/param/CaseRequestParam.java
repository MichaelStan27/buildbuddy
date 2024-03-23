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
public class CaseRequestParam {

    private List<Integer> caseIdList;
    private String name;
    private String manufacturer;
    private String price;
    private String productLink;
    private String sidePanel;
    private String cabinetType;
    private String color;

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

        if(caseIdList != null && !caseIdList.isEmpty()){
            paramFilters.add(ParamFilter.builder()
                    .field("id")
                    .operator(QueryOperator.IN)
                    .values(caseIdList)
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

        if(sidePanel != null){
            paramFilters.add(ParamFilter.builder()
                    .field("sidePanel")
                    .operator(QueryOperator.LIKE)
                    .value(sidePanel)
                    .build());
        }

        if(cabinetType != null){
            paramFilters.add(ParamFilter.builder()
                    .field("cabinetType")
                    .operator(QueryOperator.LIKE)
                    .value(cabinetType)
                    .build());
        }

        if(color != null){
            paramFilters.add(ParamFilter.builder()
                    .field("color")
                    .operator(QueryOperator.LIKE)
                    .value(color)
                    .build());
        }

        return paramFilters;

    }

}
