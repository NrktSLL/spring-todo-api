package com.nrkt.springboottodoapi.error;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.Date;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class ErrorModel implements Serializable {
    @JsonProperty("message")
    String message;
    @JsonProperty("timestamp")
    Date timestamp;
    @JsonProperty("status")
    Integer status;
    @JsonProperty("error")
    String error;
    @JsonProperty("path")
    String path;
}

