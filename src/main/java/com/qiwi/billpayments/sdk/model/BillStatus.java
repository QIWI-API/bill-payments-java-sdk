package com.qiwi.billpayments.sdk.model;

public enum BillStatus {
    WAITING("WAITING"),
    PAID("PAID"),
    REJECTED("REJECTED"),
    EXPIRED("EXPIRED");

    private final String value;

    BillStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "BillStatus{" +
                "value='" + value + '\'' +
                '}';
    }
}
