package com.qiwi.billpayments.sdk.model.out;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.qiwi.billpayments.sdk.model.BillStatus;

import java.time.ZonedDateTime;

public class ResponseStatus {
    private final BillStatus value;
    private final ZonedDateTime changedDateTime;

    @JsonCreator
    public ResponseStatus(
            @JsonProperty("value") BillStatus value,
            @JsonProperty("changedDateTime") ZonedDateTime changedDateTime
    ) {
        this.value = value;
        this.changedDateTime = changedDateTime;
    }

    public BillStatus getValue() {
        return value;
    }

    public ZonedDateTime getChangedDateTime() {
        return changedDateTime;
    }

    @Override
    public String toString() {
        return "ResponseStatus{" +
                "value=" + value +
                ", changedDateTime=" + changedDateTime +
                '}';
    }
}
