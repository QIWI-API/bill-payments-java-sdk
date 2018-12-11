package com.qiwi.billpayments.sdk.client;

import com.qiwi.billpayments.sdk.PomInfo;
import com.qiwi.billpayments.sdk.exception.UrlEncodingException;
import com.qiwi.billpayments.sdk.model.MoneyAmount;
import com.qiwi.billpayments.sdk.model.in.CreateBillInfo;
import com.qiwi.billpayments.sdk.model.in.CreateBillRequest;
import com.qiwi.billpayments.sdk.model.in.CustomFields;
import com.qiwi.billpayments.sdk.model.in.PaymentInfo;
import com.qiwi.billpayments.sdk.model.in.RefundBillRequest;
import com.qiwi.billpayments.sdk.model.out.BillResponse;
import com.qiwi.billpayments.sdk.model.out.RefundResponse;
import com.qiwi.billpayments.sdk.web.WebClient;
import org.apache.http.HttpHeaders;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class BillPaymentClient {
    private static final String AUTHORIZATION_PREFIX = "Bearer ";
    private static final String CLIENT_NAME = "java_sdk";
    private static final String BILLS_URL = "https://api.qiwi.com/partner/bill/v1/bills/";
    private static final String PAYMENT_URL = "ttps://oplata.qiwi.com/create";

    private final WebClient webClient;
    private final Map<String, String> headers;
    private final String appVersion;

    public BillPaymentClient(String secretKey, WebClient webClient) {
        this.webClient = webClient;
        this.headers = prepareHeaders(secretKey);
        this.appVersion = PomInfo.VERSION;
    }

    static Map<String, String> prepareHeaders(String bearerToken) {
        return new HashMap<String, String>() {{
            put(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString());
            put(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.toString());
            put(HttpHeaders.AUTHORIZATION, AUTHORIZATION_PREFIX + bearerToken);
        }};
    }

    public BillResponse createBill(CreateBillInfo info) throws URISyntaxException {
        BillResponse response = webClient.doRequest(
                "PUT",
                BILLS_URL + info.getBillId(),
                Optional.of(appendCustomFields(info)),
                BillResponse.class,
                headers
        );
        return appendSuccessUrl(response, info.getSuccessUrl());
    }

    static BillResponse appendSuccessUrl(BillResponse response, String successUrl) throws URISyntaxException {
        String updatedUrl = new URIBuilder(response.getPayUrl())
                .addParameter("successUrl", successUrl)
                .build()
                .toString();
        return response.withNewPayUrl(updatedUrl);
    }

    private CreateBillRequest appendCustomFields(CreateBillInfo info) {
        return CreateBillRequest.create(
                info,
                new CustomFields(CLIENT_NAME, appVersion)
        );
    }

    public BillResponse getBillInfo(String billId) {
        return webClient.doRequest(
                "GET",
                BILLS_URL + billId,
                Optional.empty(),
                BillResponse.class,
                headers
        );
    }

    public BillResponse cancelBill(String billId) {
        return webClient.doRequest(
                "POST",
                BILLS_URL + billId + "/reject",
                Optional.empty(),
                BillResponse.class,
                headers
        );
    }

    public RefundResponse refundBill(String billId, String refundId, MoneyAmount amount) {
        return webClient.doRequest(
                "PUT",
                BILLS_URL + billId + "/refunds/" + refundId,
                Optional.of(new RefundBillRequest(amount)),
                RefundResponse.class,
                headers
        );
    }

    public RefundResponse getRefundInfo(String billId, String refundId) {
        return webClient.doRequest(
                "GET",
                BILLS_URL + billId + "/refunds/" + refundId,
                Optional.empty(),
                RefundResponse.class,
                headers
        );
    }

    public String createPaymentForm(PaymentInfo paymentInfo) {
        try {
            return new URIBuilder(PAYMENT_URL)
                    .addParameter("amount", paymentInfo.getAmount().formatValue())
                    .addParameter("customFields[apiClient]", CLIENT_NAME)
                    .addParameter("customFields[apiClientVersion]", appVersion)
                    .addParameter("publicKey", paymentInfo.getPublicKey())
                    .addParameter("billId", paymentInfo.getBillId())
                    .addParameter("successUrl", paymentInfo.getSuccessUrl())
                    .build()
                    .toString();

        } catch (URISyntaxException e) {
            throw new UrlEncodingException(e);
        }
    }

    public String getAppVersion() {
        return appVersion;
    }
}
