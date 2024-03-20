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
public class MotherboardRequestParam {

    private List<Integer> motherboardIdList;
    private String name;
    private String manufacturer;
    private String price;
    private String productLink;
    private String chipset;
    private String socketType;
    private String formFactor;
    private String memorySlots;
    private String maxMemory;

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

    public List<ParamFilter> getFilters() {

        List<ParamFilter> paramFilters = new ArrayList<>();

        if (motherboardIdList != null && !motherboardIdList.isEmpty()) {
            paramFilters.add(ParamFilter.builder()
                    .field("id")
                    .operator(QueryOperator.IN)
                    .values(motherboardIdList)
                    .build());
        }

        if (name != null) {
            paramFilters.add(ParamFilter.builder()
                    .field("name")
                    .operator(QueryOperator.LIKE)
                    .value(name)
                    .build());
        }

        if (manufacturer != null) {
            paramFilters.add(ParamFilter.builder()
                    .field("manufacturer")
                    .operator(QueryOperator.LIKE)
                    .value(manufacturer)
                    .build());
        }

        if (price != null) {
            paramFilters.add(ParamFilter.builder()
                    .field("price")
                    .operator(QueryOperator.LIKE)
                    .value(price)
                    .build());
        }

        if (productLink != null) {
            paramFilters.add(ParamFilter.builder()
                    .field("productLink")
                    .operator(QueryOperator.LIKE)
                    .value(productLink)
                    .build());
        }

        if (chipset != null) {
            paramFilters.add(ParamFilter.builder()
                    .field("chipset")
                    .operator(QueryOperator.LIKE)
                    .value(chipset)
                    .build());
        }

        if (socketType != null) {
            paramFilters.add(ParamFilter.builder()
                    .field("socketType")
                    .operator(QueryOperator.LIKE)
                    .value(socketType)
                    .build());
        }

        if (formFactor != null) {
            paramFilters.add(ParamFilter.builder()
                    .field("formFactor")
                    .operator(QueryOperator.LIKE)
                    .value(formFactor)
                    .build());
        }

        if (memorySlots != null) {
            paramFilters.add(ParamFilter.builder()
                    .field("memorySlots")
                    .operator(QueryOperator.LIKE)
                    .value(memorySlots)
                    .build());
        }

        if (maxMemory != null) {
            paramFilters.add(ParamFilter.builder()
                    .field("maxMemory")
                    .operator(QueryOperator.LIKE)
                    .value(maxMemory)
                    .build());
        }

        return paramFilters;

    }

}
