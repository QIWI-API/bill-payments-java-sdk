package com.qiwi.billpayments.sdk.exception;

import com.qiwi.billpayments.sdk.model.ResponseData;

public class BadResponseException extends RuntimeException {
    private final int httpStatus;

    public BadResponseException(ResponseData responseData) {
        super(responseData.toString());
        this.httpStatus = responseData.getHttpStatus();
    }

    public BadResponseException(int httpStatus) {
        super("empty body, HTTP status " + httpStatus);
        this.httpStatus = httpStatus;
    }

    public int getHttpStatus() {
        return httpStatus;
    }
}
