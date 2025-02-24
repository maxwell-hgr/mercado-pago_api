package com.maxwellhgr.mercado_pago_api.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

@Component
public class SignatureValidator {

    @Value("${mercado.pago.signature}")
    private String SECRET_KEY;

    public boolean verifySignature(String xSignature, String xRequestId, String id) {
        try {
            String[] signatureParts = xSignature.split(",");
            String ts = extractParam(signatureParts[0]);
            String hash = extractParam(signatureParts[1]);

            String manifest = "id:" + id + ";request-id:" + xRequestId + ";ts:" + ts + ";";

            System.out.println(manifest);

            String generatedHash = generateHMAC(SECRET_KEY, manifest);

            System.out.println("Hash:" + hash);
            System.out.println("Generated hash:" + generatedHash);

            boolean isValid = generatedHash.equals(hash);

            System.out.println(isValid);
            System.out.println(isValid ? "HMAC verification passed" : "HMAC verification failed");

            return isValid;
        } catch (Exception e) {
            System.out.println("Error validating HMAC: " + e.getMessage());
            return false;
        }
    }

    private static String generateHMAC(String secretKey, String data) throws Exception {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        sha256_HMAC.init(secret_key);
        byte[] hash = sha256_HMAC.doFinal(data.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(hash);
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    private String extractParam(String param) {
        String[] keyValue = param.split("=");
        return keyValue.length > 1 ? keyValue[1] : null;
    }
}
