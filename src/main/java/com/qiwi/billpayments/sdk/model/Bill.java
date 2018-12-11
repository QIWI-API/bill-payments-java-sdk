package com.qiwi.billpayments.sdk.model;

public class Bill {
    private final String siteId;
    private final String billId;
    private final MoneyAmount amount;
    private final BillStatus status;

    public Bill(String siteId, String billId, MoneyAmount amount, BillStatus status) {
        this.siteId = siteId;
        this.billId = billId;
        this.amount = amount;
        this.status = status;
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

    public BillStatus getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "siteId='" + siteId + '\'' +
                ", billId='" + billId + '\'' +
                ", amount=" + amount +
                ", status=" + status +
                '}';
    }
}
