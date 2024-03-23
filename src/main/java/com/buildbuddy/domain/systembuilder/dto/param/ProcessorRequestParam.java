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
public class ProcessorRequestParam {

    private List<Integer> processorIdList;
    private String name;
    private String manufacturer;
    private String price;
    private String productLink;
    private String socket;
    private String series;
    private String core;
    private String integratedGraphics;
    private String microArchitecture;
    private Integer benchmark;

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

        if (processorIdList != null && !processorIdList.isEmpty()) {
            paramFilters.add(ParamFilter.builder()
                    .field("id")
                    .operator(QueryOperator.IN)
                    .values(processorIdList)
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

        if (socket != null) {
            paramFilters.add(ParamFilter.builder()
                    .field("socket")
                    .operator(QueryOperator.LIKE)
                    .value(socket)
                    .build());
        }

        if (series != null) {
            paramFilters.add(ParamFilter.builder()
                    .field("series")
                    .operator(QueryOperator.LIKE)
                    .value(series)
                    .build());
        }

        if (core != null) {
            paramFilters.add(ParamFilter.builder()
                    .field("core")
                    .operator(QueryOperator.LIKE)
                    .value(core)
                    .build());
        }

        if (integratedGraphics != null) {
            paramFilters.add(ParamFilter.builder()
                    .field("integratedGraphics")
                    .operator(QueryOperator.LIKE)
                    .value(integratedGraphics)
                    .build());
        }

        if (microArchitecture != null) {
            paramFilters.add(ParamFilter.builder()
                    .field("microArchitecture")
                    .operator(QueryOperator.LIKE)
                    .value(microArchitecture)
                    .build());
        }

        if (benchmark != null) {
            paramFilters.add(ParamFilter.builder()
                    .field("benchmark")
                    .operator(QueryOperator.LIKE)
                    .value(benchmark)
                    .build());
        }

        return paramFilters;

    }

}
