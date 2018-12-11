package com.qiwi.billpayments.sdk.model.in;

import com.qiwi.billpayments.sdk.model.MoneyAmount;

public class PaymentInfo {
    private final String publicKey;
    private final MoneyAmount amount;
    private final String billId;
    private final String successUrl;

    public PaymentInfo(
            String publicKey,
            MoneyAmount amount,
            String billId,
            String successUrl
    ) {
        this.publicKey = publicKey;
        this.amount = amount;
        this.billId = billId;
        this.successUrl = successUrl;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public MoneyAmount getAmount() {
        return amount;
    }

    public String getBillId() {
        return billId;
    }

    public String getSuccessUrl() {
        return successUrl;
    }

    @Override
    public String toString() {
        return "PaymentInfo{" +
                "publicKey='" + publicKey + '\'' +
                ", amount=" + amount +
                ", billId='" + billId + '\'' +
                ", successUrl='" + successUrl + '\'' +
                '}';
    }
}
