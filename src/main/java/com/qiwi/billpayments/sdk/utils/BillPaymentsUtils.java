package com.qiwi.billpayments.sdk.utils;

import com.qiwi.billpayments.sdk.exception.EncryptionException;
import com.qiwi.billpayments.sdk.model.Notification;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.TreeMap;

public class BillPaymentsUtils {
    private static final String FIELD_SEPARATOR = "|";
    private static final String SHA_256_ALGORITHM = "HmacSHA256";
    private static final Charset ENCODING = StandardCharsets.UTF_8;

    public static boolean checkNotificationSignature(
            String signature,
            Notification notification,
            String merchantSecret
    ) {
        String hash = encrypt(merchantSecret, joinFields(notification));
        return hash.equals(signature);
    }

    static String joinFields(Notification notification) {
        Map<String, String> fields = new TreeMap<String, String>() {{
            put("amount.currency", notification.getBill().getAmount().getCurrency().toString());
            put("amount.value", notification.getBill().getAmount().formatValue());
            put("billId", notification.getBill().getBillId());
            put("siteId", notification.getBill().getSiteId());
            put("status", notification.getBill().getStatus().getValue());
        }};
        return String.join(FIELD_SEPARATOR, fields.values());
    }

    static String encrypt(String key, String data) {
        try {
            SecretKeySpec secret = new SecretKeySpec(key.getBytes(ENCODING), SHA_256_ALGORITHM);
            Mac sha256Mac = Mac.getInstance(SHA_256_ALGORITHM);
            sha256Mac.init(secret);
            byte[] encoded = sha256Mac.doFinal(data.getBytes(ENCODING));
            return Hex.encodeHexString(encoded);

        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new EncryptionException(e);
        }
    }
}
