package com.ruoyi.erp.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * AES-128 加解密工具（员工 idCard/phone 等敏感字段）
 * 密钥从环境变量 ERP_AES_KEY 获取，不硬编码
 */
public final class AesEncryptUtil {
    private static final String ALGORITHM = "AES";
    private static final String ENV_KEY = "ERP_AES_KEY";
    private static final String DEFAULT_KEY = "ErpEncryptKey26!"; // 仅开发环境使用，生产必须覆盖

    private AesEncryptUtil() {}

    private static byte[] getKey() {
        String key = System.getenv(ENV_KEY);
        if (key == null || key.length() < 16) key = DEFAULT_KEY;
        return key.substring(0, 16).getBytes(StandardCharsets.UTF_8);
    }

    public static String encrypt(String plain) {
        if (plain == null) return null;
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(getKey(), ALGORITHM));
            return Base64.getEncoder().encodeToString(cipher.doFinal(plain.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            throw new RuntimeException("AES encryption failed", e);
        }
    }

    public static String decrypt(String encrypted) {
        if (encrypted == null) return null;
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(getKey(), ALGORITHM));
            return new String(cipher.doFinal(Base64.getDecoder().decode(encrypted)), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("AES decryption failed", e);
        }
    }

    /** 身份证脱敏：前6后4，中间用*替代 */
    public static String maskIdCard(String idCard) {
        if (idCard == null || idCard.length() < 10) return idCard;
        return idCard.substring(0, 6) + "********" + idCard.substring(idCard.length() - 4);
    }

    /** 手机号脱敏：前3后4，中间用****替代 */
    public static String maskPhone(String phone) {
        if (phone == null || phone.length() < 7) return phone;
        return phone.substring(0, 3) + "****" + phone.substring(phone.length() - 4);
    }
}
