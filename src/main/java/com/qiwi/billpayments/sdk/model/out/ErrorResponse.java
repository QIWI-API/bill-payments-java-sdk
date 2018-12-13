package com.qiwi.billpayments.sdk.model.out;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.ZonedDateTime;

public class ErrorResponse {
    private final String serviceName;
    private final String errorCode;
    private final String description;
    private final String userMessage;
    private final ZonedDateTime dateTime;
    private final String traceId;

    @JsonCreator
    public ErrorResponse(
            @JsonProperty("serviceName") String serviceName,
            @JsonProperty("errorCode") String errorCode,
            @JsonProperty("description") String description,
            @JsonProperty("userMessage") String userMessage,
            @JsonProperty("dateTime") ZonedDateTime dateTime,
            @JsonProperty("traceId") String traceId
    ) {
        this.serviceName = serviceName;
        this.errorCode = errorCode;
        this.description = description;
        this.userMessage = userMessage;
        this.dateTime = dateTime;
        this.traceId = traceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getDescription() {
        return description;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public ZonedDateTime getDateTime() {
        return dateTime;
    }

    public String getTraceId() {
        return traceId;
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "serviceName='" + serviceName + '\'' +
                ", errorCode='" + errorCode + '\'' +
                ", description='" + description + '\'' +
                ", userMessage='" + userMessage + '\'' +
                ", dateTime=" + dateTime +
                ", traceId='" + traceId + '\'' +
                '}';
    }
}
