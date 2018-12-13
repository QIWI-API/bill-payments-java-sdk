package com.qiwi.billpayments.sdk.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qiwi.billpayments.sdk.exception.BillPaymentServiceException;
import com.qiwi.billpayments.sdk.exception.SerializationException;
import com.qiwi.billpayments.sdk.exception.BadResponseException;
import com.qiwi.billpayments.sdk.json.ObjectMapperFactory;
import com.qiwi.billpayments.sdk.model.ResponseData;
import com.qiwi.billpayments.sdk.model.out.ErrorResponse;
import com.qiwi.billpayments.sdk.web.WebClient;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

public class RequestMappingIntercessor {
    private final ObjectMapper mapper = ObjectMapperFactory.create();
    private final WebClient webClient;

    public RequestMappingIntercessor(WebClient webClient) {
        this.webClient = webClient;
    }

    public <T> T request(
            String method,
            String url,
            Optional<Object> entityOpt,
            Class<T> responseClass,
            Map<String, String> headers
    ) {
        Optional<String> jsonOpt = serializeRequestBody(entityOpt);
        ResponseData response = webClient.request(method, url, jsonOpt, headers);
        return deserializeResponseBody(responseClass, response);
    }

    private Optional<String> serializeRequestBody(Optional<Object> entityOpt) {
        return entityOpt.map(ent -> {
                try {
                    return mapper.writeValueAsString(ent);
                } catch (JsonProcessingException e) {
                    throw new SerializationException(e);
                }
            });
    }

    private <T> T deserializeResponseBody(Class<T> responseClass, ResponseData response) {
        try {
            if (response.getBody() == null) {
                throw new BadResponseException(response.getHttpStatus());
            }
            return mapper.readValue(response.getBody(), responseClass);
        } catch (IOException e) {
            throw mapToError(response);
        }
    }

    private BillPaymentServiceException mapToError(ResponseData response) {
        try {
            ErrorResponse errorResponse = mapper.readValue(response.getBody(), ErrorResponse.class);
            return new BillPaymentServiceException(errorResponse, response.getHttpStatus());
        } catch (IOException e) {
            throw new BadResponseException(response);
        }
    }
}
