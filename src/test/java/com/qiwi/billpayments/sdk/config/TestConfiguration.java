package com.qiwi.billpayments.sdk.config;

public interface TestConfiguration {
    String getSecretKey();

    String getPublicKey();

    String getBillIdForRefund();
}
