package com.qiwi.billpayments.sdk.model.in;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Customer {
    private final String email;
    private final String account;
    private final String phone;

    @JsonCreator
    public Customer(
            @JsonProperty("email") String email,
            @JsonProperty("account") String account,
            @JsonProperty("phone") String phone
    ) {
        this.email = email;
        this.account = account;
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public String getAccount() {
        return account;
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "email='" + email + '\'' +
                ", account='" + account + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
