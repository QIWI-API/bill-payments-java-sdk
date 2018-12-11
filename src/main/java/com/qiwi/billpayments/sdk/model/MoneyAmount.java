package com.qiwi.billpayments.sdk.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Objects;

public class MoneyAmount {
    private final BigDecimal value;
    private final Currency currency;

    @JsonCreator
    public MoneyAmount(
            @JsonProperty("value") BigDecimal value,
            @JsonProperty("currency") Currency currency
    ) {
        this.value = value;
        this.currency = currency;
    }

    public BigDecimal getValue() {
        return value;
    }

    public Currency getCurrency() {
        return currency;
    }

    public String formatValue() {
        return value.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MoneyAmount that = (MoneyAmount) o;
        return Objects.equals(value, that.value) &&
                Objects.equals(currency, that.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, currency);
    }

    @Override
    public String toString() {
        return "MoneyAmount{" +
                "value=" + value +
                ", currency=" + currency +
                '}';
    }
}
