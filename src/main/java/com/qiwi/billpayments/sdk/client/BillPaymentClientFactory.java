package com.qiwi.billpayments.sdk.client;

import com.qiwi.billpayments.sdk.web.ApacheWebClient;
import com.qiwi.billpayments.sdk.web.WebClient;
import org.apache.http.impl.client.HttpClients;

public class BillPaymentClientFactory {
    public static BillPaymentClient createDefault(String secretKey) {
        return new BillPaymentClient(
                secretKey,
                new ApacheWebClient(HttpClients.createDefault())
        );
    }

    public static BillPaymentClient createCustom(String secretKey, WebClient webClient) {
        return new BillPaymentClient(secretKey, webClient);
    }
}
