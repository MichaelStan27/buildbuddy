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
public class StorageRequestParam {

    private List<Integer> storageIdList;
    private String name;
    private String manufacturer;
    private String price;
    private String productLink;
    private String storageType;
    private Integer capacity;
    private String formFactor;
    private String rpm;
    private String storageInterface;
    private String cacheMemory;

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

        if(storageIdList != null && !storageIdList.isEmpty()){
            paramFilters.add(ParamFilter.builder()
                    .field("id")
                    .operator(QueryOperator.IN)
                    .values(storageIdList)
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

        if(storageType != null){
            paramFilters.add(ParamFilter.builder()
                    .field("storageType")
                    .operator(QueryOperator.LIKE)
                    .value(storageType)
                    .build());
        }

        if(capacity != null){
            paramFilters.add(ParamFilter.builder()
                    .field("capacity")
                    .operator(QueryOperator.LIKE)
                    .value(capacity)
                    .build());
        }

        if(formFactor != null){
            paramFilters.add(ParamFilter.builder()
                    .field("formFactor")
                    .operator(QueryOperator.LIKE)
                    .value(formFactor)
                    .build());
        }

        if(rpm != null){
            paramFilters.add(ParamFilter.builder()
                    .field("rpm")
                    .operator(QueryOperator.LIKE)
                    .value(rpm)
                    .build());
        }

        if(storageInterface != null){
            paramFilters.add(ParamFilter.builder()
                    .field("storageInterface")
                    .operator(QueryOperator.LIKE)
                    .value(storageInterface)
                    .build());
        }

        if(cacheMemory != null){
            paramFilters.add(ParamFilter.builder()
                    .field("cacheMemory")
                    .operator(QueryOperator.LIKE)
                    .value(cacheMemory)
                    .build());
        }

        return paramFilters;

    }

}
