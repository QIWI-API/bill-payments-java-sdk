package com.qiwi.billpayments.sdk.model.in;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomFields {
    private final String apiClient;
    private final String apiClientVersion;

    @JsonCreator
    public CustomFields(
            @JsonProperty("apiClient") String apiClient,
            @JsonProperty("apiClientVersion") String apiClientVersion
    ) {
        this.apiClient = apiClient;
        this.apiClientVersion = apiClientVersion;
    }

    public String getApiClient() {
        return apiClient;
    }

    public String getApiClientVersion() {
        return apiClientVersion;
    }

    @Override
    public String toString() {
        return "CustomFields{" +
                "apiClient='" + apiClient + '\'' +
                ", apiClientVersion='" + apiClientVersion + '\'' +
                '}';
    }
}
