package com.qiwi.billpayments.sdk.model.out;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.qiwi.billpayments.sdk.model.MoneyAmount;

import java.time.ZonedDateTime;

public class RefundResponse {
    private final MoneyAmount amount;
    private final ZonedDateTime dateTime;
    private final String refundId;
    private final RefundStatus status;

    @JsonCreator
    public RefundResponse(
            @JsonProperty("amount") MoneyAmount amount,
            @JsonProperty("dateTime") ZonedDateTime dateTime,
            @JsonProperty("refundId") String refundId,
            @JsonProperty("status") RefundStatus status
    ) {
        this.amount = amount;
        this.dateTime = dateTime;
        this.refundId = refundId;
        this.status = status;
    }

    public MoneyAmount getAmount() {
        return amount;
    }

    public ZonedDateTime getDateTime() {
        return dateTime;
    }

    public String getRefundId() {
        return refundId;
    }

    public RefundStatus getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "RefundResponse{" +
                "amount=" + amount +
                ", dateTime=" + dateTime +
                ", refundId='" + refundId + '\'' +
                ", status=" + status +
                '}';
    }
}
