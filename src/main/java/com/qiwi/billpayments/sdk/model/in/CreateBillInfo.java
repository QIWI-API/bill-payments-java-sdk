package com.qiwi.billpayments.sdk.model.in;

import com.qiwi.billpayments.sdk.model.MoneyAmount;

import java.time.ZonedDateTime;

public class CreateBillInfo {
    private final String billId;
    private final MoneyAmount amount;
    private final String comment;
    private final ZonedDateTime expirationDateTime;
    private final Customer customer;
    private final String successUrl;

    public CreateBillInfo(
            String billId,
            MoneyAmount amount,
            String comment,
            ZonedDateTime expirationDateTime,
            Customer customer,
            String successUrl
    ) {
        this.billId = billId;
        this.amount = amount;
        this.comment = comment;
        this.expirationDateTime = expirationDateTime;
        this.customer = customer;
        this.successUrl = successUrl;
    }

    public String getBillId() {
        return billId;
    }

    public MoneyAmount getAmount() {
        return amount;
    }

    public String getComment() {
        return comment;
    }

    public ZonedDateTime getExpirationDateTime() {
        return expirationDateTime;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getSuccessUrl() {
        return successUrl;
    }

    @Override
    public String toString() {
        return "CreateBillInfo{" +
                "billId='" + billId + '\'' +
                ", amount=" + amount +
                ", comment='" + comment + '\'' +
                ", expirationDateTime=" + expirationDateTime +
                ", customer=" + customer +
                ", successUrl='" + successUrl + '\'' +
                '}';
    }
}
