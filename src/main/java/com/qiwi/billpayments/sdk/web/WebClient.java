package com.qiwi.billpayments.sdk.web;

import com.qiwi.billpayments.sdk.model.ResponseData;

import java.util.Map;
import java.util.Optional;

public interface WebClient {
    ResponseData request(
            String method,
            String url,
            Optional<String> entityOpt,
            Map<String, String> headers
    );
}
