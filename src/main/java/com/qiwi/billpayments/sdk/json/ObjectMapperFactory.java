package com.qiwi.billpayments.sdk.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class ObjectMapperFactory {
    public static ObjectMapper create() {
        return new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .registerModule(new SimpleModule().addSerializer(new BigDecimalSerializer()))
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }
}
