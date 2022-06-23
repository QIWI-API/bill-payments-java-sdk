package com.qiwi.billpayments.sdk.client;

import org.junit.Assert;
import org.junit.Test;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qiwi.billpayments.sdk.json.ObjectMapperFactory;
import com.qiwi.billpayments.sdk.model.out.BillResponse;

public class RequestMappingIntercessorTest {
    @Test
    public void testDeserializeResponseBody() throws JsonProcessingException {
        //given
        ObjectMapper mapper = ObjectMapperFactory.create();
        String extendedBillResponseBody = "{\n"
                + "  \"siteId\": \"opeqvd-00\",\n"
                + "  \"billId\": \"6c2b25e2-f2cc-11ec-a498-00155d15bac7\",\n"
                + "  \"amount\": {\n"
                + "    \"currency\": \"RUB\",\n"
                + "    \"value\": \"1.00\"\n"
                + "  },\n"
                + "  \"status\": {\n"
                + "    \"value\": \"WAITING\",\n"
                + "    \"changedDateTime\": \"2022-06-23T11:13:54.854+03:00\"\n"
                + "  },\n"
                + "  \"customer\": {\n"
                + "    \"email\": \"test@qiwi.com\",\n"
                + "    \"account\": \"accountId\",\n"
                + "    \"phone\": \"79876543210\"\n"
                + "  },\n"
                + "  \"customFields\": {\n"
                + "    \"apiClient\": \"java_sdk\",\n"
                + "    \"apiClientVersion\": \"1.5.0\"\n"
                + "  },\n"
                + "  \"comment\": \"comment\",\n"
                + "  \"creationDateTime\": \"2022-06-23T11:13:54.854+03:00\",\n"
                + "  \"expirationDateTime\": \"2022-06-24T11:13:54.369+03:00\",\n"
                + "  \"payUrl\": \"https://some.url.com\",\n"
                + "  \"recipientPhoneNumber\": \"79876543210\",\n"
                + "  \"field\": \"shouldBeIgnored\"\n"
                + "}";

        //expected: "no exception thrown"
        BillResponse response = mapper.readValue(extendedBillResponseBody, BillResponse.class);
        Assert.assertNotNull(response);
    }
}