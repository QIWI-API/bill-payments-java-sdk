package com.qiwi.billpayments.sdk.model;

public class ResponseData {
    private final String body;
    private final int httpStatus;

    public ResponseData(String body, int httpStatus) {
        this.body = body;
        this.httpStatus = httpStatus;
    }

    public String getBody() {
        return body;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String toString() {
        return "ResponseData{" +
                "body='" + body + '\'' +
                ", httpStatus=" + httpStatus +
                '}';
    }
}
