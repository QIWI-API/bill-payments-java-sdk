package com.qiwi.billpayments.sdk.utils;

import com.qiwi.billpayments.sdk.model.Bill;
import com.qiwi.billpayments.sdk.model.BillStatus;
import com.qiwi.billpayments.sdk.model.MoneyAmount;
import com.qiwi.billpayments.sdk.model.Notification;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Currency;

public class BillPaymentsUtilsTest {
    @Test
    public void testJoinFields() {
        //given
        Notification notification = new Notification(
                new Bill(
                        "siteId",
                        "billId",
                        new MoneyAmount(
                                BigDecimal.TEN,
                                Currency.getInstance("RUB")
                        ),
                        BillStatus.PAID
                ),
                "version"
        );
        String expectedJoinedFields = "RUB|10.00|billId|siteId|PAID";

        //when
        String joinedFields = BillPaymentsUtils.joinFields(notification);

        //then
        Assert.assertEquals(
                "Fields should be join in a given format",
                expectedJoinedFields,
                joinedFields
        );
    }

    @Test
    public void testEncrypt() {
        //given
        String key = "key";
        String data = "data";
        String expectedHash = "5031fe3d989c6d1537a013fa6e739da23463fdaec3b70137d828e36ace221bd0";

        //when
        String computedHash = BillPaymentsUtils.encrypt(key, data);

        //then
        Assert.assertEquals(
                "HMAC SHA256 algorithm should be determined",
                expectedHash,
                computedHash
        );
    }
}
