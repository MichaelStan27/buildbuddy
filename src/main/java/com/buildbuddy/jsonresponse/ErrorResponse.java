package com.buildbuddy.jsonresponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    @JsonProperty(value = "exceptionClass")
    private String exceptionClass;

    @JsonProperty(value = "errorMessage")
    private String errorMessage;

}
