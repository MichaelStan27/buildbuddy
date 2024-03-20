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
public class RamRequestParam {

    private List<Integer> ramIdList;
    private String name;
    private String manufacturer;
    private String price;
    private String productLink;
    private Integer ramSize;
    private Integer ramQuantity;
    private String ramSpeed;
    private String ramType;
    private String casLatency;

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

        if(ramIdList != null && !ramIdList.isEmpty()){
            paramFilters.add(ParamFilter.builder()
                    .field("id")
                    .operator(QueryOperator.IN)
                    .values(ramIdList)
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

        if(ramSize != null){
            paramFilters.add(ParamFilter.builder()
                    .field("ramSize")
                    .operator(QueryOperator.LIKE)
                    .value(ramSize)
                    .build());
        }

        if(ramQuantity != null){
            paramFilters.add(ParamFilter.builder()
                    .field("ramQuantity")
                    .operator(QueryOperator.LIKE)
                    .value(ramQuantity)
                    .build());
        }

        if(ramSpeed != null){
            paramFilters.add(ParamFilter.builder()
                    .field("ramSpeed")
                    .operator(QueryOperator.LIKE)
                    .value(ramSpeed)
                    .build());
        }

        if(ramType != null){
            paramFilters.add(ParamFilter.builder()
                    .field("ramType")
                    .operator(QueryOperator.LIKE)
                    .value(ramType)
                    .build());
        }

        if(casLatency != null){
            paramFilters.add(ParamFilter.builder()
                    .field("casLatency")
                    .operator(QueryOperator.LIKE)
                    .value(casLatency)
                    .build());
        }

        return paramFilters;

    }

}
