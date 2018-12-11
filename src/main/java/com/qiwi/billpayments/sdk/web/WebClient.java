package com.qiwi.billpayments.sdk.web;

import java.util.Map;
import java.util.Optional;

public interface WebClient {
    <T> T doRequest(
            String method,
            String url,
            Optional<Object> entityOpt,
            Class<T> responseClass,
            Map<String, String> headers
    );
}
