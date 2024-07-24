package com.example.utils.uniad;

import lombok.Data;
import org.springframework.util.DigestUtils;

import java.util.Map;

@Data
public class CommonParams {
    private String channel_id;
    private long time;
    private String sign;



    // Getters and setters

    // Method to generate sign based on given parameters
    public static String generateSign(Map<String, Object> params, String secret) {
        StringBuilder sb = new StringBuilder();
        params.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> sb.append(entry.getKey()).append("=").append(entry.getValue()));
        sb.append("&secret=").append(secret);
        return md5ToLower(sb.toString());
    }

    // Utility method for MD5 conversion to lowercase
    private static String md5ToLower(String input) {
        //md5
        String md5 = DigestUtils.md5DigestAsHex(input.getBytes());

        return md5.toLowerCase(); // Return hashed and lowercased string
    }
}
