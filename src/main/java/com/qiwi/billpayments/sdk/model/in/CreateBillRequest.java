package com.qiwi.billpayments.sdk.model.in;

import com.qiwi.billpayments.sdk.model.MoneyAmount;

import java.time.ZonedDateTime;

public class CreateBillRequest {
    private final MoneyAmount amount;
    private final String comment;
    private final ZonedDateTime expirationDateTime;
    private final Customer customer;
    private final CustomFields customFields;

    public CreateBillRequest(
            MoneyAmount amount,
            String comment,
            ZonedDateTime expirationDateTime,
            Customer customer,
            CustomFields customFields
    ) {
        this.amount = amount;
        this.comment = comment;
        this.expirationDateTime = expirationDateTime;
        this.customer = customer;
        this.customFields = customFields;
    }

    public static CreateBillRequest create(CreateBillInfo info, CustomFields customFields) {
        return new CreateBillRequest(
                info.getAmount(),
                info.getComment(),
                info.getExpirationDateTime(),
                info.getCustomer(),
                customFields
        );
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

    public CustomFields getCustomFields() {
        return customFields;
    }

    @Override
    public String toString() {
        return "CreateBillRequest{" +
                "amount=" + amount +
                ", comment='" + comment + '\'' +
                ", expirationDateTime=" + expirationDateTime +
                ", customer=" + customer +
                ", customFields=" + customFields +
                '}';
    }
}
