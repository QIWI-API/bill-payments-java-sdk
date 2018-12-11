package com.qiwi.billpayments.sdk.model.out;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.qiwi.billpayments.sdk.model.MoneyAmount;
import com.qiwi.billpayments.sdk.model.in.CustomFields;
import com.qiwi.billpayments.sdk.model.in.Customer;

import java.time.ZonedDateTime;

public class BillResponse {
    private final String siteId;
    private final String billId;
    private final MoneyAmount amount;
    private final ResponseStatus status;
    private final String comment;
    private final Customer customer;
    private final ZonedDateTime creationDateTime;
    private final ZonedDateTime expirationDateTime;
    private final String payUrl;
    private final CustomFields customFields;

    @JsonCreator
    public BillResponse(
            @JsonProperty("siteId") String siteId,
            @JsonProperty("billId") String billId,
            @JsonProperty("amount") MoneyAmount amount,
            @JsonProperty("status") ResponseStatus status,
            @JsonProperty("comment") String comment,
            @JsonProperty("customer") Customer customer,
            @JsonProperty("creationDateTime") ZonedDateTime creationDateTime,
            @JsonProperty("expirationDateTime") ZonedDateTime expirationDateTime,
            @JsonProperty("payUrl") String payUrl,
            @JsonProperty("customFields") CustomFields customFields
    ) {
        this.siteId = siteId;
        this.billId = billId;
        this.amount = amount;
        this.status = status;
        this.comment = comment;
        this.customer = customer;
        this.creationDateTime = creationDateTime;
        this.expirationDateTime = expirationDateTime;
        this.payUrl = payUrl;
        this.customFields = customFields;
    }

    public BillResponse withNewPayUrl(String payUrl) {
        return new BillResponse(
                this.siteId,
                this.billId,
                this.amount,
                this.status,
                this.comment,
                this.customer,
                this.creationDateTime,
                this.expirationDateTime,
                payUrl,
                this.customFields
        );
    }

    public String getSiteId() {
        return siteId;
    }

    public String getBillId() {
        return billId;
    }

    public MoneyAmount getAmount() {
        return amount;
    }

    public ResponseStatus getStatus() {
        return status;
    }

    public String getComment() {
        return comment;
    }

    public ZonedDateTime getCreationDateTime() {
        return creationDateTime;
    }

    public ZonedDateTime getExpirationDateTime() {
        return expirationDateTime;
    }

    public String getPayUrl() {
        return payUrl;
    }

    public Customer getCustomer() {
        return customer;
    }

    public CustomFields getCustomFields() {
        return customFields;
    }

    @Override
    public String toString() {
        return "BillResponse{" +
                "siteId='" + siteId + '\'' +
                ", billId='" + billId + '\'' +
                ", amount=" + amount +
                ", status=" + status +
                ", comment='" + comment + '\'' +
                ", customer=" + customer +
                ", creationDateTime=" + creationDateTime +
                ", expirationDateTime=" + expirationDateTime +
                ", payUrl='" + payUrl + '\'' +
                ", customFields=" + customFields +
                '}';
    }
}
