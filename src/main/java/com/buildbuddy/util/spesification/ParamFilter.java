package com.buildbuddy.util.spesification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParamFilter {

    private String field;
    private QueryOperator operator;
    private Object value;
    private List<?> values;

}
