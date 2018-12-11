package com.qiwi.billpayments.sdk.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigDecimalSerializer extends JsonSerializer<BigDecimal> {

    @Override
    public void serialize(BigDecimal value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(value.setScale(2, RoundingMode.HALF_DOWN).toString());
    }

    @Override
    public Class<BigDecimal> handledType() {
        return BigDecimal.class;
    }
}
