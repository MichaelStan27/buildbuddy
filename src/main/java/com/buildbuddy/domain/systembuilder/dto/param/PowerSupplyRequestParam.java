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
public class PowerSupplyRequestParam {

    private List<Integer> powerSupplyIdList;
    private String name;
    private String manufacturer;
    private String price;
    private String productLink;
    private String power;
    private String color;
    private String formFactor;
    private String efficiency;

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

        if (powerSupplyIdList != null && !powerSupplyIdList.isEmpty()) {
            paramFilters.add(ParamFilter.builder()
                    .field("id")
                    .operator(QueryOperator.IN)
                    .values(powerSupplyIdList)
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

        if (power != null) {
            paramFilters.add(ParamFilter.builder()
                    .field("power")
                    .operator(QueryOperator.LIKE)
                    .value(power)
                    .build());
        }

        if (color != null) {
            paramFilters.add(ParamFilter.builder()
                    .field("color")
                    .operator(QueryOperator.LIKE)
                    .value(color)
                    .build());
        }

        if (formFactor != null) {
            paramFilters.add(ParamFilter.builder()
                    .field("formFactor")
                    .operator(QueryOperator.LIKE)
                    .value(formFactor)
                    .build());
        }

        if (efficiency != null) {
            paramFilters.add(ParamFilter.builder()
                    .field("efficiency")
                    .operator(QueryOperator.LIKE)
                    .value(efficiency)
                    .build());
        }

        return paramFilters;

    }
}
