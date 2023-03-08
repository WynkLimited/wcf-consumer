package com.wynk.consumerservice.utils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.SignatureException;

public class EncryptUtils {
    /**
     * Computes RFC 2104-compliant HMAC signature.
     *
     * @param data
     *            The data to be signed.
     * @param secretKey
     *            The signing key.
     * @return The Base64-encoded RFC 2104-compliant HMAC signature.
     * @throws java.security.SignatureException
     *             when signature generation fails
     */
    public static String calculateRFC2104HMAC(String data, String secretKey) throws java.security.SignatureException {
        String result;
        try {
            Mac mac = Mac.getInstance("HmacSHA1");
            SecretKeySpec key = new SecretKeySpec(secretKey.getBytes(), "HmacSHA1");
            mac.init(key);
            byte[] authentication = mac.doFinal(data.getBytes());
            result = new String(org.apache.commons.codec.binary.Base64.encodeBase64(authentication));

        } catch (Exception e) {
            throw new SignatureException("Failed to generate HMAC : " + e.getMessage());
        }
        return result;
    }
}
