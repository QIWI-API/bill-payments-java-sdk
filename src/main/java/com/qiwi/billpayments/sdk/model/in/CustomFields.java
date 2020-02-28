package com.qiwi.billpayments.sdk.model.in;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomFields {
    private final String apiClient;
    private final String apiClientVersion;
    private final String checkoutReferer;

    @JsonCreator
    public CustomFields(
            @JsonProperty("apiClient") String apiClient,
            @JsonProperty("apiClientVersion") String apiClientVersion,
            @JsonProperty("CHECKOUT_REFERER") String checkoutReferer
    ) {
        this.apiClient = apiClient;
        this.apiClientVersion = apiClientVersion;
        this.checkoutReferer = checkoutReferer;
    }

    public String getApiClient() {
        return apiClient;
    }

    public String getApiClientVersion() {
        return apiClientVersion;
    }

    public String getCheckoutReferer() {
        return checkoutReferer;
    }

    @Override
    public String toString() {
        return "CustomFields{" +
                "apiClient='" + apiClient + '\'' +
                ", apiClientVersion='" + apiClientVersion + '\'' +
                ", checkoutReferer='" + checkoutReferer + '\'' +
                '}';
    }

}
