package com.buildbuddy.jsonresponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataResponse<T> {

    @JsonProperty(value = "timestamp")
    private LocalDateTime timestamp;

    @JsonProperty(value = "httpStatus")
    private HttpStatus httpStatus;

    @JsonProperty(value = "error")
    private ErrorResponse error;

    @JsonProperty(value = "message")
    private String message;

    @JsonProperty(value = "data")
    private T data;
}
