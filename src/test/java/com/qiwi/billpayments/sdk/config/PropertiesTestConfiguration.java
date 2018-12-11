package com.qiwi.billpayments.sdk.config;

public class PropertiesTestConfiguration implements TestConfiguration {
    @Override
    public String getSecretKey() {
        return System.getProperty("qiwi.client.test.secret.key");
    }

    @Override
    public String getPublicKey() {
        return System.getProperty("qiwi.client.test.public.key");
    }

    @Override
    public String getBillIdForRefund() {
        return System.getProperty("qiwi.client.test.bill-id-for-refund");
    }
}
