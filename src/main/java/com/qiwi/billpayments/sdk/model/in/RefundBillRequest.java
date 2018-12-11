package com.qiwi.billpayments.sdk.model.in;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.qiwi.billpayments.sdk.model.MoneyAmount;

public class RefundBillRequest {
    private final MoneyAmount amount;

    @JsonCreator
    public RefundBillRequest(@JsonProperty("amount") MoneyAmount amount) {
        this.amount = amount;
    }

    public MoneyAmount getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "RefundBillRequest{" +
                "amount=" + amount +
                '}';
    }
}
