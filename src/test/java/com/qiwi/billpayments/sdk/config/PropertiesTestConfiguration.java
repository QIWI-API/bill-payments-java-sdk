package com.qiwi.billpayments.sdk.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesTestConfiguration implements TestConfiguration {
    private final Properties properties = new Properties();

    public PropertiesTestConfiguration() {
        try {
            InputStream propertiesFile = getClass().getClassLoader().getResourceAsStream("test.properties");
            properties.load(propertiesFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getSecretKey() {
        return properties.getProperty("qiwi.client.test.secret.key");
    }

    @Override
    public String getPublicKey() {
        return properties.getProperty("qiwi.client.test.public.key");
    }

    @Override
    public String getBillIdForRefund() {
        return properties.getProperty("qiwi.client.test.bill-id-for-refund");
    }
}
