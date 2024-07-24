package com.example.utils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.ArrayUtils;

public class ADX_price {
    private static final String MAC_NAME = "HmacSHA1";

    public static void main(String[] args) {
        try {
            System.out.println(decodePrice("a9033eebd57f5561d64d8e58eb354604", "bcd92b70e3ba0c5959f4d2ac6b1b5696",
                    "YWRjZmU0Y2ZjNjljMzdkMFcCREVGQRcRNDUyZQ=="));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param e_key 价格加密密钥
     * @param i_key 价格校验密钥
     * @param e_enc 待解密
     * @return
     * @throws Exception
     */
    private static double decodePrice(String e_key, String i_key, String e_enc) throws Exception {
        if ("".equals(e_key) || "".equals(i_key) || "".equals(e_enc)) {
            return 0;
        }

        byte[] e_src = Base64.decodeBase64(e_enc); // base64解码

        byte[] iv = ArrayUtils.subarray(e_src, 0, 16); // 初始化矢量数组

        byte[] en_price = ArrayUtils.subarray(e_src, 16, 24); // 加密的价格数组

        byte[] sig = ArrayUtils.subarray(e_src, 24, 28); // 完整性签名的前4位

        byte[] pad = ArrayUtils.subarray(getSignature(new String(iv), e_key).getBytes(), 0, 8); // 加密得到加密数组

        byte[] price = xor_bytes(en_price, pad, 8); // 通过异或运算得到价格数组

        byte[] merge = byteMerger(price, iv); // 合并price与iv数组

        byte[] conf_sig = ArrayUtils.subarray(getSignature(new String(merge), i_key).getBytes(), 0, 4);

        boolean flag = ArrayUtils.isEquals(sig, conf_sig); // 比较两个签名数组是否相同

        if (!flag) {
            throw new Exception("signature is illegal"); // 返回签名非法的错误
        } else {
            return Double.parseDouble(new String(price));
        }
    }

    private static byte[] xor_bytes(byte[] bytes1, byte[] bytes2, int length) {
        byte[] result = new byte[length];
        for (int i = 0; i < length; i++) {
            result[i] = (byte) (bytes1[i] ^ bytes2[i]);
        }
        return result;
    }

    // System.arraycopy()方法
    public static byte[] byteMerger(byte[] bt1, byte[] bt2) {
        if (bt1 == null || bt2 == null) {
            return null;
        }
        byte[] bt3 = new byte[bt1.length + bt2.length];
        System.arraycopy(bt1, 0, bt3, 0, bt1.length);
        System.arraycopy(bt2, 0, bt3, bt1.length, bt2.length);
        return bt3;
    }

    public static String getSignature(String data, String key) throws Exception {
        if ("".equals(data) || "".equals(key)) {
            return "";
        }
        byte[] keyBytes = key.getBytes();
        // 根据给定的字节数组构造一个密钥。
        SecretKeySpec signingKey = new SecretKeySpec(keyBytes, MAC_NAME);
        Mac mac = Mac.getInstance(MAC_NAME);
        mac.init(signingKey);

        byte[] rawHmac = mac.doFinal(data.getBytes());

        String hexBytes = byte2hex(rawHmac);
        return hexBytes;
    }

    private static String byte2hex(final byte[] b) {
        if (b == null) {
            return "";
        }
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            // 以十六进制（基数 16）无符号整数形式返回一个整数参数的字符串表示形式。
            stmp = (java.lang.Integer.toHexString(b[n] & 0xFF));
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }
        return hs;
    }
}
