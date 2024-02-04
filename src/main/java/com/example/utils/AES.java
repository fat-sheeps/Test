package com.example.utils;

import com.google.common.io.BaseEncoding;
import com.google.protobuf.ByteString;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

/**
 * Simple implementation of AES protocol
 * - "AES"
 * - No KeyGenerator
 * - No IV 
 * - "ECB" instead of "CBC"
 */
public class AES {

    private static final String ALGORITHM = "AES";

    private static final String ALGORITHM_STR = "AES/ECB/PKCS5Padding";

    private static final String ALGORITHM_STR_NO_PADDING = "AES/ECB/NoPadding";

    private static final String ALGORITHM_STR_CBC_PADDING = "AES/CBC/PKCS5Padding";

    /**
     * 加密
     * @param s 需要加密的字符串
     * @param token 加密token，16进制的hex格式，必须是32长度的字符串。如果token不合法，返回null
     * @return 加密后的字符串
     */
    public static String encrypt(String s, String token) {
        try {
            byte[] key = Hex.toBytes(token);
            return encryptBase64URLSafe(s, key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 解密
     * @param encrypted 已加密的字符串
     * @param token 加密token，16进制的hex格式，必须是32长度的字符串。如果token不合法，返回空串
     * @return 解密后的字符串
     */
    public static String decrypt(String encrypted, String token){
        try {
            byte[] key = Hex.toBytes(token);
            return decryptBase64URLSafe(encrypted, key);
        } catch (Exception e) {
//            e.printStackTrace();
            return null;
        }
    }

    private static byte[] encryptBytes(String s, byte[] key) {
        if (key == null) {
            return null;
        }
        // 判断Key是否为16位
        if (key.length != 16) {
            return null;
        }
        SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);

            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            return cipher.doFinal(s.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            return null;
        }
    }

    private static String decryptBytes(byte[] encrypted, byte[] key) {
        try {
            // 判断Key是否正确
            if (key == null) {
                return null;
            }
            // 判断Key是否为16位
            if (key.length != 16) {
                return null;
            }
            SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            try {
                byte[] original = cipher.doFinal(encrypted);
                return new String(original, StandardCharsets.UTF_8);
            } catch (Exception e) {
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    private static String encryptBase64URLSafe(String s, byte[] key) {
        BaseEncoding b64 = BaseEncoding.base64Url().omitPadding();
        byte[] encryptedBytes = encryptBytes(s, key);
        return b64.encode(encryptedBytes);
    }

    private static String decryptBase64URLSafe(String encrypted, byte[] key) {
        BaseEncoding b64 = BaseEncoding.base64Url().omitPadding();
        byte[] encryptedBytes = b64.decode(encrypted);
        String decrypted = decryptBytes(encryptedBytes,key);
        return decrypted;
    }

    public static String decryptIflytecPrice(String encrypted, String key) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM_STR);
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            byte[] plaintext = cipher.doFinal(Base64.decodeBase64(encrypted));
            return new String(plaintext, StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException | InvalidKeyException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String decryptOppoPrice(String encrypted, String key) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM_STR);
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            byte[] plaintext = cipher.doFinal(Hex.toBytes(encrypted));
            return new String(plaintext, StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException | InvalidKeyException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String decryptTencentPrice(String encrypted, String key) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM_STR_NO_PADDING);
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            byte[] plaintext = cipher.doFinal(Base64.decodeBase64(encrypted));
            return new String(plaintext, StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException | InvalidKeyException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String decryptMgtvPrice(String encrypted, String key) {
        try {
            SecretKeySpec keySpec =new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM_STR_CBC_PADDING);
            //偏移量
            IvParameterSpec zeroIv = new IvParameterSpec(key.getBytes(StandardCharsets.UTF_8));
            cipher.init(Cipher.DECRYPT_MODE, keySpec, zeroIv);
            byte[] plaintext = new byte[0];
            plaintext = cipher.doFinal(Hex.toBytes(encrypted));
            return new String(plaintext, StandardCharsets.UTF_8).split("\\|")[0];
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 采用aes 256位ECB模式，填充方式PKCS5，之后再进行URL安全 BASE64编码后替换的
     * @param encrypted
     * @param key
     * @return
     */
    public static String decryptMeishuPrice(String encrypted, String key) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM_STR);
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            byte[] plaintext = cipher.doFinal(Base64.decodeBase64(encrypted));
            return new String(plaintext, StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException | InvalidKeyException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static String decryptBytes3(byte[] encrypted, byte[] key) {
        try {
            // 判断Key是否正确
            if (key == null) {
                return null;
            }
            SecretKeySpec skeySpec = new SecretKeySpec(key, ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM_STR);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            try {
                byte[] original = cipher.doFinal(encrypted);
                return new String(original, StandardCharsets.UTF_8);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * 爱点击价格解密，加密算法 aes 256位的ecb模式，padding方式为 PKCS5
     *
     * @param encrypted 价格加密字符串
     * @param key 秘钥
     * @return
     */
//    public static String decryptIclickPrice(String encrypted, String key) {
//        try {
//            //base64编码字典填充
//            encrypted = encrypted.replace("-", "/").replace("_", "+");
//            byte[] priceDecodeBase64 = Base64.decodeBase64(encrypted.getBytes(StandardCharsets.UTF_8));
//            IClickSettlementProto.Settlement settlement = IClickSettlementProto.Settlement.parseFrom(priceDecodeBase64);
//            ByteString encodedPrice = settlement.getPrice();
//            return decryptBytes3(encodedPrice.toByteArray(), key.getBytes());
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

    public static String encryptBase64(String encrypted, String key) {
        try {
            final Key dataKey = new SecretKeySpec(Base64.decodeBase64(key), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM_STR);
            cipher.init(Cipher.ENCRYPT_MODE, dataKey);
            byte[] encryptData = cipher.doFinal(encrypted.getBytes(StandardCharsets.UTF_8));
            return Base64.encodeBase64String(encryptData).replaceAll("\r", "").replaceAll("\n", "");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String encryptByAESKey(String encrypted, String key) {
        try {
            final Key dataKey = new SecretKeySpec(Base64.decodeBase64(key), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM_STR);
            cipher.init(Cipher.ENCRYPT_MODE, dataKey);
            byte[] encryptData = cipher.doFinal(encrypted.getBytes(StandardCharsets.UTF_8));
            return Base64.encodeBase64String(encryptData);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
