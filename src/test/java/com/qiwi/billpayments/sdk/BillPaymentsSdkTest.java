package com.qiwi.billpayments.sdk;

import com.qiwi.billpayments.sdk.client.BillPaymentClient;
import com.qiwi.billpayments.sdk.client.BillPaymentClientFactory;
import com.qiwi.billpayments.sdk.config.PropertiesTestConfiguration;
import com.qiwi.billpayments.sdk.config.TestConfiguration;
import com.qiwi.billpayments.sdk.model.Bill;
import com.qiwi.billpayments.sdk.model.BillStatus;
import com.qiwi.billpayments.sdk.model.MoneyAmount;
import com.qiwi.billpayments.sdk.model.Notification;
import com.qiwi.billpayments.sdk.model.in.CreateBillInfo;
import com.qiwi.billpayments.sdk.model.in.Customer;
import com.qiwi.billpayments.sdk.model.in.PaymentInfo;
import com.qiwi.billpayments.sdk.model.out.BillResponse;
import com.qiwi.billpayments.sdk.model.out.RefundResponse;
import com.qiwi.billpayments.sdk.model.out.RefundStatus;
import com.qiwi.billpayments.sdk.utils.BillPaymentsUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Currency;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class BillPaymentsSdkTest {
    private final TestConfiguration configuration = new PropertiesTestConfiguration();
    private final BillPaymentClient client = BillPaymentClientFactory.createDefault(configuration.getSecretKey());

    @Before
    public void checkConfigs() {
        Assume.assumeThat(
                "If there is no keys defined, we skip integration tests",
                Arrays.asList(
                        configuration.getBillIdForRefund(),
                        configuration.getPublicKey(),
                        configuration.getSecretKey()
                ),
                CoreMatchers.everyItem(CoreMatchers.notNullValue())
        );
    }

    @Test
    public void testCheckValidNotificationSignature() {
        //given
        String merchantSecret = "test-merchant-secret-for-signature-check";
        Notification notification = new Notification(
                new Bill(
                        "test",
                        "test_bill",
                        new MoneyAmount(
                                BigDecimal.ONE,
                                Currency.getInstance("RUB")
                        ),
                        BillStatus.PAID
                ),
                "3"
        );
        String validSignature = "07e0ebb10916d97760c196034105d010607a6c6b7d72bfa1c3451448ac484a3b";
        String wrongSignature = "foo";

        //when
        boolean validSignatureIsOk = BillPaymentsUtils.checkNotificationSignature(
                validSignature,
                notification,
                merchantSecret
        );

        //then
        Assert.assertTrue(
                "should return true on valid signature",
                validSignatureIsOk
        );

        //when
        boolean wrongSignatureIsOk = BillPaymentsUtils.checkNotificationSignature(
                wrongSignature,
                notification,
                merchantSecret
        );

        //then
        Assert.assertFalse(
                "should return false on wrong signature",
                wrongSignatureIsOk
        );
    }

    @Test
    public void testCreateAndCancelBill() throws Exception {
        //given
        CreateBillInfo billInfo = newBillInfo();

        //when
        BillResponse response = client.createBill(billInfo);

        //then
        Assert.assertEquals(
                "response should have same id",
                billInfo.getBillId(),
                response.getBillId()
        );
        Assert.assertEquals(
                "initial status should be waiting",
                BillStatus.WAITING,
                response.getStatus().getValue()
        );

        //when
        boolean successUrlPartFound = new URIBuilder(response.getPayUrl())
                .getQueryParams()
                .contains(new BasicNameValuePair("successUrl", billInfo.getSuccessUrl()));

        //then
        Assert.assertTrue(
                "response should contain passed url",
                successUrlPartFound
        );

        //when
        BillResponse requestedBillInfo = client.getBillInfo(billInfo.getBillId());

        //then
        Assert.assertEquals(
                "get response should have same id",
                billInfo.getBillId(),
                requestedBillInfo.getBillId()
        );

        //when
        BillResponse cancelledBillInfo = client.cancelBill(billInfo.getBillId());

        //then
        Assert.assertEquals(
                "cancel response should have same id",
                billInfo.getBillId(),
                cancelledBillInfo.getBillId()
        );
        Assert.assertEquals(
                "status should be change to rejected",
                BillStatus.REJECTED,
                cancelledBillInfo.getStatus().getValue()
        );
    }

    @Test
    public void testBillIdForRefundIsPaid() {
        //given
        String paidBillId = configuration.getBillIdForRefund();

        //when
        BillResponse requestedBillInfo = client.getBillInfo(paidBillId);

        //then
        Assert.assertEquals(
                "bill should be paid before refund",
                BillStatus.PAID,
                requestedBillInfo.getStatus().getValue()
        );
    }

    @Test
    public void testRefundBill() throws Exception {
        //given
        String paidBillId = configuration.getBillIdForRefund();
        String refundId = "e5ac6a59-2d69-48f1-b338-78d50bd718c6";
        MoneyAmount amount = new MoneyAmount(
                BigDecimal.valueOf(10.00),
                Currency.getInstance("RUB")
        );

        //when
        RefundResponse refundResponse = client.refundBill(paidBillId, refundId, amount);

        //then
        Assert.assertEquals(
                "response should have same id",
                refundResponse.getRefundId(),
                refundId
        );
        Assert.assertEquals(
                "bill should not be completely refund",
                RefundStatus.PARTIAL,
                refundResponse.getStatus()
        );
        Assert.assertEquals(
                "response should have same refund amount",
                amount.formatValue(),
                refundResponse.getAmount().formatValue()
        );

        //when
        RefundResponse requestedRefundResponse = client.getRefundInfo(paidBillId, refundId);

        //then
        Assert.assertEquals(
                "get refund response should have same id",
                refundResponse.getRefundId(),
                requestedRefundResponse.getRefundId()
        );
        Assert.assertEquals(
                "get refund response should have same status",
                refundResponse.getStatus(),
                requestedRefundResponse.getStatus()
        );
    }

    private CreateBillInfo newBillInfo() {
        return new CreateBillInfo(
                UUID.randomUUID().toString(),
                new MoneyAmount(
                        BigDecimal.valueOf(100.34),
                        Currency.getInstance("RUB")
                ),
                "test",
                ZonedDateTime.now().plusDays(45),
                new Customer(
                        "test@test.ru",
                        "user uid on your side",
                        "79999999999"
                ),
                "http://test.ru/"
        );
    }

    @Test
    public void testCreatePaymentForm() throws Exception {
        //given
        String key = configuration.getPublicKey();
        MoneyAmount amount = new MoneyAmount(
                BigDecimal.valueOf(499.90),
                Currency.getInstance("RUB")
        );
        String billId = UUID.randomUUID().toString();
        String successUrl = "http://test.ru/";

        //when
        String paymentUrl = client.createPaymentForm(new PaymentInfo(key, amount, billId, successUrl));
        Map<String, String> params = new URIBuilder(paymentUrl)
                .getQueryParams()
                .stream()
                .collect(Collectors.toMap(
                        NameValuePair::getName,
                        NameValuePair::getValue
                ));

        //then
        Assert.assertEquals(
                "url contains passed amount",
                amount.formatValue(),
                params.get("amount")
        );
        Assert.assertEquals(
                "url contains passed url",
                successUrl,
                params.get("successUrl")
        );
        Assert.assertEquals(
                "url contains passed billId",
                billId,
                params.get("billId")
        );
        Assert.assertEquals(
                "url contains passed public key",
                key,
                params.get("publicKey")
        );
        Assert.assertNotNull(
                "url contains passed client name",
                params.get("customFields[apiClient]")
        );
        Assert.assertNotNull(
                "url contains passed client version",
                params.get("customFields[apiClientVersion]")
        );
    }

    @Test
    public void testAppVersion() {
        //given
        Pattern versionRegex = Pattern.compile("\\d+.\\d+.\\d+");
        String appVersion = client.getAppVersion();

        //when
        boolean isVersionCorrect = versionRegex.matcher(appVersion).find();

        //then
        Assert.assertTrue(
                "app version should be retrieved from pom.xml and have correct format",
                isVersionCorrect
        );
    }
}
