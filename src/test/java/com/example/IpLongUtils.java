package com.example;

import java.math.BigInteger;

public class IpLongUtils {
    /**
     * 把字符串IP转换成long
     *
     * @param ipStr 字符串IP
     * @return IP对应的long值
     */
    public static long ip2Long(String ipStr) {
        String[] ip = ipStr.split("\\.");
        return (Long.valueOf(ip[0]) << 24) + (Long.valueOf(ip[1]) << 16)
                + (Long.valueOf(ip[2]) << 8) + Long.valueOf(ip[3]);
    }

    /**
     * 把IP的long值转换成字符串
     *
     * @param ipLong IP的long值
     * @return long值对应的字符串
     */
    public static String long2Ip(long ipLong) {
        StringBuilder ip = new StringBuilder();
        ip.append(ipLong >>> 24).append(".");
        ip.append((ipLong >>> 16) & 0xFF).append(".");
        ip.append((ipLong >>> 8) & 0xFF).append(".");
        ip.append(ipLong & 0xFF);
        return ip.toString();
    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static long ipToInteger(String ipAddress) {
        String[] ipSegments = ipAddress.split("\\.");
        long result = 0;
        for (int i = 0; i < ipSegments.length; i++) {
            int ipSegment = Integer.parseInt(ipSegments[i]);
            result = (result << 8) | ipSegment;
        }
        return result;
    }
    public static String integerToIP(long integerValue) {
        StringBuilder ipBuilder = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            int segment = (int) (integerValue & 0xff);
            if (i > 0) {
                ipBuilder.insert(0, ".");
            }
            ipBuilder.insert(0, segment);
            integerValue >>= 8;
        }
        return ipBuilder.toString();
    }
    public static BigInteger ipv6ToInteger(String ipv6Address) {
        // Remove any square brackets if present
        ipv6Address = ipv6Address.replaceAll("\\[|\\]", "");
        String[] segments = ipv6Address.split(":");
        BigInteger result = BigInteger.ZERO;
        for (String segment : segments) {
            BigInteger segmentValue = new BigInteger(segment, 16);
            result = result.shiftLeft(16).add(segmentValue);
        }
        return result;
    }
    public static String integerToIPv6(BigInteger integerValue) {
        StringBuilder ipv6Builder = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            BigInteger segmentValue = integerValue.and(BigInteger.valueOf(0xffff));
            if (ipv6Builder.length() > 0) {
                ipv6Builder.insert(0, ":");
            }
            ipv6Builder.insert(0, String.format("%04x", segmentValue));
            integerValue = integerValue.shiftRight(16);
        }
        return ipv6Builder.toString();
    }

    public static void main(String[] args) {
        /*System.out.println(ip2Long("192.168.0.1"));
        System.out.println(long2Ip(3232235521L));
        System.out.println(ip2Long("10.0.0.1"));*/
        /*String ip = "192.168.0.1";
        long integerValue = ipToInteger(ip);
        System.out.println(integerValue);*/
        String ipv6 = "ffff:ffff:ffff:ffff:ffff:ffff:ffff:ffff";
        BigInteger integerValue = ipv6ToInteger(ipv6);
        System.out.println(integerValue);
        //9223372036854775807
        //42540766452641154071740215577757643572
        //340282366920938463463374607431768211455
    }

}
