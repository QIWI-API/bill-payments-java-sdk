package com.qiwi.billpayments.sdk.model;

public class Notification {
    private final Bill bill;
    private final String version;

    public Notification(Bill bill, String version) {
        this.bill = bill;
        this.version = version;
    }

    public Bill getBill() {
        return bill;
    }

    public String getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "bill=" + bill +
                ", version='" + version + '\'' +
                '}';
    }
}
