package com.example;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RSAUtil {
    private static String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAvcm08/QoRqgxxu+BPOLce/1lCMsicZbTqbTvzvi7YwhXrFNDqSN84jjehxzBKqEbW02myQ6hf5TpjSiQLC09ugwaC/VuVW1RaR7GbXiOGTTp/L869o/1IaXdaW2nrZa7mQ1nOQstNPvG7HjL+mmkLn7iWF1g5YtW0Ciexfbvuxz/eSeiGDqh6WF1lACdcm89w6mAAJK3vX0tOA/w/oDyjhbKD5j4YZOlXQbhNAr5Z8C+k+ppOA4DZlBcD69oVZGJD+BiU2/RWaE5rH3AetdeghHTikkzQ1KAlA8NvgtATkwr1KVs0VqolbLz2KGqhenAB2sKtaQ43P9FUUSsxoR5iQIDAQAB";

    private static String privateKey = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQC9ybTz9ChGqDHG74E84tx7/WUIyyJxltOptO/O+LtjCFesU0OpI3ziON6HHMEqoRtbTabJDqF/lOmNKJAsLT26DBoL9W5VbVFpHsZteI4ZNOn8vzr2j/Uhpd1pbaetlruZDWc5Cy00+8bseMv6aaQufuJYXWDli1bQKJ7F9u+7HP95J6IYOqHpYXWUAJ1ybz3DqYAAkre9fS04D/D+gPKOFsoPmPhhk6VdBuE0CvlnwL6T6mk4DgNmUFwPr2hVkYkP4GJTb9FZoTmsfcB6116CEdOKSTNDUoCUDw2+C0BOTCvUpWzRWqiVsvPYoaqF6cAHawq1pDjc/0VRRKzGhHmJAgMBAAECggEAEZHBua/JprjdWtBDQ5Kw3J2Zo9XCS4et8pD4fk2Sc1mLXD2dDuRlJEAlgZaM31howuwOW6CKHNh5QoMVzbEmfLmwE5lV7HyhQ70Am5PEe9rlKmu9lqO/Cq1jMOZg1nYUydduzd4WAkiooq2QHI95DoDajVmfpcBfege4oxT4Hnco7m5tuglHbSeV9M5hf+wRq8NNYrO5nWXJGDVPefRSK7OTg+FGu6CvQpdOw2zRqe9sTOy9xFeNMf8BbX5GINfHOobcX3AfMQq70EXzi2M2l8zQiwDw1QqzgpTtPpf2DbhhXqsBfploTf2yG0i5NJhXeCy8LnD5BEyDaubaCgWBzQKBgQDjiz9eEIBMVjrXMmK21cycQjLdWadvAYFHNvIydzQqCZHCwUtYrl/Qs1uaIQkC+L6ENcFdQJwgaTOQSFDrWDIxHp5Nre21ychVhHsvz0rQpFNkW9weAsi7NZlwvcJyRu1GZXdjND2Nc6ML1Z6Rwji8qwKyjvEUzuYrnnrmpdw3AwKBgQDVhbYhfqJunuF0oili3WGf85pz+8/NLkZPfksIhzleOe2/o4Ez+p8uRjuHbwLt66aG+uowQE2TbVgQEn1ZXy99pv2EaOUHjejOFnd0TOovz9gouPnFCgqSDB4bcGOdIz5cFgFC48DJTJhcxHxNv54bWTEuCFrObdE2FBRgx4RxgwKBgFpK6UxUU/T2ysaWMeHHmTfwdOqtJum/uSMfRKCN+N8tiO+1cEaye0b/xTu2HDNXrZIdGqv8dXfPBy0b5TBegRxIwBynBgr7FUoKFi/0nAWLD6RlZ0VpaWoQbOD2C/D5ADLNFMEC++HjnzGVBSVl4snLOnjMjBHrwB5S9wYLjwUtAoGAQ2IBCiz2gcZuC+qo+OQ4TMwJsBSFjZDO64NFlplmB1BssQ+eVJMA7Q+I9cKYwBCf6vUANt30HVRieB/9otihGhw64TBhJrMoBvGcHP2w4gnLg35shDwY/eIJFkuvhAV9Rn5DQ6KwUlZztsLjH34bHTJWjNpiHccWQOSxUaFlMJUCgYBnHLp4oXA/zw2l/QzeFcGQR0J+VF2lKyQIiHLWVZ4lpI3tFi5WjAaHP5nNbDnTwqBcnKeqwc+j4odzugTqFwDxmWvCSRuaa0K1SMUCEOdQh6InOFI5c5HW1IhRUae+c8s8T2RWy3Z+GLE3ko8A+/mqTSswlyCdKsPX3OBCjlcF7g==";


    public static void main(String[] args) throws Exception {
        String message = "{\"id\":\"1\",\"name\":\"zhang\",\"pid\":\"2\",\"pname\":\"wang\"}";;
        String messageEn = encrypt(message, publicKey);
        System.out.println("加密：" + messageEn);
        String messageDe = decrypt(messageEn, privateKey);
        System.out.println("解密:" + messageDe);

        //genKeyPair();
    }

    /**
     * 该方法 可生成公钥与私钥
     * 如果不想用在线网站生成的公钥私钥,用这个生成的也可以
     *
     * @throws NoSuchAlgorithmException
     */
    public static void genKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        // 初始化密钥对生成器，密钥大小为96-1024位
        keyPairGen.initialize(1024, new SecureRandom());
        // 生成一个密钥对，保存在keyPair中
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        // 得到私钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        // 得到公钥
        String publicKeyString = new String(Base64.encodeBase64(publicKey.getEncoded()));
        // 得到私钥字符串
        String privateKeyString = new String(Base64.encodeBase64((privateKey.getEncoded())));
        // 将公钥和私钥打印出来直接粘贴出来用即可
        System.out.println("公钥："+publicKeyString);
        System.out.println("----------------------------------------------------------------");
        System.out.println("私钥： "+privateKeyString);
    }

    /**
     * 加密
     * @param str
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static String encrypt(String str, String publicKey) throws Exception {
        // base64编码的公钥
        byte[] decoded = Base64.decodeBase64(publicKey);
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
        // RSA加密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        return Base64.encodeBase64String(cipher.doFinal(str.getBytes("UTF-8")));
    }

    /**
     * 解密
     * @param str
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static String decrypt(String str, String privateKey) throws Exception {
        // 64位解码加密后的字符串
        byte[] inputByte = Base64.decodeBase64(str.getBytes("UTF-8"));
        // base64编码的私钥
        byte[] decoded = Base64.decodeBase64(privateKey);
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
        // RSA解密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        return new String(cipher.doFinal(inputByte));
    }
}