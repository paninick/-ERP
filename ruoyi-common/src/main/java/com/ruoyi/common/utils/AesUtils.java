package com.ruoyi.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * AES加解密工具类
 * 
 * @author ruoyi
 */
public class AesUtils
{
    private static final Logger log = LoggerFactory.getLogger(AesUtils.class);

    // 默认密钥 (16位) - 生产环境建议通过外部配置注入替换
    private static String secretKey = "RuoYiErpSecretKey!"; 

    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";

    public static void setSecretKey(String key) {
        if (key != null && key.length() >= 16) {
            secretKey = key.substring(0, 16);
        }
    }

    /**
     * AES 加密
     */
    public static String encrypt(String plainText) {
        if (StringUtils.isEmpty(plainText)) {
            return plainText;
        }
        try {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.substring(0, 16).getBytes(StandardCharsets.UTF_8), ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            byte[] encryptedBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            log.error("AES Encrypt Error", e);
        }
        return plainText;
    }

    /**
     * AES 解密
     */
    public static String decrypt(String encryptedText) {
        if (StringUtils.isEmpty(encryptedText)) {
            return encryptedText;
        }
        try {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.substring(0, 16).getBytes(StandardCharsets.UTF_8), ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            // 如果解密失败（比如原数据是明文），则直接返回原数据
            log.warn("AES Decrypt Warning, string may not be encrypted: {}", e.getMessage());
            return encryptedText;
        }
    }
}
