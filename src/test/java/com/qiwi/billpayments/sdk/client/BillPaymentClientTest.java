package com.qiwi.billpayments.sdk.client;

import com.qiwi.billpayments.sdk.model.BillStatus;
import com.qiwi.billpayments.sdk.model.MoneyAmount;
import com.qiwi.billpayments.sdk.model.in.CustomFields;
import com.qiwi.billpayments.sdk.model.in.Customer;
import com.qiwi.billpayments.sdk.model.out.BillResponse;
import com.qiwi.billpayments.sdk.model.out.ResponseStatus;
import org.apache.http.HttpHeaders;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.Currency;
import java.util.Map;
import java.util.UUID;

public class BillPaymentClientTest {
    @Test
    public void testPrepareHeaders() {
        //given
        String token = "token";
        String bearerPrefix = "Bearer ";

        //when
        Map<String, String> headers = BillPaymentClient.prepareHeaders(token);

        //then
        Assert.assertEquals(
                "headers should contain bearer token",
                headers.get(HttpHeaders.AUTHORIZATION),
                bearerPrefix + token
        );
    }

    @Test
    public void testAppendSuccessUrl() throws Exception {
        //given
        String successUrl = "http://success-url.ru/";
        BillResponse response = new BillResponse(
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                new MoneyAmount(
                        BigDecimal.valueOf(100.34),
                        Currency.getInstance("RUB")
                ),
                new ResponseStatus(
                        BillStatus.PAID,
                        ZonedDateTime.now()
                ),
                "comment",
                new Customer(
                        "test@test.ru",
                        "user uid on your side",
                        "79999999999"
                ),
                ZonedDateTime.now(),
                ZonedDateTime.now(),
                "http://test.ru/",
                new CustomFields("client", "version", null),
                "79999999999"
        );

        //when
        BillResponse appendedResponse = BillPaymentClient.appendSuccessUrl(response, successUrl);
        String charset = StandardCharsets.UTF_8.name();
        String expectedUrl = response.getPayUrl() + "?successUrl=" + URLEncoder.encode(successUrl, charset);

        //then
        Assert.assertEquals(
                "Pay url should contain original url with appended success url as encoded param",
                expectedUrl,
                appendedResponse.getPayUrl()
        );
    }
}
