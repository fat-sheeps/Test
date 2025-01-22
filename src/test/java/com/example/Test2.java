package com.example;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.BCrypt;
import cn.hutool.crypto.digest.MD5;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.example.domain.DspProtoSurge;
import com.example.domain.Student;
import com.example.domain.SubUser;
import com.example.domain.User;
import com.example.utils.OKHttpUtil;
import com.example.utils.UrlUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.commons.codec.Encoder;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.Test;
import org.springframework.web.util.HtmlUtils;
import sun.security.util.Password;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.security.KeyPair;
import java.security.KeyPairGenerator;

@Slf4j
public class Test2 {

    @Test
    public void test01() {
        log.info("1");
        log.warn("2");
        log.error("3");
    }

    @Test
    public void test02() {
        Integer a = Integer.valueOf("188");
        System.out.println((double) 4055 / 100);
    }

    @Test
    public void test03() throws InvalidProtocolBufferException {
        DspProtoSurge.BidRequest.Builder bidRequest = DspProtoSurge.BidRequest.newBuilder();
        bidRequest.setId("123");
        bidRequest.setApiVersion("1.0");
        System.out.println(JsonFormat.printer().print(bidRequest));
        System.out.println(Thread.currentThread().getName());
        System.out.println(Thread.currentThread().getName());
        System.out.println(Thread.currentThread().getId());
        System.out.println(Thread.currentThread().getId());

    }

    @Test
    public void test04() {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        List<String> list2 = new ArrayList<>();
        list2.add("1");
        list2.add("2");
        list2.add("3");
        list2.add("4");
        list2.add("5");
        List<String> list3 = new ArrayList<>();
        list3.add("1");
        list3.add("2");
        list3.add("3");
        list3.add("4");
        Map<String, List<String>> map = new HashMap<>();
        Map<String, List<String>> map2 = new HashMap<>();
        map.put("1", list);
        map.put("2", list2);
        map.put("3", list3);
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            List<String> temps = new ArrayList<>(entry.getValue());
            temps.removeIf(s -> s.equals("1"));
            if (temps.size() > 3) {
                map2.put(entry.getKey(), entry.getValue());
            }
        }
        System.out.println(map2);
    }

    @Test
    public void test05() {
        java.sql.Timestamp timestamp = new java.sql.Timestamp(System.currentTimeMillis());
        java.sql.Timestamp timestamp1 = new java.sql.Timestamp(System.currentTimeMillis() + 1);
        //比较大小
        System.out.println(timestamp.before(timestamp1));
        System.out.println(timestamp1.before(timestamp));
        //格式化
        System.out.println(JSON.toJSONString(timestamp1.getHours()));

    }

    @Test
    public void test06() throws IOException {
        File file = new File("C:\\Users\\Administrator\\Desktop\\1.txt");
        InputStream is = null;
        try {
            is = java.nio.file.Files.newInputStream(file.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        byte bs[] = new byte[1024];
        is.read(bs);
        System.out.println(Arrays.toString(bs));


    }

    @Test
    public void test07() {
        String s = "108_fghj";
        System.out.println(s.substring(0, 3));
        System.out.println(s);
    }

    @Test
    public void test08() {
        String url = "http%3A%2F%2Fstatic.yximgs.com%2Fudata%2Fpkg%2Fsmart_1d4e74fa0ca643e1a6d20c427b5f04ae.jpg";
        System.out.println(UrlUtil.urlDecode(url));
    }

    @Test
    public void test09() {
        System.out.println(1234.34 / 100);
    }

    @Test
    public void test10() {
        //passwordBCrypt 等效于springframework中的passwordEncoder
        System.out.println(BCrypt.hashpw("wayio!@#", BCrypt.gensalt(10)));
        System.out.println(BCrypt.hashpw("123456", BCrypt.gensalt(10)));
        System.out.println(BCrypt.hashpw("123456", BCrypt.gensalt(10)));
        System.out.println(BCrypt.checkpw("123456", "$2a$10$0Ey1/OlxXOIdskH5OAmMEeHGkwk4PX3yVZfB3iKQhmaXxP2ZY34Zu"));
        System.out.println(BCrypt.checkpw("123456", "$2a$10$pq57s90cc/HKR4N5s374X.h8zpWv/UP0KgqIXx5TDC9jcjJUKRaAC"));
        System.out.println(BCrypt.checkpw("wayio!@#", "$2a$10$P3VQkycM/uHv5NgR.SZYX.WVuspjaq3DEru0PLacOUNoJZg.QZBA."));
    }

    @Data
    public static class User {

        private Long id;

        private String name;

        private int count;

        private double price;
    }

    @Test
    public void test11() {
        User value = new User();
        User user = new User();
        user.setCount(0);
        user.setPrice(5);

        List<User> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(user);
        }


        for (User u : list) {
            value.setCount(value.getCount() + 1);
            value.setPrice(divisionDouble(value.getPrice() * (value.getCount() - 1) + u.getPrice(), value.getCount(), 2));
//            value.setPrice( divide(value.getPrice()  + u.getPrice() , value.getCount(), 2));
            System.out.println(value);
        }

    }

    public static double divisionDouble(double dividend, double divisor, int scale) {
        if (divisor == 0) {
            return dividend;
        }
        BigDecimal bdDividend = new BigDecimal(dividend);
        BigDecimal bdDivisor = new BigDecimal(divisor);
        BigDecimal result = bdDividend.divide(bdDivisor, scale, RoundingMode.HALF_UP);
        return result.doubleValue();
    }

    @Test
    public void test12() {
        List<Double> doubles = Arrays.asList(1.1, 2.3, 3.5, 4.7, 5.9, 6.1, 7.3, 8.5, 9.7, 10.9, 11.99);
        // 求和
        System.out.println(doubles.stream().mapToDouble(Double::doubleValue).sum());
        //求平均值
        System.out.println(doubles.stream().mapToDouble(Double::doubleValue).average().getAsDouble());

        double result = 0;
        for (int i = 0; i < doubles.size(); i++) {
            result = (result * (i) + doubles.get(i)) / (i + 1);
        }
        System.out.println(result);
    }

    @Test
    public void test13() {
        System.out.println(BCrypt.gensalt());
        System.out.println(BCrypt.gensalt());
        System.out.println(BCrypt.gensalt());
        System.out.println(BCrypt.gensalt(20));
        System.out.println(BCrypt.gensalt(20));
        System.out.println(BCrypt.gensalt(20));
    }

    @Data
    public static class BidRequest {
//        private String requestId;
//        private String tagId;
//        private Double bidFloor;

        //        private String requestid;
//        private String tagid;
//        private Double bidfloor;
        private String request_id;
        private String tag_id;
        private Double bid_floor;
    }

    @Test
    public void test14() throws JsonProcessingException {
        String str = "{\"requestId\":\"123\",\"tagId\":\"456\",\"bidFloor\":7.89}";
        BidRequest bidRequest = JSON.parseObject(str, BidRequest.class);
        System.out.println("fastjson:" + bidRequest);

        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false);
        BidRequest bidRequest2 = mapper.readValue(str, BidRequest.class);
        System.out.println("jackson:" + bidRequest2);

        Gson gson = new Gson();
        BidRequest bidRequest3 = gson.fromJson(str, BidRequest.class);
        System.out.println("gson:" + bidRequest3);
    }

    @Test
    public void test15() {
        ByteString byteString = ByteString.copyFromUtf8("1234567890");
        System.out.println(byteString.toString());
    }

    @Test
    public void test16() {
        Set<String> set = new HashSet<>();
        set.add("A");
        set.add("B");
        set.add("C");
        set.add("A");
        System.out.println(set);
    }

    @Test
    public void test17() {
//        System.out.println("Android_11".toLowerCase().contains("android"));
        System.out.println(Arrays.toString("Android_|11".split("\\|")));
        System.out.println(Arrays.toString(StringUtils.split("Android_|11", "|")));
    }

    @Test
    public void test18() {
        System.out.println(Long.MAX_VALUE);
        System.out.println(new BigInteger("-154293327275284121"));
        System.out.println(new BigInteger("18292450746434267495"));
    }

    @Test
    public void test19() {
        //9223372036854775807
        //9069078709579491688
        String value = "18426434983208820283";
        // 如果value的值超出Long的最大值，则将value转为溢出后的负值
//        System.out.println(new BigInteger(value).longValue());

        long n = -20309090500731333L - Long.MAX_VALUE;
        // 输出原始的大整数
        System.out.println(n);
        System.out.println(new BigInteger(String.valueOf(Long.MAX_VALUE)).add(new BigInteger(String.valueOf(n))));
        System.out.println(value);


        System.out.println(new BigInteger(String.valueOf(Long.MAX_VALUE)).multiply(new BigInteger(String.valueOf(Long.MAX_VALUE))).multiply(new BigInteger(String.valueOf(Long.MAX_VALUE))));
//        System.out.println(new BigInteger("18426434983208820283").subtract(new BigInteger(String.valueOf(Long.MAX_VALUE))));
    }

    @Test
    public void test20() throws UnsupportedEncodingException {
        System.out.println(URLEncoder.encode("http://www.baidu.com?price=${ACTION_PRICE}", String.valueOf(StandardCharsets.UTF_8)));
    }

    @Test
    public void test21() {
        System.out.println(Integer.MAX_VALUE);
        System.out.println("3020231617");
    }

    @Test
    public void test22() {
        //10000111001
        //1000000000
        long a = 1081l;
        System.out.println((a & 0x400));
    }

    @Test
    public void test23() {
        int a = 7; // 二进制表示为 0111
        int b = 9; // 二进制表示为 1001
        int c = a & b; // 结果为 0，因为 0111 & 1001 = 0001
        System.out.println(c); // 输出 0
    }

    @Test
    public void test24() {
        Map<String, Double> map = new HashMap<>();
//        map.put("a" , 1.001);
//        map.put("b" , 2.2);
//        map.put("c" , 3.9);
        System.out.println(map.values().stream().mapToDouble(Double::doubleValue).average().orElse(1.0));
    }

    @Test
    public void test25() throws UnsupportedEncodingException {
        String url = "http://localhost:8888/imp?id=1&name=提升-30%&ua=ua";
//        url = URLEncoder.encode(url, String.valueOf(StandardCharsets.UTF_8));
        System.out.println(url);
        url = "http://localhost:8888/imp?id=1&name=%E6%8F%90%E5%8D%87-30%&ua=ua";
        System.out.println(HttpUtil.createGet(url).execute().body());
    }

    @Test
    public void test26() {
        String url = "https://tr.byteurl.cn/ad/impression/toutiao/?action_type=view&surl_token=ejLLW&ad_id=1810681553503307&ad_name=cdp_promotion_7416551640272027674&campaign_id=1810680526754009&creative_id=1810681587807435&csite=900000000&ctype=15&mac=&mac1=d41d8cd98f00b204e9800998ecf8427e&ua=Mozilla%2F5.0+%28Linux%3B+Android+11%3B+PEQM00+Build%2FRP1A.200720.011%3B+wv%29+AppleWebKit%2F537.36+%28KHTML%2C+like+Gecko%29+Version%2F4.0+Chrome%2F83.0.4103.106+Mobile+Safari%2F537.36&idfa=&imei_md5=&android_id_md5=&os=0&ip=117.188.48.157&event_time_ts=1727156887000&convert_id=0&callback_param=CMuIsbbi2ZsDEMvp3sbi2ZsDGMa91uu9ASCGhq_sbCgAMAw4t4mBqANCJThjOWM3YWI4NDhhYmM5M2VhNTc3NzFmYTBlOWNkNjRhdTczMzFIgNKTrQNQAJABAg&oaid=10B9D2215FEB47A8BC2BA04D1B32678D86654435f9853c9a9b1ec44d2c3632f1&union_site=2460838542&req_id=8c9c7ab848abc93ea57771fa0e9cd64au7331&rta_trace_id=8c9c7ab848abc93ea57771fa0e9cd64au7331&outerid=&track_type=&uid=29218292486&did=&project_name=ejLLW-拉新-三目标-and-穿山甲-纯激励-0920-激励版位-【OPPO】_11/40%/1&promotion_name=ejLLW-and-短剧-纯激励-19-0001-聚云-激励版位-0920_04&ad_platform=toutiao&promotion_id=7416551640272027674&project_id=7416495326411554843&ug_semver=v1.3.4";
        System.out.println(HttpUtil.createGet(url).execute().body());
    }

    @Test
    public void test27() {
        List<String> reqs = new ArrayList<>();
        reqs.add("http://localhost:8888/imp?id=1&name=zhang&ua=ua");
        reqs.add("http://localhost:8888/imp?id=2&name=zhang&ua=ua");
        reqs.add("http://localhost:8888/imp?id=3&name=zhang&ua=ua");
        reqs.add("http://localhost:8888/imp?id=4&name=zhang&ua=ua");
        reqs.add("http://localhost:8888/imp?id=5&name=zhang&ua=ua");
        reqs.add("http://localhost:8888/imp?id=6&name=zhang&ua=ua");
        reqs.add("http://localhost:8888/imp?id=7&name=zhang&ua=ua");
        reqs.add("http://localhost:8888/imp?id=8&name=zhang&ua=ua");
        reqs.add("http://localhost:8888/imp?id=9&name=zhang&ua=ua");
        reqs.add("http://localhost:8888/imp?id=10&name=zhang%&ua=ua");
        reqs.add("http://localhost:8888/imp?id=11&name=zhang%&ua=ua");
        reqs.add("http://localhost:8888/imp?id=12&name=zhang%&ua=ua");
        reqs.add("http://localhost:8888/imp?id=13&name=zhang%&ua=ua");
        reqs.add("http://localhost:8888/imp?id=14&name=zhang%&ua=ua");
        reqs.add("http://localhost:8888/imp?id=15&name=zhang%&ua=ua");
        //调用http接口，控制在1qps
        reqs.forEach(req -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(HttpUtil.createGet(req).execute().body());
            //TODO 拿到res后取出text，入库
        });
    }

    @Test
    public void test28() {
        List<String> urls = new ArrayList<>();
        urls.add("http://lf6-sf-be-pack-sign.pglstatp-toutiao.com/ad.union.api/1e579711a96541b983c9fa0bd848ff09?lk3s=d16ba171&x-expires=1942070400&x-signature=WUZuiBVj6hJehff4FlVLi06S%2BZw%3D");
        urls.add("http://lf3-sf-be-pack-sign.pglstatp-toutiao.com/ad.union.api/ab7f45864874c1158ba786dc45b1d3b6?lk3s=d16ba171&x-expires=1942070400&x-signature=wWHOFZ4DeHhSH10n949Ztt5W2U4%3D");
        urls.add("https://store.heytapimage.com/img/202409/18/4d0c2cabe13b75b726670c7d3e5309dc.png");
        urls.add("https://qh-material.taobao.com/dsp/img/sxjnp6ndbekfyz6hexdswnadwspdthwx.jpg?x-oss-process=image/resize,w_720,h_1280,limit_0,m_fill&");
        urls.add("https://p0.meituan.net/bizad/giga_13033343_36b52b417a489be47dde8258c623f18a.jpg");
        urls.add("http://lf26-sf-be-pack-sign.pglstatp-toutiao.com/ad.union.api/d5f05d9e39cc4943d0b272eb6de273a0?lk3s=d16ba171&x-expires=1942070400&x-signature=ipdZGi%2BgKbSy4NTB6%2FDqWDPojTY%3D");
        urls.add("https://qh-material.taobao.com/dsp/img/m75zze27x37ddhfb3qnckefdt2rwrjae.jpg?x-oss-process=image/resize,w_720,h_1280,limit_0,m_fill&");
        urls.add("https://p2-lm.adukwai.com/bs2/adUnionVideo/dcc6c679df544b77ab9366b7b1d2c85f.jpg");
        urls.add("https://qh-material.taobao.com/dsp/img/ca6g8kk5ns5emnsxwzrz7xci3h3geest.jpg?x-oss-process=image/resize,w_720,h_1280,limit_0,m_fill&");
        urls.add("https://p2-lm.adukwai.com/bs2/adUnionVideo/6ae617493dfb4f4b913e5e037d448511.jpg");
        urls.add("https://p16.a.kwimgs.com/bs2/adUnionVideo/99cbfe11e9894ed88d3e2989fe9b101f.jpg?ocid=100000026&sdktyv=v1");
        urls.add("http://lf9-sf-be-pack-sign.pglstatp-toutiao.com/ad.union.api/81a6f4b70fcc39c4883e6c6ea13d535f?lk3s=d16ba171&x-expires=1942070400&x-signature=9HcvaKvXOJc69SgzNQI0yh%2B8dMk%3D");
        urls.add("https://qh-material.taobao.com/dsp/img/j8xkbkktknyb8yfsscjxzwqaskejq3mt.jpg?x-oss-process=image/resize,w_720,h_1280,limit_0,m_fill&");
        urls.add("http://lf26-sf-be-pack-sign.pglstatp-toutiao.com/ad.union.api/2e14122f6a5d0cb628a610968fb575be?lk3s=d16ba171&x-expires=1942070400&x-signature=i6Z2bTiu8r0FRnjgV9aMoWQSlo0%3D");
        urls.add("https://p0.meituan.net/bizad/giga_14766896_bafe81086bbc2b191c543b3cadbe1017.jpg");
        urls.add("http://lf9-sf-be-pack-sign.pglstatp-toutiao.com/ad.union.api/030300fcf18002bb30d7fef544759f96?lk3s=d16ba171&x-expires=1942070400&x-signature=QBDeQRYtFuJNDjvGF1dfAIsaP08%3D");
        urls.add("https://ilce.alicdn.com/minolta/452286/5/adef4e5a107f496faee22b733b74c279.jpg80?content=%7B%2214%22%3A%7B%22attrs%22%3A%7B%22value%22%3A%229999.0%22%7D%7D%2C%2216%22%3A%7B%22attrs%22%3A%7B%22value%22%3A%22https%3A%2F%2Fimg.alicdn.com%2Fi4%2F1917047079%2FO1CN01HTweY922AEju6azVT_%21%212-item_pic.png%22%7D%7D%2C%227%22%3A%7B%22attrs%22%3A%7B%22value%22%3A%22%E6%B7%98%E5%AE%9D%E7%B2%BE%E9%80%89%E5%A5%BD%E7%89%A9%22%7D%7D%2C%229%22%3A%7B%22attrs%22%3A%7B%22value%22%3A%22%E6%B7%98%E5%AE%9D%E7%B2%BE%E9%80%89%E5%A5%BD%E7%89%A9%22%7D%7D%7D&channel=114&");
        urls.add("https://qh-material.taobao.com/dsp/aigc/merge/39a56ea17ad4119bec5a42f4de34e9f6.png?x-oss-process=image/resize,w_720,h_1280,limit_0,m_fill&");
        urls.add("http://lf9-sf-be-pack-sign.pglstatp-toutiao.com/ad.union.api/1b49e94e8184738fdcd2ab7c13710024?lk3s=d16ba171&x-expires=1942070400&x-signature=3NcedgiNTHhf4AT4rSGE1v%2BFdZ0%3D");
        urls.add("http://lf9-sf-be-pack-sign.pglstatp-toutiao.com/ad.union.api/6fbc813f4b1852dc4135d7446fdf550c?lk3s=d16ba171&x-expires=1942070400&x-signature=aH8%2B5LpfsLFNxZHMKQCyhxud1ec%3D");
        urls.add("http://lf9-sf-be-pack-sign.pglstatp-toutiao.com/ad.union.api/e4fac54bcc1099f028b6cd965923273c?lk3s=d16ba171&x-expires=1942070400&x-signature=%2BJk4E%2BFzKZOWRU%2Ff%2BdgN5W%2FSdGk%3D");
        urls.add("http://lf9-sf-be-pack-sign.pglstatp-toutiao.com/ad.union.api/f153b494f797020ede57107bf240c44e?lk3s=d16ba171&x-expires=1942070400&x-signature=lP80eg0DIwsssv46KbWl3aNJQTs%3D");
        urls.add("http://lf6-sf-be-pack-sign.pglstatp-toutiao.com/ad.union.api/945144748e5a7bcd4dfcb41c54e0568d?lk3s=d16ba171&x-expires=1942070400&x-signature=m%2FC83YWqKMV5hVp4BFpnSRRlplk%3D");
        urls.add("https://p0.meituan.net/bizad/giga_9689701_17a797f8e1c7fe22d26242329618e3de.jpg");
        urls.add("https://adsfs.heytapimage.com/ads-material-depot/image/98807e6d497f21e4e1d07fd7fac8f99c.jpg");
        urls.add("http://lf3-sf-be-pack-sign.pglstatp-toutiao.com/ad.union.api/b5e268c97e86c90b827a420e79de0650?lk3s=d16ba171&x-expires=1942070400&x-signature=3P2MLpOAy4ByHZ0hjLF4Qeba77E%3D");
        urls.add("https://images.pinduoduo.com/marketing_api/2023-09-23/9a439c6c-e7d9-11ec-b87d-0a580a4c9737.jpg?imageMogr2/thumbnail/720x1280!");
        urls.add("http://lf26-sf-be-pack-sign.pglstatp-toutiao.com/ad.union.api/098600b3abcfedd02a47766bcd32fc7e?lk3s=d16ba171&x-expires=1942070400&x-signature=aWzKzTZvLVbyqFidSrY7WxT8D%2Fw%3D");
        urls.add("https://qh-material.taobao.com/dsp/img/byrbhbjemx8hzcpr22whewnr4fifawwj.jpg?x-oss-process=image/resize,w_720,h_1280,limit_0,m_fill&");
        urls.add("http://lf3-sf-be-pack-sign.pglstatp-toutiao.com/ad.union.api/cadba2609c4354fbc03e348a58e6136a?lk3s=d16ba171&x-expires=1942070400&x-signature=WDBtRr9OOCUoHD9FNJ12zgsH1%2F4%3D");
        urls.add("http://lf26-sf-be-pack-sign.pglstatp-toutiao.com/ad.union.api/827cf46876df5e6d71d6d3d6e70ef779?lk3s=d16ba171&x-expires=1942070400&x-signature=xm7qbbRZkP8i2K2JEOnZUoGmfoM%3D");
        urls.add("https://p2-lm.adukwai.com/bs2/adUnionVideo/e795521c8a8041c6af89ba084a557326.jpg");
        urls.add("http://lf26-sf-be-pack-sign.pglstatp-toutiao.com/ad.union.api/e7787ecf3845b23ff3bbdaa1e5dd4b15?lk3s=d16ba171&x-expires=1942070400&x-signature=My3ONVZ8wBVbS6azUXOSVjBdL44%3D");
        urls.add("https://adsfs.heytapimage.com/ads-material-depot/image/5a4f961029d6385cbb8b042cd63cab2b.jpg");
        urls.add("https://qh-material.taobao.com/dsp/img/hnrx7giw8sf7nz8ds24axjzxwqxncrbz.jpg?x-oss-process=image/resize,w_720,h_1280,limit_0,m_fill&");
        urls.add("https://ilce.alicdn.com/minolta/469055/2/560b938593b4e6317ee289ab6da2728c.jpg80?content=%7B%2215%22%3A%7B%22attrs%22%3A%7B%22value%22%3A%222.1%22%7D%7D%2C%227%22%3A%7B%22attrs%22%3A%7B%22value%22%3A%22https%3A%2F%2Fimg.alicdn.com%2Fi3%2F2930255252%2FO1CN01WvD7MU1ofT4ZjID8n_%21%210-item_pic.jpg%22%7D%7D%2C%2210%22%3A%7B%22attrs%22%3A%7B%22value%22%3A%22%E6%BC%AB%E8%8A%B1%E5%AE%B6%E7%94%A8%E7%BA%B8%E5%B7%BE%22%7D%7D%7D&channel=114&");
        urls.add("http://lf9-sf-be-pack-sign.pglstatp-toutiao.com/ad.union.api/18a5d93296329ebe7c30d7aba87b7f8c?lk3s=d16ba171&x-expires=1942070400&x-signature=KTkRXsjy%2BH7Sr8r8fzwDsmHUAv0%3D");
        urls.add("https://adsfs.heytapimage.com/ads-material-depot/image/31e5c2a9c3883840074b0555853a130c.png");
        urls.add("https://ilce.alicdn.com/minolta/452276/7/3e033fcacb2cf76aa72c7d2c60808c6a.jpg80?content=%7B%2213%22%3A%7B%22attrs%22%3A%7B%22value%22%3A%2219.9%22%7D%7D%2C%225%22%3A%7B%22attrs%22%3A%7B%22value%22%3A%22https%3A%2F%2Fimg.alicdn.com%2Fi4%2F509142617%2FO1CN01ZChbnj1VCdUo8x9br_%21%21509142617.jpg%22%7D%7D%2C%226%22%3A%7B%22attrs%22%3A%7B%22value%22%3A%22%E7%94%B5%E7%83%AD%E5%84%BF%E7%AB%A5%E6%B6%A6%E6%9C%AC%22%7D%7D%2C%228%22%3A%7B%22attrs%22%3A%7B%22value%22%3A%22%E6%B7%98%E5%AE%9D%E7%B2%BE%E9%80%89%E5%A5%BD%E7%89%A9%22%7D%7D%7D&channel=114&");
        urls.add("http://lf26-sf-be-pack-sign.pglstatp-toutiao.com/ad.union.api/0dbf9857e92b4f726a015a1c0a91a32f?lk3s=d16ba171&x-expires=1942070400&x-signature=nNFJRHThrUGU20UfCgIteNC7eQo%3D");
        urls.add("http://lf9-sf-be-pack-sign.pglstatp-toutiao.com/ad.union.api/0ee2c10fcffa6a2e36ca80b93954bd62?lk3s=d16ba171&x-expires=1942070400&x-signature=2jZkHXeSQYCyQA5NV3KSSnIsIOw%3D");
        urls.add("http://lf3-sf-be-pack-sign.pglstatp-toutiao.com/ad.union.api/5d285dcbe49fe3a7a42049768107a457?lk3s=d16ba171&x-expires=1942070400&x-signature=0kj1IzPVRBeThOBGxVt6NmXdKEo%3D");
        urls.add("https://store.heytapimage.com/img/202409/18/4d0c2cabe13b75b726670c7d3e5309dc.png");
        urls.add("https://p0.meituan.net/bizad/giga_14471423_4f656313fcacadbb5cbc95f3f4743b55.jpg");
        urls.add("https://qh-material.taobao.com/dsp/img/3dj73yymaqy3kftpa8meyfrnm4s3xjts.jpg?x-oss-process=image/resize,w_720,h_1280,limit_0,m_fill&");
        urls.add("https://alpha.alicdn.com/minolta/166864/0/eb8280efaefcbc781c9aed5cd4debea8.jpg_1080x1920.jpg?content=%7B%2224%22%3A%7B%22mini%22%3Afalse%2C%22scale%22%3A%22papercut%22%2C%22url%22%3A%22O1CN01i4ecEm1lU4C1yaYyu_%21%212181924821.jpg%22%7D%2C%2228%22%3A%7B%22value%22%3A%22%5Cu9632%5Cu81ed%5Cu9632%5Cu53cd%5Cu5473%5Cu5730%5Cu6f0f%22%7D%7D&pid=mm_26632523_17388321_110964150058&channel=4&getAvatar=avatar");
        urls.add("http://lf3-sf-be-pack-sign.pglstatp-toutiao.com/ad.union.api/71f4b6cb391887f4b61766f47ecf2b23?lk3s=d16ba171&x-expires=1942070400&x-signature=Oeh85%2F9dLuOvpTZt%2F9vbFs9%2FlDQ%3D");
        urls.add("http://lf9-sf-be-pack-sign.pglstatp-toutiao.com/ad.union.api/1be9da465f8c2773b448b856b6621a34?lk3s=d16ba171&x-expires=1942070400&x-signature=T7s1o8%2BwmUnNJsH18NlEABeg%2Bqs%3D");
        urls.add("https://adsfs.heytapimage.com/ads-material-depot/image/7100b7f3c0fbf5807065a15f47966700.jpg");
        urls.add("https://p0.meituan.net/bizad/dd8fe1186bd3db1d451f816885970b59.jpg");
        urls.add("http://lf9-sf-be-pack-sign.pglstatp-toutiao.com/ad.union.api/60221f46e0c0cb3bd78f04c218c43976?lk3s=d16ba171&x-expires=1942070400&x-signature=QOXPnDXOLofabz29mQiozwj5gG4%3D");
        urls.add("https://p2-lm.adukwai.com/bs2/adUnionVideo/c1d2b62a194244c7801f798989659e35.jpg");
        urls.add("https://images.pinduoduo.com/marketing_api/2023-04-12/330eff56-050f-42fe-a62c-fa6aafd9952b.jpeg?imageMogr2/thumbnail/720x1280!");
        urls.add("https://img1.360buyimg.com/pop/jfs/t1/240599/27/18403/79326/66ecf9c6F437c09dd/18b3f8a29e02d01a.jpg");
        urls.add("https://p2-lm.adukwai.com/bs2/adUnionVideo/1837a00891ef416298624f468d1fbd09.jpg");
        urls.add("https://img-x.jd.com/1280x720_s3.jpg?d=26&1=jfs/t1/130162/4/44938/84133/66e255adF4af63a69/3a8fd55cfccb539f.jpg&2=jfs/t1/20663/4/22609/81527/66c7fed6Fb3a9ad6e/7aed984eddf5e9c6.jpg");
        urls.add("https://p0.meituan.net/bizad/giga_19405660_91b14b9d4ff2200f4a793e42abb15d56.jpg");
        urls.add("https://ilce.alicdn.com/minolta/470138/2/0e8919b228235a4029f2862e0e2c290a.jpg80?content=%7B%2213%22%3A%7B%22attrs%22%3A%7B%22value%22%3A%22%E9%97%AA%E9%AD%94%E6%96%B0%E6%AC%BE%E9%92%A2%E5%8C%96%E8%86%9C%22%7D%7D%2C%225%22%3A%7B%22attrs%22%3A%7B%22value%22%3A%22https%3A%2F%2Fimg.alicdn.com%2Fi1%2F793375733%2FO1CN01aMLj2i1sDldQ2zPPH_%21%210-item_pic.jpg%22%7D%7D%2C%2210%22%3A%7B%22attrs%22%3A%7B%22value%22%3A%2215.6%22%7D%7D%7D&channel=114&");
        urls.add("https://img1.360buyimg.com/pop/jfs/t1/184360/4/47968/116132/66f28f61F0e969295/1c613c9380697bfa.jpg");
        urls.add("https://p2-lm.adukwai.com/bs2/adUnionVideo/1f56e3e4b19f48fa99c6a4f4c3f95eb6.jpg");
        urls.add("https://feed-image.baidu.com/0/pic/-803668268_599286534_1893685529.jpg");
        urls.add("https://adsfs.heytapimage.com/ads-material-depot/image/870a15908d24890c770c34c77d375e09.jpg");
        urls.add("https://p0.meituan.net/bizad/b452972c6062b773f56a1641da89308a.jpg");
        urls.add("https://p2-lm.adukwai.com/bs2/adUnionVideo/61739d53e3354f8982cb9ade07a4733b.jpg");
        urls.add("https://images.pinduoduo.com/marketing_api/2023-10-04/2887b178-e7f4-11ed-b2fb-0a580a4bf4ad.jpg?imageMogr2/thumbnail/720x1280!");
        urls.add("https://images.pinduoduo.com/marketing_api/2023-02-01/198299c7-9429-47ee-9355-d1a67309f26f.jpeg?imageMogr2/thumbnail/720x1280!");
        urls.add("http://lf6-sf-be-pack-sign.pglstatp-toutiao.com/ad.union.api/d5f05d9e39cc4943d0b272eb6de273a0?lk3s=d16ba171&x-expires=1942070400&x-signature=tYsWJsClyjrWUJG4rqLU1MKWcqw%3D");
        urls.add("https://images.pinduoduo.com/marketing_api/2023-12-16/0e5a29c6-d1fa-11ed-9b5a-0a580a494078.jpg?imageMogr2/thumbnail/720x1280!");
        urls.add("https://img1.360buyimg.com/pop/jfs/t1/240599/27/18403/79326/66ecf9c6F437c09dd/18b3f8a29e02d01a.jpg");
        urls.add("https://p0.meituan.net/bizad/dd8fe1186bd3db1d451f816885970b59.jpg");
        urls.add("https://img1.360buyimg.com/pop/jfs/t1/234037/29/24661/78432/66ebf6ffFabf562ac/548bc19908a50d3e.jpg");
        urls.add("http://lf26-sf-be-pack-sign.pglstatp-toutiao.com/ad.union.api/1449abfadd5083b70695cda0b67820e0?lk3s=d16ba171&x-expires=1942070400&x-signature=iiyPOQdsL7GC8dsCxDZwWI7N5TA%3D");
        urls.add("https://qh-material.taobao.com/dsp/img/rn4inxdbkjtjmhcgrhmswpm7bwbpemec.jpg?x-oss-process=image/resize,w_720,h_1280,limit_0,m_fill&");
        urls.add("http://lf9-sf-be-pack-sign.pglstatp-toutiao.com/ad.union.api/6d2468d5e0321b173689f94cf2f4963e?lk3s=d16ba171&x-expires=1942070400&x-signature=LMkEZQ4j1asZEPNkeE4RhxFs8Xc%3D");
        urls.add("https://images.pinduoduo.com/marketing_api/2024-06-28/44880956-9b68-11ec-9725-0a580a484ed9.jpg?imageMogr2/thumbnail/720x1280!");
        urls.add("https://qh-material.taobao.com/dsp/img/smza3mmqbjfwsfrbrpfqbsyqqaf7b8t3.jpg?x-oss-process=image/resize,w_720,h_1280,limit_0,m_fill&");
        urls.add("https://p2-lm.adukwai.com/bs2/adUnionVideo/4eb21eef5d274d61a5111eccde6dff5d.jpg");
        urls.add("http://lf26-sf-be-pack-sign.pglstatp-toutiao.com/ad.union.api/79735f6ee0aac73e90ca336ecb88b28c?lk3s=d16ba171&x-expires=1942070400&x-signature=EcJOGAvtupwd2DcQ0Tg85MP8Btc%3D");
        urls.add("https://qh-material.taobao.com/dsp/img/ze2pa7wnx6n2mydta4fp5idm7bsatkwh.jpg?x-oss-process=image/resize,w_720,h_1280,limit_0,m_fill&");
        urls.add("https://p0.meituan.net/waimaiad/0c4ffac6b68b7405bccf4529029e0736132382.jpg");
        urls.add("https://img-x.jd.com/1280x720_s3.jpg?d=26&1=jfs/t1/99914/21/38158/69427/6465e8b9F7c589dd3/9faade4c9715b7b6.jpg&2=jfs/t1/183107/18/20276/90602/61230803Ebdf19fb4/7e5d1e6d670f5ec3.jpg");
        urls.add("https://adsfs.heytapimage.com/ads-material-depot/image/6acdd3a28d5dcf555a0bb9724d4ac332.jpg");
        urls.add("https://images.pinduoduo.com/marketing_api/2022-12-11/63644540-53c9-4ba9-8f70-c667d466e44c.jpeg?imageMogr2/thumbnail/720x1280!");
        urls.add("http://lf3-sf-be-pack-sign.pglstatp-toutiao.com/ad.union.api/da012909850e3f26f5686447980f99c0?lk3s=d16ba171&x-expires=1942070400&x-signature=hynWFe3%2BeohWfTW8vqpLH5qNAak%3D");
        urls.add("http://lf6-sf-be-pack-sign.pglstatp-toutiao.com/ad.union.api/e4fac54bcc1099f028b6cd965923273c?lk3s=d16ba171&x-expires=1942070400&x-signature=WTGvAJ36WHloVHtyOg1sEDzrLrE%3D");
        urls.add("https://img-x.jd.com/1280x720_s3.jpg?d=26&1=jfs/t1/234351/16/22848/224407/6683ac80F90674fb1/32339521c5d1506a.jpg&2=jfs/t1/108124/24/11188/132752/5e86e9bcE3658c6d0/1ae15747166e46f6.jpg");
        urls.add("http://lf3-sf-be-pack-sign.pglstatp-toutiao.com/ad.union.api/fd6a0ff866a2ed1fd32af93f2b6bbbf0?lk3s=d16ba171&x-expires=1942070400&x-signature=5DJT4Y5LI4jwlL6RXwy9XZKtTIg%3D");
        urls.add("http://lf3-sf-be-pack-sign.pglstatp-toutiao.com/ad.union.api/228c68d1b89166954167929a0d1a5b40?lk3s=d16ba171&x-expires=1942070400&x-signature=9905RteHFOCbbvU%2FK06KmOtvxu0%3D");
        urls.add("http://lf9-sf-be-pack-sign.pglstatp-toutiao.com/ad.union.api/53d1713357710356d38469461d88edb1?lk3s=d16ba171&x-expires=1942070400&x-signature=8yo3JrLNvT6gVdeTe5uVSrfaoPU%3D");
        urls.add("https://adsfs.heytapimage.com/ads-material-depot/image/58645f8fc7b61372cdcd1e4a53151a1e.jpg");
        urls.add("https://ilce.alicdn.com/minolta/467356/6/bbd507ed45ed5c8ae75b39db2443648e.jpg80?content=%7B%227%22%3A%7B%22attrs%22%3A%7B%22value%22%3A%22https%3A%2F%2Fimg.alicdn.com%2Fi3%2F494858290%2FO1CN01mfP8Za2B6sLhnNU10_%21%210-item_pic.jpg%22%7D%7D%2C%2219%22%3A%7B%22attrs%22%3A%7B%22value%22%3A%22%E5%8A%A0%E5%8E%9A%E7%BA%B8%E6%B4%81%E4%B8%BD%E9%9B%85%E6%B4%97%E8%84%B8%E5%B7%BE%22%7D%7D%2C%2230%22%3A%7B%22attrs%22%3A%7B%22value%22%3A%2229.9%22%7D%7D%7D&channel=114&");
        urls.add("https://adsfs.heytapimage.com/ads-material-depot/image/736d350587e642de1a8e317f6bf99367.jpg");
        urls.add("https://p2-lm.adukwai.com/bs2/adUnionVideo/e3c7d389e81d432a969889a44fe78d0d.jpg");
        urls.add("http://lf3-sf-be-pack-sign.pglstatp-toutiao.com/ad.union.api/e0953bcd5fb2a44b5c9716fc412d762d?lk3s=d16ba171&x-expires=1942070400&x-signature=m%2BoVyZiPqA8YEErDvluMox0Y%2FUQ%3D");
        urls.add("https://qh-material.taobao.com/dsp/img/e8eprrkg4nbdftprddn48zjdjta2snih.jpg?x-oss-process=image/resize,w_720,h_1280,limit_0,m_fill&");
        urls.add("https://qh-material.taobao.com/dsp/img/zrehxxnimtnhbmid6cff8hbjsdbyf6zp.jpg?x-oss-process=image/resize,w_720,h_1280,limit_0,m_fill&");
        urls.add("https://p0.meituan.net/bizad/giga_2414192_f07eef6e0c9a3c651ebc042c6f2dd2f1.jpg");
        urls.add("https://images.pinduoduo.com/marketing_api/2023-09-03/3d3f7d34-ddaf-11ed-bb06-0a580a4e609a.jpg?imageMogr2/thumbnail/720x1280!");
        urls.add("https://images.pinduoduo.com/marketing_api/2024-09-17/b4849d8e-569f-11ee-b4b0-0a580a48c89f.jpg?imageMogr2/thumbnail/720x1280!");
        urls.add("https://img1.360buyimg.com/pop/jfs/t1/130960/20/43587/92650/66ddd04cFad558504/484a502e1610a2bd.jpg");
        urls.add("https://adsfs.heytapimage.com/ads-material-depot/image/05c20711ca54192a7f89921934ad42bf.jpg");
        urls.add("http://lf9-sf-be-pack-sign.pglstatp-toutiao.com/ad.union.api/77f2b841165ac5d211bc80598a6a3f31?lk3s=d16ba171&x-expires=1942070400&x-signature=MfJWsNjlsBIUyn%2Bnc5jlyrDztbI%3D");
        urls.add("https://alpha.alicdn.com/minolta/309543/0/fb92b37e31186e4db4fdc0352cfa88c1.jpg_1080x1920.jpg?content=%7B%2221%22%3A%7B%22filters%22%3A%5B%7B%22attrs%22%3A%7B%22dst_rect%22%3A%5B0%2C0%2C748%2C750%5D%2C%22src_rect%22%3A%5B12%2C8%2C770%2C778%5D%7D%2C%22type%22%3A%22copy%22%7D%5D%2C%22url%22%3A%22O1CN01ToTLXq2B6sCzdRnmd_%21%21494858290.jpg%22%7D%2C%225%22%3A%7B%22value%22%3A%22%5Cu7eaf%5Cu68c9%5Cu52a0%5Cu539a%5Cu538b%5Cu7f29%5Cu6bdb%5Cu5dfe%22%7D%7D&pid=mm_26632523_17388321_110964150058&channel=4&getAvatar=avatar");
        urls.add("https://img1.360buyimg.com/pop/jfs/t1/240599/27/18403/79326/66ecf9c6F437c09dd/18b3f8a29e02d01a.jpg");
        urls.add("https://qh-material.taobao.com/dsp/img/zpjecfpbkmrcqxre4swcsizkn6mqgmhr.jpg?x-oss-process=image/resize,w_720,h_1280,limit_0,m_fill&");
        urls.add("https://p0.meituan.net/bizad/cdbfd2290c446a8fffcfb5b1f4bd1f94.jpg");
        urls.add("http://lf9-sf-be-pack-sign.pglstatp-toutiao.com/ad.union.api/a1807eb4946e98b1087b9d4a8001b3a4?lk3s=d16ba171&x-expires=1942070400&x-signature=DQxhUc4%2Ffox2t0NpxOY%2BNTTZAEA%3D");
        urls.add("https://p2-lm.adukwai.com/bs2/adUnionVideo/5b5ac39c11bb4f7db92b4cd5ba824fb1.jpg");
        urls.add("https://adsfs.heytapimage.com/ads-material-depot/image/74f5e1df786db3ff53e937a6c068bb7d.png");
        urls.add("https://adsfs.heytapimage.com/ads-material-depot/image/bb0d67e586a969fa0300821409f58c0c.jpg");
        urls.add("https://qh-material.taobao.com/dsp/img/ikatyexhfkssqr7cwfhqwgzpczywzc4x.jpg?x-oss-process=image/resize,w_720,h_1280,limit_0,m_fill&");
        urls.add("https://store.heytapimage.com/img/202409/18/4d0c2cabe13b75b726670c7d3e5309dc.png");
        urls.add("https://adsfs.heytapimage.com/ads-material-depot/image/555ce4002893b809c6179a6ab8e4df99.jpg");
        urls.add("https://adsfs.heytapimage.com/ads-material-depot/image/f40cb9fd950a449fd83cfb955b7ca8b2.jpg");
        urls.add("https://qh-material.taobao.com/dsp/img/apkas4fpp3cecdcyyx76qwp6i2jpihpb.jpg?x-oss-process=image/resize,w_720,h_1280,limit_0,m_fill&");
        urls.add("http://lf9-sf-be-pack-sign.pglstatp-toutiao.com/ad.union.api/a28ecc6785083f3c2951700f636e8c93?lk3s=d16ba171&x-expires=1942070400&x-signature=BjXHrHqHVETGhjzINTgIaOdx6xk%3D");
        urls.add("https://images.pinduoduo.com/marketing_api/2024-01-03/b7ab7b54-218e-11ee-8ce1-0a580a4c7093.jpg?imageMogr2/thumbnail/720x1280!");
        urls.add("http://lf9-sf-be-pack-sign.pglstatp-toutiao.com/ad.union.api/a6c4eb374a78ffecef9c5773ff893f89?lk3s=d16ba171&x-expires=1942070400&x-signature=OZsOrUUbHgWYLm%2FGED3a%2FJzWrhc%3D");
        urls.add("https://p2-lm.adukwai.com/bs2/adUnionVideo/dd57837f23b24d20bf06e220dd6e0f2c.jpg");
        urls.add("http://lf26-sf-be-pack-sign.pglstatp-toutiao.com/ad.union.api/5fb8068a3dfb133b6b10d5505bf6d3cd?lk3s=d16ba171&x-expires=1942070400&x-signature=4H14EzdgrDAgwSSDTt%2BEQ7IzizI%3D");
        urls.add("https://p2-lm.adukwai.com/bs2/adUnionVideo/8610da354e8a48159029ecdccb207893.jpg");
        urls.add("https://ilce.alicdn.com/minolta/452276/7/d69154939e26914e583a1b060347dca5.jpg80?content=%7B%2213%22%3A%7B%22attrs%22%3A%7B%22value%22%3A%2225.9%22%7D%7D%2C%225%22%3A%7B%22attrs%22%3A%7B%22value%22%3A%22https%3A%2F%2Fimg.alicdn.com%2Fi3%2F2210110774181%2FO1CN01CmN5CF1gkwkvwozrr_%21%212210110774181-0-tblite.jpg%22%7D%7D%2C%226%22%3A%7B%22attrs%22%3A%7B%22value%22%3A%22%E5%AD%A6%E7%94%9F%E4%B8%8D%E9%94%88%E9%92%A2%E5%BF%AB%E9%A4%90%E6%9D%AF%22%7D%7D%2C%228%22%3A%7B%22attrs%22%3A%7B%22value%22%3A%22%E7%88%86%E6%AC%BE%E4%BD%8E%E4%BB%B7%E5%A5%BD%E7%89%A9%22%7D%7D%7D&channel=114&");
        urls.add("https://p16.a.kwimgs.com/udata/pkg/market6f62daa7b6f34914820d5d57da4ec573.jpg?ocid=100000034&sdktyv=v1");
        urls.add("http://lf26-sf-be-pack-sign.pglstatp-toutiao.com/ad.union.api/baf9b66fe252fa905b9666018882d795?lk3s=d16ba171&x-expires=1942070400&x-signature=2GqmGy7SBB2WyMPD34B%2F3QsrjAg%3D");
        urls.add("https://adsfs.heytapimage.com/ads-material-depot/image/fbece448aef4db85c2f3f0535090b0e9.jpg");
        urls.add("https://alpha.alicdn.com/minolta/309543/0/913b1c7c57c5c3a2e4ec5db284ca66cf.jpg_1080x1920.jpg?content=%7B%2221%22%3A%7B%22filters%22%3A%5B%7B%22attrs%22%3A%7B%22dst_rect%22%3A%5B0%2C0%2C748%2C750%5D%2C%22src_rect%22%3A%5B12%2C8%2C770%2C778%5D%7D%2C%22type%22%3A%22copy%22%7D%5D%2C%22url%22%3A%22O1CN01ToTLXq2B6sCzdRnmd_%21%21494858290.jpg%22%7D%2C%225%22%3A%7B%22value%22%3A%22%5Cu7eb3%5Cu7c73%5Cu878d%5Cu4e1d%5Cu79d1%5Cu6280%5Cu9762%5Cu6599%22%7D%7D&pid=mm_26632523_17388321_110964150058&channel=4&getAvatar=avatar");
        urls.add("https://img-x.jd.com/1280x720_s3.jpg?d=26&1=jfs/t1/247415/26/18796/97769/66f36e83F4e5a8bff/bd46579f7eb5f032.jpg&2=jfs/t1/237384/2/11793/104743/65b3252bFed14195d/c53e9e71ea982775.jpg");
        urls.add("https://adsfs.heytapimage.com/ads-material-depot/image/31e5c2a9c3883840074b0555853a130c.png");
        urls.add("http://lf6-sf-be-pack-sign.pglstatp-toutiao.com/ad.union.api/96800f1ec70fe88ea3c94a685f64c5d5?lk3s=d16ba171&x-expires=1942070400&x-signature=p62xJiobaozxFZcd4LM3wrh5mh0%3D");
        urls.add("http://lf6-sf-be-pack-sign.pglstatp-toutiao.com/ad.union.api/2e41b675d9d563339aa7f635662f108e?lk3s=d16ba171&x-expires=1942070400&x-signature=c9Nj7Ike558OuxqeIV6dVmHOigY%3D");
        urls.add("https://qh-material.taobao.com/dsp/img/gp4x5rikrbptk7w8cnegamss5cs8ktze.jpg?x-oss-process=image/resize,w_720,h_1280,limit_0,m_fill&");
        urls.add("http://lf3-sf-be-pack-sign.pglstatp-toutiao.com/ad.union.api/8737c213a959f65a9e11771d89688d9c?lk3s=d16ba171&x-expires=1942070400&x-signature=ITz%2FL%2BURQrd4t9WeG%2FAfIJQ1WaU%3D");
        urls.add("https://ilce.alicdn.com/minolta/475403/1/fbdfa9121ad792677149833ea610def7.jpg80?content=%7B%2214%22%3A%7B%22attrs%22%3A%7B%22value%22%3A%224.7%22%7D%7D%2C%225%22%3A%7B%22attrs%22%3A%7B%22value%22%3A%22https%3A%2F%2Fimg.alicdn.com%2Fi1%2F2206588314948%2FO1CN01PzbCZK1mQEivhe7Jf_%21%212206588314948-0-C2M.jpg%22%7D%7D%2C%228%22%3A%7B%22attrs%22%3A%7B%22value%22%3A%22%E6%B7%98%E5%AE%9D%E7%B2%BE%E9%80%89%E5%A5%BD%E7%89%A9%E6%8E%A8%E8%8D%90%22%7D%7D%7D&channel=114&");
        urls.add("https://adsfs.heytapimage.com/ads-material-depot/image/a86921e9f62960ca401984befb7a3ae6.jpg");
        urls.add("https://qh-material.taobao.com/dsp/img/mieyd5ssxhdtamrsdxfktmfpkbzkc3d6.jpg?x-oss-process=image/resize,w_720,h_1280,limit_0,m_fill&");
        urls.add("http://lf26-sf-be-pack-sign.pglstatp-toutiao.com/ad.union.api/945144748e5a7bcd4dfcb41c54e0568d?lk3s=d16ba171&x-expires=1942070400&x-signature=cs%2B677SpI%2F%2BqCTLBTkGV6yQfTVw%3D");
        urls.add("http://lf3-sf-be-pack-sign.pglstatp-toutiao.com/ad.union.api/90e678daca2128eccb6208182d1204d0?lk3s=d16ba171&x-expires=1942070400&x-signature=mfo5C0pN2aANnW%2FOLjLfEHGX2c4%3D");
        urls.add("https://img-x.jd.com/1280x720_s3.jpg?d=26&1=jfs/t1/229845/34/25154/97684/66f26b15F465b6f43/7017e47f87cbec35.jpg&2=jfs/t1/241052/28/7253/88319/6645a4b1F914ea0ae/9df4ac3b6403492d.jpg");
        urls.add("https://adsfs.heytapimage.com/ads-material-depot/image/75712b84c227f0ff0d26385cf155c3fe.jpg");
        urls.add("http://lf9-sf-be-pack-sign.pglstatp-toutiao.com/ad.union.api/3949c94b1031a0b58ad87655dded1502?lk3s=d16ba171&x-expires=1942070400&x-signature=zPC0%2BXqjmpkfng6D4fATvhnHxn0%3D");
        urls.add("https://qh-material.taobao.com/dsp/img/bpf2wnknmfyyrqxxntamcnrqpncnjas8.jpg?x-oss-process=image/resize,w_720,h_1280,limit_0,m_fill&");
        urls.add("https://adsfs.heytapimage.com/ads-material-depot/image/71eccb423c23bc80bbf80676658681b1.jpg");
        urls.add("https://ilce.alicdn.com/minolta/475403/1/23d0e766d7c1d722edcbc3087b9fdd59.jpg80?content=%7B%2214%22%3A%7B%22attrs%22%3A%7B%22value%22%3A%225.9%22%7D%7D%2C%225%22%3A%7B%22attrs%22%3A%7B%22value%22%3A%22https%3A%2F%2Fimg.alicdn.com%2Fi1%2F2930255252%2FO1CN01gBhAo71ofT441e9Cd_%21%210-item_pic.jpg%22%7D%7D%2C%228%22%3A%7B%22attrs%22%3A%7B%22value%22%3A%22%E6%BC%AB%E8%8A%B1%E6%82%AC%E6%8C%82%E5%BC%8F%E7%BA%B8%E5%B7%BE%22%7D%7D%7D&channel=114&");
        urls.add("https://adsfs.heytapimage.com/ads-material-depot/image/74f5e1df786db3ff53e937a6c068bb7d.png");
        urls.add("http://lf6-sf-be-pack-sign.pglstatp-toutiao.com/ad.union.api/8015ed90b67bbc627f25039b6b2f6196?lk3s=d16ba171&x-expires=1942070400&x-signature=wO3F1Tvu%2FIlVdYhfyrZn588z5bc%3D");
        urls.add("https://p16.a.kwimgs.com/bs2/adUnionVideo/e4f479dfeb7d4923a17f88c6edfa469a.jpg?ocid=100000354&sdktyv=v1");
        urls.add("https://adsfs.heytapimage.com/ads-material-depot/image/74f5e1df786db3ff53e937a6c068bb7d.png");
        urls.add("https://qh-material.taobao.com/dsp/img/wcdde8apqatctzmwf5hib5n7zyyebzbc.jpg?x-oss-process=image/resize,w_720,h_1280,limit_0,m_fill&");
        urls.add("https://qh-material.taobao.com/dsp/img/kychfd4enaczwk34zmfsdrcnzbeeqrkw.jpg?x-oss-process=image/resize,w_720,h_1280,limit_0,m_fill&");
        urls.add("https://images.pinduoduo.com/marketing_api/2023-04-12/330eff56-050f-42fe-a62c-fa6aafd9952b.jpeg?imageMogr2/thumbnail/720x1280!");
        urls.add("http://lf6-sf-be-pack-sign.pglstatp-toutiao.com/ad.union.api/6856203cebe5d83404b1ced0e7140957?lk3s=d16ba171&x-expires=1942070400&x-signature=wb2hknl2PRqiJV56yyfTA3%2FVFfA%3D");
        urls.add("https://img1.360buyimg.com/pop/jfs/t1/240599/27/18403/79326/66ecf9c6F437c09dd/18b3f8a29e02d01a.jpg");
        urls.add("https://qh-material.taobao.com/dsp/img/fyndknk42b3xbx4xhwbsp38kshnj8dg6.jpg?x-oss-process=image/resize,w_720,h_1280,limit_0,m_fill&");
        urls.add("http://lf6-sf-be-pack-sign.pglstatp-toutiao.com/ad.union.api/e4fac54bcc1099f028b6cd965923273c?lk3s=d16ba171&x-expires=1942070400&x-signature=WTGvAJ36WHloVHtyOg1sEDzrLrE%3D");
        urls.add("https://adsfs.heytapimage.com/ads-material-depot/image/7100b7f3c0fbf5807065a15f47966700.jpg");
        urls.add("http://lf3-sf-be-pack-sign.pglstatp-toutiao.com/ad.union.api/d9227d1b0e84c7821b7e83f5e41fa0f6?lk3s=d16ba171&x-expires=1942070400&x-signature=l%2FtZnTo%2F6LNl4LGoH1iQwd31yRQ%3D");
        urls.add("https://images.pinduoduo.com/marketing_api/2024-08-23/2b6c7ed0-10e0-11ef-9f54-0a580a4d35ed.jpg?imageMogr2/thumbnail/720x1280!");
        urls.add("https://qh-material.taobao.com/dsp/aigc/merge/7be08a8553561c257b9663cc5f8f6fed.png?x-oss-process=image/resize,w_720,h_1280,limit_0,m_fill&");
        urls.add("https://p0.meituan.net/bizad/giga_14143160_663a89edb2bf721d1e342f59a457db85.jpg");
        urls.add("https://images.pinduoduo.com/marketing_api/2024-07-20/96bf551e-fa0f-11ee-b332-0a580a4e5d1b.jpg?imageMogr2/thumbnail/720x1280!");
        urls.add("https://img-x.jd.com/1280x720_s3.jpg?d=26&1=jfs/t1/175377/19/47184/78716/66ece9efFd4a6271c/99b675b1543249cd.jpg&2=jfs/t1/110208/22/50891/84752/66e3eeb0Fc385cb46/75f6a7deb6f11173.jpg");
        urls.add("http://lf3-sf-be-pack-sign.pglstatp-toutiao.com/ad.union.api/4899405d1843c8d4831be289d5ab9d50?lk3s=d16ba171&x-expires=1942070400&x-signature=LLxzu9VqoSwv9QuEa%2FzcIR%2BTqAQ%3D");
        urls.add("https://ilce.alicdn.com/minolta/475403/1/7f3addbe7798774647d27591658c032f.jpg80?content=%7B%2214%22%3A%7B%22attrs%22%3A%7B%22value%22%3A%222.2%22%7D%7D%2C%225%22%3A%7B%22attrs%22%3A%7B%22value%22%3A%22https%3A%2F%2Fimg.alicdn.com%2Fi1%2F4230975401%2FO1CN01BVVsaU1pli26Jsvbd_%21%214230975401.jpg%22%7D%7D%2C%228%22%3A%7B%22attrs%22%3A%7B%22value%22%3A%22%E8%8A%B3%E9%A6%99%E6%A8%9F%E8%84%91%E4%B8%B8%E8%A1%A3%E6%9F%9C%22%7D%7D%7D&channel=114&");
        urls.add("https://p2-lm.adukwai.com/bs2/adUnionVideo/ca5b5acadb874b4c8fff8872e802b993.jpg");
        urls.add("https://images.pinduoduo.com/marketing_api/2023-04-12/330eff56-050f-42fe-a62c-fa6aafd9952b.jpeg?imageMogr2/thumbnail/720x1280!");
        urls.add("https://p2-lm.adukwai.com/bs2/adUnionVideo/fbadeb1822264d9b90c5b1fe028a7dba.jpg");
        urls.add("https://p0.meituan.net/bizad/f787f825e262024bf37add5d4e32f9b3.jpg");
        urls.add("http://lf26-sf-be-pack-sign.pglstatp-toutiao.com/ad.union.api/71f4b6cb391887f4b61766f47ecf2b23?lk3s=d16ba171&x-expires=1942070400&x-signature=sp8QhQJaABUygPCxcgUUiGthrSQ%3D");
        urls.add("https://images.pinduoduo.com/marketing_api/2024-02-29/e3610b84-c327-11ee-9675-0a580a4d4990.jpg?imageMogr2/thumbnail/720x1280!");
        urls.add("https://qh-material.taobao.com/dsp/img/mfmexxzfaczfawbf8aewjmshirntmzys.jpg?x-oss-process=image/resize,w_720,h_1280,limit_0,m_fill&");
        urls.add("https://qh-material.taobao.com/dsp/img/ajtekjznd3y666ymej5aegc7cw4cpe5h.jpg?x-oss-process=image/resize,w_720,h_1280,limit_0,m_fill&");
        urls.add("http://lf9-sf-be-pack-sign.pglstatp-toutiao.com/ad.union.api/ce9217af21f2a20841f8c9a8a79271c1?lk3s=d16ba171&x-expires=1942070400&x-signature=MZKmoTC6%2B2MWrK94CfoAYff8fPM%3D");
        urls.add("http://lf26-sf-be-pack-sign.pglstatp-toutiao.com/ad.union.api/44474c07d82e317bb48c0591cd7bb242?lk3s=d16ba171&x-expires=1942070400&x-signature=DlfDp7Ht2zpK2XQzEuesg5MbMZA%3D");
        urls.add("https://p2-lm.adukwai.com/bs2/adUnionVideo/67135d4c418f47f7a3258d7a2175eeb9.jpg");
        urls.add("https://feed-image.baidu.com/kp/a3bc897a17ff50963085643d653ec8ea.png");
        urls.add("https://adsfs.heytapimage.com/ads-material-depot/image/31e5c2a9c3883840074b0555853a130c.png");
        urls.add("http://lf3-sf-be-pack-sign.pglstatp-toutiao.com/ad.union.api/45080671d6d2ae59e83cca22178d0268?lk3s=d16ba171&x-expires=1942070400&x-signature=SdcvpRyKZ0BUUcPo4UuktkOSfuU%3D");
        urls.add("https://images.pinduoduo.com/marketing_api/2024-08-19/e078ce5e-594d-11ef-97ce-0a580a4b500b.png");
        urls.add("https://qh-material.taobao.com/dsp/img/p5fmczkq5xzhjsz23wb5hkynczi5jr65.jpg?x-oss-process=image/resize,w_720,h_1280,limit_0,m_fill&");
        urls.add("https://adsfs.heytapimage.com/ads-material-depot/image/e5480afd62feae4264704716a3f3b0e0.jpg");
        urls.add("http://lf9-sf-be-pack-sign.pglstatp-toutiao.com/ad.union.api/e4fac54bcc1099f028b6cd965923273c?lk3s=d16ba171&x-expires=1942070400&x-signature=%2BJk4E%2BFzKZOWRU%2Ff%2BdgN5W%2FSdGk%3D");
        urls.add("http://lf6-sf-be-pack-sign.pglstatp-toutiao.com/ad.union.api/a6c4eb374a78ffecef9c5773ff893f89?lk3s=d16ba171&x-expires=1942070400&x-signature=0wRfpFpOG2qP9gd1cD7Dp%2B3H6yw%3D");
        urls.add("https://adsfs.heytapimage.com/ads-material-depot/image/e2938b832391d2b3f6ee06fb6c6ceaee.jpg");
        urls.add("https://img1.360buyimg.com/pop/jfs/t1/185517/27/48012/92595/66ecf91fFa30f4368/dcc4e17d710bd4a8.jpg");
        urls.add("https://qh-material.taobao.com/dsp/img/rstfertfbyxrywakazxsmzm4tm4sa6nk.jpg?x-oss-process=image/resize,w_720,h_1280,limit_0,m_fill&");
        urls.add("https://img1.360buyimg.com/pop/jfs/t1/102326/7/52311/132972/66f4c4acF84ea38dc/46143d7d44b83158.jpg");
        urls.add("https://adsfs.heytapimage.com/ads-material-depot/image/032af89ee84d3aa013e81310a4b0d297.jpg");
        urls.add("https://p1.meituan.net/bizad/giga_8152471_7987f7db478c591b6de78e5ecb1c5e19.jpg");
        urls.add("https://qh-material.taobao.com/dsp/img/r7ybfh7pwxsbz4yezrrrp3mheecqp4ec.jpg?x-oss-process=image/resize,w_720,h_1280,limit_0,m_fill&");
        urls.add("https://qh-material.taobao.com/dsp/img/j2fsmfremjpwzc2zfjchsdcs2n3tpfqy.jpg?x-oss-process=image/resize,w_720,h_1280,limit_0,m_fill&");
        urls.add("http://lf3-sf-be-pack-sign.pglstatp-toutiao.com/ad.union.api/a95178c47bb88af9c2e68e15f8896bed?lk3s=d16ba171&x-expires=1942070400&x-signature=156Bj8HM%2FsaVGs5vYlnrZBjoVbI%3D");
        urls.add("https://images.pinduoduo.com/marketing_api/2024-08-28/cbb1f92a-6427-11ef-bb50-0a580a4ce5f8.png");
        urls.add("http://lf26-sf-be-pack-sign.pglstatp-toutiao.com/ad.union.api/7899f20ef15a0818f4ccec6f6731e0da?lk3s=d16ba171&x-expires=1942070400&x-signature=R765Qg8vUoBcHvC24t5kKkKYUwo%3D");
        urls.add("https://p2-lm.adukwai.com/bs2/adUnionVideo/9da7c67c7b9844aa9db083940fecf4cc.jpg");
        urls.add("https://adsfs.heytapimage.com/ads-material-depot/image/870a15908d24890c770c34c77d375e09.jpg");
        urls.add("https://p1.meituan.net/bizad/e69082e8e867b850da40f4d7e5addc0c.jpg");
        urls.add("https://p1.meituan.net/waimaiad/5683ce044b4a437512fca27abb814308126657.jpg");
        urls.add("https://qh-material.taobao.com/dsp/img/ajtekjznd3y666ymej5aegc7cw4cpe5h.jpg?x-oss-process=image/resize,w_720,h_1280,limit_0,m_fill&");
        urls.add("http://lf3-sf-be-pack-sign.pglstatp-toutiao.com/ad.union.api/da012909850e3f26f5686447980f99c0?lk3s=d16ba171&x-expires=1942070400&x-signature=hynWFe3%2BeohWfTW8vqpLH5qNAak%3D");
        urls.add("https://adsfs.heytapimage.com/ads-material-depot/image/d5d5b51a81ee58d30dda638d44a231b0.jpg");
        urls.add("https://p2-lm.adukwai.com/udata/pkg/aW1hZ2UtcGtnOmFkX2RzcF9pbWFnZV91cGxvYWQ6NDM2MzkyMDU2ODpNRVJDSEFOVDpbQkA0MGYzZTI4MzozOTc0ODY2Mzg5NTY4.jpg");
        urls.add("http://lf6-sf-be-pack-sign.pglstatp-toutiao.com/ad.union.api/0fea29a0332724594e587cb6f8c27370?lk3s=d16ba171&x-expires=1942070400&x-signature=YPvXThW826vSshPkhoicpY6tf88%3D");
        urls.add("https://img-x.jd.com/1280x720_s3.jpg?d=26&1=jfs/t1/87793/11/48167/101603/66ecf08cF62a01b55/0918f6df7177f480.jpg&2=jfs/t1/185589/7/44438/70468/662609fdF73d5d0ab/7252dbbecfa7fe30.jpg");
        urls.add("https://adsfs.heytapimage.com/ads-material-depot/image/1d90ccd522b89e7fe3f2524e676d55cb.jpg");
        urls.add("http://lf3-sf-be-pack-sign.pglstatp-toutiao.com/ad.union.api/2ee6e87d1847a03d0aa5b241c7f44254?lk3s=d16ba171&x-expires=1942070400&x-signature=7a%2F6eHSIFQat3rA1aHxqL%2BggLVQ%3D");
        urls.add("https://adsfs.heytapimage.com/ads-material-depot/image/5a779810375aaaaa0311f0ca47d5f6a7.jpg");
        urls.add("https://adsfs.heytapimage.com/ads-material-depot/image/234b43dd33d8f034f4088881dd050998.jpg");
        urls.add("https://qh-material.taobao.com/dsp/img/anicyrzpdebrcrqe447xfeqj8dxsy25w.jpg?x-oss-process=image/resize,w_720,h_1280,limit_0,m_fill&");
        urls.add("https://qh-material.taobao.com/dsp/img/amhkmdwbwab6is3th6p4zzbq3zak6zeb.jpg?x-oss-process=image/resize,w_720,h_1280,limit_0,m_fill&");
        urls.add("http://lf9-sf-be-pack-sign.pglstatp-toutiao.com/ad.union.api/7c247cf70bc63ec0c8592463873f1f99?lk3s=d16ba171&x-expires=1942070400&x-signature=HSkMaGUYKCrUgbpBQZekfgRn7LY%3D");
        urls.add("https://p2-lm.adukwai.com/bs2/adUnionVideo/f8e1646fda9c4eb2ac84ec71b9edb7b6.jpg");
        urls.add("https://adsfs.heytapimage.com/ads-material-depot/image/3e3c74f576c4de5582fbe2762b081085.jpg");
        urls.add("https://qh-material.taobao.com/dsp/img/fjwhdc3kwweejrpx3deryejmgj4e4ahk.jpg?x-oss-process=image/resize,w_720,h_1280,limit_0,m_fill&");
        urls.add("https://adsfs.heytapimage.com/ads-material-depot/image/c51c41bd6f96b9c340f3207fce7d9ed7.jpg?region=cn-north-1&x-ocs-process=image/crop,x_0,y_0,w_1080,h_1512");
        urls.add("https://adsfs.heytapimage.com/ads-material-depot/image/74f5e1df786db3ff53e937a6c068bb7d.png");
        urls.add("https://store.heytapimage.com/img/202409/18/4d0c2cabe13b75b726670c7d3e5309dc.png");
        urls.add("https://adsfs.heytapimage.com/ads-material-depot/image/31e5c2a9c3883840074b0555853a130c.png");
        urls.add("https://ilce.alicdn.com/minolta/469683/3/4968e97c2b21240d3ba113b00543a173.jpg80?content=%7B%2213%22%3A%7B%22attrs%22%3A%7B%22value%22%3A%22%E6%B7%98%E5%AE%9D%E7%B2%BE%E9%80%89%E5%A5%BD%E7%89%A9%22%7D%7D%2C%225%22%3A%7B%22attrs%22%3A%7B%22value%22%3A%22https%3A%2F%2Fimg.alicdn.com%2Fi3%2F2206567351047%2FO1CN01lGGWA51JbZeop9K1k_%21%212206567351047-2-C2M.png%22%7D%7D%2C%228%22%3A%7B%22attrs%22%3A%7B%22value%22%3A%227.0%22%7D%7D%7D&channel=114&");
        urls.add("https://adsfs.heytapimage.com/ads-material-depot/image/2cb3550b68c0d5a74b851b00a44a1bc4.jpg");
        urls.add("https://images.pinduoduo.com/marketing_api/2022-12-31/5e595a4c-35c9-4ff2-9033-021292ce5565.jpeg?imageMogr2/thumbnail/720x1280!");
        urls.add("https://images.pinduoduo.com/marketing_api/2024-05-03/56a1509c-342e-11ee-8b75-0a580a4e5e1a.jpg?imageMogr2/thumbnail/720x1280!");
        urls.add("https://adsfs.heytapimage.com/ads-material-depot/image/74f5e1df786db3ff53e937a6c068bb7d.png");
        urls.add("https://adsfs.heytapimage.com/ads-material-depot/image/0f49ccbae738a4231c06aa51d263f874.jpg");
        urls.add("https://store.heytapimage.com/img/202408/19/b9425b8807b0eed87bdbeb92030736d3.png");
        //生成html页面 要求将urls生成缩略图的页面，一行放10个图片
        String html = "";
        for (int i = 0; i < urls.size(); i++) {
            if (i % 10 == 0) {
                html += "<div>";
            }
            html += "<img src='" + urls.get(i) + "' width='200' height='400'>";
            if (i % 10 == 9) {
                html += "</div>";
            }
        }


        System.out.println(html);

    }

    @Test
    public void test29() {
        ITesseract instance = new Tesseract();
        instance.setDatapath("C:\\Users\\wayio\\Desktop\\tessdata");

        instance.setLanguage("chi_sim");
        try {
            String result;
            result = instance.doOCR(new File("C:\\Users\\wayio\\Desktop\\creative.png"));
            System.out.println(result);
        } catch (TesseractException e) {
            System.err.println(e.getMessage());
        }
    }

    @Test
    public void test30() {
        System.out.println(RandomUtil.randomBoolean());
        System.out.println(RandomUtil.randomBoolean());
        System.out.println(RandomUtil.randomBoolean());
        System.out.println(RandomUtil.randomBoolean());
        System.out.println(RandomUtil.randomBoolean());
        System.out.println(RandomUtil.randomBoolean());
        System.out.println(RandomUtil.randomBoolean());
        System.out.println(RandomUtil.randomBoolean());
        System.out.println(RandomUtil.randomBoolean());
        System.out.println(RandomUtil.randomBoolean());
    }

    @Test
    public void test31() {
        List<Student> list = new ArrayList<>();
        list.add(new Student().setName("1"));
        list.add(new Student().setName("2"));
        list.add(new Student().setName("31"));
        list.add(new Student().setName("4"));
        list.add(new Student().setName("5"));
        list.add(new Student().setName("6"));

        //查询到name为3的;
        if (list.stream().anyMatch(i -> i.getName().equals("3"))) {
            System.out.println("存在");
            //打印第一个
            System.out.println(list.stream().filter(i -> i.getName().equals("3")).findFirst().get());
        }

    }

    @Test
    public void test32() throws UnsupportedEncodingException {
        System.out.println(URLEncoder.encode("中 文", "UTF-8"));
        System.out.println(URLEncoder.encode("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/130.0.0.0 Safari/537.36", "UTF-8"));
//        System.out.println(.encode("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/130.0.0.0 Safari/537.36", "UTF-8"));
    }

    @Test
    public void test33() {
        String str = "12";
        String str1 = "12345678";
        //处理字符串 不足5位后面补空格
        System.out.println(StringUtils.rightPad(str, 5, " "));
        System.out.println(StringUtils.rightPad(str1, 5, " "));
    }

    @Test
    public void test34() {
        //读取两个csv文件，将所有行的内容放进一个list中，取两个list中的交集
        List<String> list1 = FileUtil.readUtf8Lines("C:\\Users\\Administrator\\Desktop\\1.csv");
        List<String> list2 = FileUtil.readUtf8Lines("C:\\Users\\Administrator\\Desktop\\2.csv");
        list1.retainAll(list2);
        System.out.println(list1);
    }

    @Test
    public void test35() throws InterruptedException {
        long start = System.nanoTime();
        Thread.sleep(100L);
        long end = System.nanoTime();
        System.out.println((end - start) / 1_000_000.0);
    }

    @Test
    public void test36() {
        String property = "";
        System.out.println((1081 & 0x400) != 0);
        System.out.println((1107 & 0x400) != 0);

        System.out.println((83 & 0x400) != 0);
        System.out.println((27 & 0x400) != 0);
    }

    @Test
    public void test37() {
        int num = 10;
        long start = System.currentTimeMillis();
        CountDownLatch countDownLatch = new CountDownLatch(5);
        ExecutorService executor = Executors.newFixedThreadPool(5);
        List<String> futures = new ArrayList<>();
        // 执行耗时操作
        for (int i = 1; i <= num; i++) {
            int j = i;
            Future<String> future = executor.submit(() -> {

                try {
                    sleep(j);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return "Task completed";
            });
            String res;
            try {
                res = future.get(1500, TimeUnit.MILLISECONDS);
            } catch (TimeoutException | InterruptedException | ExecutionException e) {
                //log.error("error:{}", e.getMessage());
                res = "timeout!";
            }
            futures.add(res);
            countDownLatch.countDown();

        }

        String result = futures.toString();
        log.info(result);
        executor.shutdown(); // 关闭线程池


        long end = System.currentTimeMillis();
        log.info("总耗时：{}", end - start);

    }

    @Test
    public void test38() throws InterruptedException {
        //123W条数据，一个线程执行1W条， 10个线程循环执行
        //总数据个数
        int num = 123_6789;
        //线程数
        int threadNum = 10;
        //每个线程执行的任务数
        int perThreadNum = 10000;
        //批次数
        int batch = (num % (threadNum * perThreadNum)) != 0 ? (num / (threadNum * perThreadNum) + 1) : (num / (threadNum * perThreadNum));

        long start = System.currentTimeMillis();

        //分批次执行 使用CountDownLatch的问题：需要将数据一次性取出，可能会内存溢出
        //优化：while读取，每读取出10W条数据后即进行批处理，开启10个线程，每个线程执行1W条数据。批次循环执行。
        /*log.info("批次数：{}", batch);
        for (int i = 0; i < batch; i++) {
            log.info("批次：{}", i + 1);
            int startIndex = i * threadNum * perThreadNum;
            int endIndex = (i + 1) * threadNum * perThreadNum;
            if (endIndex > num) {
                endIndex = num;
            }
            CountDownLatch countDownLatch = new CountDownLatch(threadNum);
            for (int j = 0; j < threadNum; j++) {
                int subStartIndex = startIndex + j * perThreadNum;
                //结束索引，如果最后一个线程的subEndIndex>num，则结束索引为num，否则为subStartIndex + perThreadNum
                int subEndIndex = subStartIndex + perThreadNum;
                if (subEndIndex > num) {
                    subEndIndex = num;
                }
                int _subEndIndex = subEndIndex;
                new Thread(() -> {
                    try {
                        if (subStartIndex < num) {
                            //执行业务
                            sleep(subStartIndex, _subEndIndex);
                        }
                    } finally {
                        countDownLatch.countDown();
                    }
                }).start();
            }
            //await()会阻塞到countDownLatch.countDown()为0 才会往下走
            countDownLatch.await();
            log.info("批次：{},{}~{}，结束", i + 1, startIndex, endIndex);
        }*/


        //创建10个线程执行任务，主线程等待所有子线程执行完成后才继续执行
        CountDownLatch countDownLatch = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            new Thread(() -> {
                try {
                    sleep(finalI);
                } finally {
                    countDownLatch.countDown(); //子线程执行完成后，计数器减1
                }
            }).start();
        }
        //await()会阻塞到countDownLatch.countDown()为0 才会往下走
        countDownLatch.await();
        long end = System.currentTimeMillis();
        log.info("总耗时：{}", end - start);

    }

    @Test
    public void test39() throws InterruptedException {
        //读取大文件假设有123万条数据，每读取10万条， 则创建10个线程，每个线程将1万条数据放入redis中。直到读取完所有数据


    }

    //模拟耗时
    private static void sleep(int num) {
        try {
            log.info("当前线程：{}，num：{}, 开始执行", Thread.currentThread().getName(), num);
            Thread.sleep(600L);
            log.info("当前线程：{}，num：{}, 结束执行", Thread.currentThread().getName(), num);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void sleep(int startIndex, int endIndex) {
        long start = System.currentTimeMillis();
        try {
//            log.info("当前线程：{}，{}~{},开始执行", Thread.currentThread().getName(), startIndex , endIndex);
            Thread.sleep((long) (0.06 * (endIndex - startIndex)));
            log.info("当前线程：{}，{}~{},结束执行,耗时：{}", Thread.currentThread().getName(), startIndex, endIndex, System.currentTimeMillis() - start);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test40() {
        BigInteger a = new BigInteger("0");
        BigInteger b = BigInteger.ZERO;
        System.out.println(b.compareTo(a));
    }

    @Test
    public void test41() {
        //对比两个map
        Map<String, Object> map1 = new HashMap<>();
        map1.put("id", "1");
        map1.put("age", "18");
        Map<String, Object> map11 = new HashMap<>();
        map11.put("name-1", "zhang");
        map1.put("name", map11);
        System.out.println(map1);
        Map<String, Object> map2 = new HashMap<>();
        Map<String, Object> map22 = new HashMap<>();
        map2.put("id", "2");
        map22.put("name-1", "wang");
        map2.put("name", map22);
        map2.put("remark", "asdf");
        System.out.println(map2);
        JSONObject jsonObject1 = JSONObject.parseObject(JSON.toJSONString(map1));
        JSONObject jsonObject2 = JSONObject.parseObject(JSON.toJSONString(map2));
        List<JSONObject> diffList = new ArrayList<>();
        //递归找出两个json不同的部分 输出key以及value
        findDiff(diffList, jsonObject1, jsonObject2);
        System.out.println(JSON.toJSONString(diffList, true));
    }

    /**
     * 查找两个JSON对象之间的差异
     *
     * @param diffList 用于存储差异结果的JSON对象
     * @param oldJson  旧的JSON对象
     * @param newJson  新的JSON对象
     */
    public static void findDiff(List<JSONObject> diffList, JSONObject oldJson, JSONObject newJson) {
        if (oldJson == null || newJson == null) {
            throw new IllegalArgumentException("Input JSON objects cannot be null");
        }
        if (diffList == null) {
            diffList = new ArrayList<>();
        }
        Set<String> keys1 = new HashSet<>(oldJson.keySet());
        Set<String> keys2 = new HashSet<>(newJson.keySet());
        findDiffRecursive(diffList, oldJson, newJson, keys1, keys2, 0);
    }

    /**
     * 递归查找两个JSON对象之间的差异
     *
     * @param diffList 用于存储差异结果的JSON对象
     * @param oldJson  旧的JSON对象
     * @param newJson  新的JSON对象
     * @param keys1    旧JSON对象的键集合
     * @param keys2    新JSON对象的键集合
     * @param depth    当前递归的深度
     */
    private static void findDiffRecursive(List<JSONObject> diffList, JSONObject oldJson, JSONObject newJson, Set<String> keys1, Set<String> keys2, int depth) {
        //递归深度不能超过10
        if (depth > 10) {
            throw new RuntimeException("Maximum recursion depth exceeded 10");
        }
        for (String key : keys1) {
            if (!keys2.contains(key)) {
                Map<String, Object> diff = new HashMap<>();
                diff.put("old", oldJson.get(key));
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(key, diff);
                diffList.add(jsonObject);
            } else {
                Object value1 = oldJson.get(key);
                Object value2 = newJson.get(key);
                if (value1 == null && value2 == null) {
                    continue;
                }
                if (value1 instanceof JSONObject && value2 instanceof JSONObject) {
                    findDiffRecursive(diffList, (JSONObject) value1, (JSONObject) value2, ((JSONObject) value1).keySet(), ((JSONObject) value2).keySet(), depth + 1);
                } else if (!Objects.equals(value1, value2)) {
                    Map<String, Object> diff = new HashMap<>();
                    diff.put("old", value1);
                    diff.put("new", value2);
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put(key, diff);
                    diffList.add(jsonObject);
                }
            }
        }
        for (String key : keys2) {
            if (!keys1.contains(key)) {
                Map<String, Object> diff = new HashMap<>();
                diff.put("new", newJson.get(key));
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(key, diff);
                diffList.add(jsonObject);
            }
        }
    }


    @Test
    public void test42() {
        List<Map<String, Object>> sourceList = new ArrayList<>();
        HashMap<String, Object> mapVal = Maps.newHashMap();
        mapVal.put("id", "1");
        sourceList.add(mapVal);
        List<Map<String, Object>> targetList = new ArrayList<>();
        HashMap<String, Object> mapVal2 = Maps.newHashMap();
        mapVal2.put("id", "1");
        mapVal2.put("name", "zhang");
        HashMap<String, Object> mapVal3 = Maps.newHashMap();
        mapVal3.put("id", "1");
        mapVal3.put("name", "zhang");
        HashMap<String, Object> mapVal4 = Maps.newHashMap();
        mapVal4.put("id", "1");
        mapVal4.put("name", "wang");
        HashMap<String, Object> mapVal5 = Maps.newHashMap();
        mapVal5.put("id", "1");
        mapVal5.put("name", "li");
        targetList.add(mapVal2);
        targetList.add(mapVal3);
        targetList.add(mapVal4);
        targetList.add(mapVal5);
        targetList.add(new HashMap<>());
        System.out.println(sourceList);
        System.out.println(targetList);


        System.out.println(sourceList);
        System.out.println(targetList);

    }

    @Test
    public void test43() {
        //md5
//        System.out.println(DigestUtils.md5Hex("123456qwe"));
//        System.out.println(DigestUtils.md5Hex("123456QWE"));
        //随机生成区分大小写密钥
        String key = RandomStringUtils.randomAlphanumeric(6);
        System.out.println(key);
        //将key的中间几位替换为*
        key = key.substring(0, 3) + "**************" + key.substring(17);
        System.out.println(key);
    }

    @Test
    public void test44() {
        String uuid = "1234";
        Algorithm algorithm = Algorithm.HMAC256("secret");

        JWTCreator.Builder builder = JWT.create().withClaim("uuid", uuid).withClaim("name", "zhang");

        String token = builder.sign(algorithm);
        System.out.println(token);
        //
    }

    @Test
    public void test45() {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoiemhhbmciLCJ1dWlkIjoiMTIzNCJ9.igyZ0acVK9lFTls_Ccr4TJCcvAjTw-1LCI50czhnxyY";
        DecodedJWT decodedJWT = JWT.decode(token);
        System.out.println(decodedJWT.getClaim("uuid").asString());
        System.out.println(decodedJWT.getClaim("name").asString());
        System.out.println(decodedJWT.getClaims());
    }

    @Test
    public void test46() {
        try {
            Mac hmac_SHA256 = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec("123456".getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            hmac_SHA256.init(secret_key);
            byte[] hash = hmac_SHA256.doFinal("userName_zhangsan".getBytes(StandardCharsets.UTF_8));
            System.out.println(bytesToHex(hash));
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Exception while encrypting data with HMAC-SHA256", e);
        }
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02x", b & 0xff));
        }
        return builder.toString();
    }

    /**
     * 递归替换xss
     *
     * @param json 用于存储差异结果的JSON对象
     */
    private static void replaceXss(JSONObject json, int depth) {
        //递归深度不能超过10
        if (depth > 10) {
            return;
        }
        for (Map.Entry<String, Object> entry : json.entrySet()) {
            Object value = entry.getValue();
            String key = entry.getKey();
            if (value instanceof JSONObject) {
                replaceXss((JSONObject) value, depth + 1);
            } else if (value instanceof JSONArray) {
                for (Object item : (JSONArray) value) {
                    if (item instanceof JSONObject) {
                        replaceXss((JSONObject) item, depth + 1);
                    }
                }
            } else if (value instanceof String) {
                value = HtmlUtils.htmlEscape(value.toString());
                json.put(key, value);
            }

        }
    }

    @Test
    public void test47() {
        String str = "123456<script>alert('123')</script>";
        JSONObject json = new JSONObject();
        JSONObject json1 = new JSONObject();
        json1.put("key<>", str);
        json.put("name", json1);
//        replaceXss(json, 0);

        String a = "123456<script>alert('123')</script>[]()";
        System.out.println(HtmlUtils.htmlEscape(a.toString()));
        System.out.println(HtmlUtils.htmlEscape(".-/"));

        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put(null, "111");
//        JSONObject json2 = JSONObject.parseObject(a);
//        replaceXss(json2, 0);
//        System.out.println(json2.toJSONString());
        //判断是否为json
//        boolean isJson = JSONUtil.isJson(json.toJSONString());
//        System.out.println(isJson);
    }

    @Test
    public void test48() {
        //sql注入
        List<String> keywords = Arrays.asList("add", "select", "count", "asc", "desc", "char", "mid", "'", ":", "\"", "insert", "delete", "drop", "table", "update", "truncate", "from", "%");
        String str = "asdfgh\"";
        for (String keyword : keywords) {
            if (str.toLowerCase().contains(keyword)) {
                System.out.println("存在注入关键字" + keyword);
                return;
            }
        }
    }

    @Test
    public void test49() throws NoSuchAlgorithmException {
        String param = "{\"id\":\"1\",\"name\":\"zhang\",\"pid\":\"2\",\"pname\":\"wang\"}";
        //公钥加密
        //私钥解密


        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        KeyPair pair = keyGen.genKeyPair();
        System.out.println(pair.getPublic());
        System.out.println(pair.getPrivate());
    }

    @Test
    public void test50() {
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now);
        System.out.println(now.plusHours(8));
    }

    /**
     * 递归替换xss
     *
     * @param object 用于存储差异结果的JSON对象
     */
    private static void replaceXss(Object object, int depth) throws IllegalAccessException {
        //递归深度不能超过10
        if (depth > 10) {
            return;
        }
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(object);
                if (value != null) {
                    if (value instanceof String) {
                        field.set(object, HtmlUtils.htmlEscape((String) value));
                    } else if (value instanceof JSONObject) {
                        replaceXss(value, depth + 1);
                    } else if (value instanceof JSONArray) {
                        JSONArray array = (JSONArray) value;
                        for (Object o : array) {
                            replaceXss(o, depth + 1);
                        }
                    } else if (value.getClass().isArray()) {
                        Object[] array = (Object[]) value;
                        for (Object o : array) {
                            replaceXss(o, depth + 1);
                        }
                    } else if (value instanceof Collection) {
                        Collection<?> collection = (Collection<?>) value;
                        for (Object item : collection) {
                            replaceXss(item, depth + 1);
                        }
                    }
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Test
    public void test51() {
        List<com.example.domain.User> userList = new ArrayList<>();
        com.example.domain.User user1 = com.example.domain.User.builder().build();
        com.example.domain.User user2 = com.example.domain.User.builder().build();
//        user1.setSubUsers(new ArrayList<>());
        userList.add(user1);
        SubUser subUser = SubUser.builder().id(111L).build();
        user2.setSubUsers(Collections.singletonList(subUser));
        userList.add(user2);
        // 取出subUser的id
        List<SubUser> subUsers = Collections.emptyList();
        List<Long> ids = userList.stream().map(user -> (user.getSubUsers() == null ? subUsers : user.getSubUsers()).stream().map(SubUser::getId).collect(Collectors.toList())).flatMap(Collection::stream).collect(Collectors.toList());
        System.out.println(ids);

    }

    @Test
    public void test52() {
        //保留1位小数
        float hours = (float) 25200000 / DateUtils.MILLIS_PER_HOUR;
        //保留1位小数
        hours = (float) (Math.floor(hours * 10)) / 10;
        System.out.println(hours);
//        System.out.println(HtmlUtils.htmlEscape("=（）()<>", "UTF-8"));
//        System.out.println(HtmlUtils.htmlUnescape("=（）()&lt;&gt;"));
    }

    @Test
    public void test53() {
        // str的长度不固定 保留str的前三位和后三位 中间进行脱敏
        System.out.println(getDesString("~！@#￥%……&*（）——+"));
        System.out.println(getDesString("~!@#$%^&*()_+"));
        System.out.println(getDesString("s"));
        System.out.println(getDesString("st"));
        System.out.println(getDesString("str"));
        System.out.println(getDesString("str1"));
        System.out.println(getDesString("str12"));
        System.out.println(getDesString("str123"));
        System.out.println(getDesString("str1234"));
        System.out.println(getDesString("str12345"));
        System.out.println(getDesString("str123456"));
        System.out.println(getDesString("str1234567"));
        System.out.println(getDesString("strdhfjkjdsflhasfjdsfhdsjfhsdkfd"));

    }

    /**
     * 对字符串进行脱敏处理
     * 小于4位的 最后1个字符置为*
     * 小于7位的 最后3个字符置为*
     * 大于等于7位的 漏出前3后3 中间字符置为*
     *
     * @param str 一般为10位以上
     * @return str********kfd
     */
    private static String getDesString(String str) {
        if (StringUtils.isBlank(str)) {
            return str;
        }
        int length = str.length();
        if (length < 4) {
            // 最后一个字符置为*
            return str.substring(0, length - 1) + "*";
        } else if (length < 7) {
            // 最后3个字符置为*
            return str.substring(0, length - 3) + "***";
        } else {
            return str.substring(0, 3) +
                    StringUtils.repeat("*", Math.max(0, length - 6)) +
                    str.substring(length - 3);
        }
    }

    @Test
    public void test54() {
        TimeZone timeZone = TimeZone.getDefault();
        System.out.println(timeZone.getID());
        //北京时区
        timeZone = TimeZone.getTimeZone("UTC");
        System.out.println(timeZone.getID());
        //TimeZone 转为 ZoneId
        ZoneId zoneId = timeZone.toZoneId();
        System.out.println(zoneId.getId());
    }

    @Test
    public void test55() {
        //计算今天还剩多少小时 精确到2位小数
        double hours = (double) (DateUtil.between(new Date(), DateUtil.endOfDay(new Date()), DateUnit.MINUTE, true) * 100) / 60 / 100;
        System.out.println(hours);
    }

    @Test
    public void test56() {
        // map list new 初始化数据记录
//        Map<String, String> map3 = new HashMap<String, String>(){{put("aa","bb");put("df", "rt");}};
//        map3.put("aar","bb");
//        System.out.println(map3);

//        List<String> list = new ArrayList<String>(){{add("0");}};
//        list.add("1");
//        list.add("2");
//        list.add("3");
//        list.add("4");
//        System.out.println(list);
//        String []str = new String[0];
//        str = list.toArray(str);
//        System.out.println(str);
        List<Integer> list = new ArrayList<Integer>() {{
            add(0);
        }};
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        System.out.println(list);
        int[] arrays = new int[0];
        //将list中的元素放到arrays中
        arrays = list.stream().mapToInt(Integer::intValue).toArray();
//        System.out.println(arrays);

        List<String> list2 = new LinkedList<>();
        list2.add("钱大");
        list2.add("朱二");
        list2.add("张三");
        list2.add("李四");
        list2.add("王五");
        list2.add("周六");
        list2.add("冯七");
        list2.add("赵八");
        list2.add("是的");
        list2.add("aaaa");
        System.out.println(list2);

        Set<String> set = new HashSet<>();
        set.add("钱大");
        set.add("朱二");
        set.add("张三");
        set.add("李四");
        set.add("王五");
        set.add("周六");
        set.add("冯七");
        set.add("赵八");
        set.add("是的");
        set.add("aaaa");
        System.out.println(set);

        Map<String, String> map = new HashMap<>();
        map.put("钱大", "1");
        map.put("朱二", "2");
        map.put("张三", "3");
        map.put("李四", "4");
        map.put("王五", "5");
        map.put("周六", "6");
        map.put("冯七", "7");
        map.put("赵八", "8");
        map.put("是的", "9");
        map.put("aaa", "10");
        System.out.println(map);

        Map<String, String> map2 = new TreeMap<>();
        map2.put("钱大", "1");
        map2.put("朱二", "2");
        map2.put("张三", "3");
        map2.put("李四", "4");
        map2.put("王五", "5");
        map2.put("周六", "6");
        map2.put("冯七", "7");
        map2.put("赵八", "8");
        map2.put("是的", "9");
        map2.put("aaa", "10");
        System.out.println(map2);

//        System.out.println(Collections.binarySearch(list, "1"));
//        System.out.println(Collections.binarySearch(list, "5"));
//        System.out.println(list.contains(null));
    }

    @Test
    public void test57() {
        int a = 2;
        switch (a) {
            case 1:
                System.out.println("1");
                break;
            case 2:
                System.out.println("2");
            case 3:
                System.out.println("3");
            default:
                System.out.println("default");
        }
    }

    /**
     * 请求腾讯接口获取dealId与displayId的对应关系
     */
    @Test
    public void test58() {
        String url = "https://open.adx.qq.com/adproposal/list";

        Integer dspId = 110344;
        String token = "480338cb4accc5d0cae64d47b2ea0d88";
        String time = System.currentTimeMillis() / 1000 + "";

        JSONObject jsonObject = new JSONObject();

        JSONArray data = new JSONArray();
//        data.add(new JSONObject(){{put("page", 1);}});
//        data.add(new JSONObject(){{put("size", 20);}});
        //pro_code是必填的？？
        data.add(new JSONObject() {{
            put("pro_code", "202412160027");
        }});
        data.add(new JSONObject() {{
            put("pro_code", "202412190023");
        }});
        data.add(new JSONObject() {{
            put("pro_code", "202412240042");
        }});

        jsonObject.put("data", data);
        jsonObject.put("dsp_id", dspId);
        jsonObject.put("token", token);
        jsonObject.put("time", time);
        jsonObject.put("sig", DigestUtils.md5Hex(dspId + token + time));

        //请求url
        String result = OKHttpUtil.post(url, jsonObject.toJSONString(), 60 * 1000);
        JSONObject res = JSONObject.parseObject(result);
        System.out.println(res);
        Map<String, Integer> map = new HashMap<>();
        JSONArray retMessage = res.getJSONArray("ret_msg");
        for (int i = 0; i < retMessage.size(); i++) {
            JSONObject jsonObject1 = retMessage.getJSONObject(i);
            if (jsonObject1 != null) {
                JSONArray deal_data = jsonObject1.getJSONArray("deal_data");
                if (deal_data != null) {
                    for (Object dealDatum : deal_data) {
                        JSONObject jsonObject2 = (JSONObject) dealDatum;
                        Integer dealId = jsonObject2.getInteger("deal_id");
                        Integer displayId = jsonObject2.getInteger("display_id");
                        map.put(dealId + "", displayId);
                    }
                }
            }

        }
        System.out.println("dealMap:" + JSONObject.toJSONString(map));


    }

    @Test
    public void test59() {
        AtomicInteger i = new AtomicInteger(0);
        AtomicInteger a = new AtomicInteger(7);
        System.out.println(i.get());
        i.getAndIncrement();
        System.out.println(i.get());

        double d = (double) i.get() / a.get();
        System.out.println(d);
    }

    @Test
    public void test60() {
        List<String> list1 = null;
        List<String> list2 = new ArrayList<>(Arrays.asList("1", "2", null));
        List<String> list3 = null;

        //三个list的交集 如果有null的则不参与取交集
        List<String> intersection = getIntersection(list1, list2, list3);
        System.out.println("Intersection: " + intersection);
        System.out.println(list2.contains(null));
    }

    @SafeVarargs
    public static <T> List<T> getIntersection(List<T>... lists) {
        List<List<T>> validLists = Arrays.stream(lists)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        if (validLists.isEmpty()) {
            return Collections.emptyList();
        }

        List<T> result = new ArrayList<>(validLists.get(0));
        for (int i = 1; i < validLists.size(); i++) {
            result.retainAll(validLists.get(i));
        }
        return result;
    }

    /**
     *
     */
    private enum MarketingTargetType {


        ;

        private final String value;
        private final String name;

        MarketingTargetType(String value, String name) {
            this.value = value;
            this.name = name;
        }

    }

    @Test
    public void test61() {
        String str = "年龄：14至64岁；地理位置：（常住、近期、常驻且近期、旅行、去过）西藏自治区、北京市、克达拉市、昆玉市、新疆未知；操作系统版本：Android；联网方式：Wifi、3G、4G、5G；自定义人群：排除京东-一年内已下单-IDFA（ID: 42480196）、京东-一年内已下单-OAID（ID: 42480193）、京东-联盟低活-OAID（ID: 42396079）、京东-联盟低活-OAID-NEW（ID: 41792041）、京东-首购年度-OAID-点击排除包（ID: 39011710）；排除已转化用户：同应用，自定义转化行为：下单；";

        Student student = new Student();
        student.setName("张三");
        Student b = new Student();
//        Optional.ofNullable(student.getName()).map(i-> i + "not null ").ifPresent(b::setName);
        //将str用；进行分割 获取每个元素 每个元素中再用：分割 分别为key和value
        Arrays.stream(str.split("[；，]")).forEach(i -> {
            String[] split = i.split("：");
            if (split.length == 2) {
                String key = split[0];
                String value = split[1];
                b.getDesc().put(key, value);
            } else if (split.length == 1) {
                String key = split[0];
                b.getDesc().put(key, "");
            }
        });
        System.out.println(b);
    }

    @Test
    public void test62() {
        LocalDateTime dateTime = LocalDateTime.now();
        //时间戳
        System.out.println(dateTime.toInstant(ZoneOffset.of("+0")).toEpochMilli());
        System.out.println(dateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli());

        long timestamp = System.currentTimeMillis();
        dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneOffset.of("+8"));
    }

    @Test
    public void test63() {
        List<Student> updateList = new ArrayList<>();
        List<Student> saveList = new ArrayList<>();

        List<Student> listOld = new ArrayList<>();
//        listOld.add(new Student().setId(0).setName("00").setAge(0).setRemark("00000"));
//        listOld.add(new Student().setId(1).setName("张三").setAge(18).setRemark("张三的备注"));
//        listOld.add(new Student().setId(3).setName("王五").setAge(20).setRemark("王五的备注"));
//        listOld.add(new Student().setId(2).setName("李四").setAge(19).setRemark("李四的备注"));
        List<Student> listNew = new ArrayList<>();
        listNew.add(new Student().setId(1).setName("张三").setAge(18).setRemark("张三的备注"));
        listNew.add(new Student().setId(2).setName("李四").setAge(88).setRemark("李四的备注-update"));
        listNew.add(new Student().setId(4).setName("周六").setAge(66).setRemark("周六的备注"));
        listNew.add(new Student().setId(3).setName("王五").setAge(22).setRemark("王五的备注"));
        // listOld和listNew进行对比 如果listNew中有新增的则放到saveList中 如果listNew中有更新的则放到updateList中，更新的依据是各个属性的值是否相同
        listNew.forEach(i -> {
            if (listOld.stream().noneMatch(j -> j.getId().equals(i.getId()))) {
                saveList.add(i);
            } else {
                Student student = listOld.stream().filter(j -> j.getId().equals(i.getId())).findFirst().orElse(null);
                if (isNotSame(i, student)) {
                    updateList.add(i);
                }
            }
        });

        System.out.println(saveList);
        System.out.println(updateList);

    }

    private static boolean isNotSame(Student source, Student target) {

        return !StringUtils.equals(target.getName(), source.getName())
                || !ObjectUtil.equals(target.getAge(), source.getAge())
                || !StringUtils.equals(target.getRemark(), source.getRemark())
                ;
    }

    @Test
    public void test64() throws InterruptedException {
        LocalDateTime now = LocalDateTime.now();
        Thread.sleep(1);
        LocalDateTime now1 = LocalDateTime.now();
        System.out.println(ObjectUtil.equals(now, now1));
        System.out.println(ObjectUtil.equals(223, new Integer(223)));
        System.out.println(NumberUtil.equals(BigDecimal.valueOf(223), null));
    }

    @Test
    public void test65() throws IOException {
//        InputStream is = this.getClass().getResourceAsStream("/application.yml");
//
//        //按行输出
//        BufferedReader br = new BufferedReader(new InputStreamReader(is));
//        while (br.ready()) {
//            System.out.println(br.readLine());
//        }
//        System.out.println();
        Path path = Paths.get("D:\\workspace\\Test\\src\\main\\resources\\application.yml");
        InputStream stream = new BufferedInputStream(Files.newInputStream(path));
        //按行输出
        BufferedReader br = new BufferedReader(new InputStreamReader(stream));
        while (br.ready()) {
            System.out.println(br.readLine());
        }
        //读取远程文件
        try (InputStream inputStream = new URL("http://localhost:8080/application.yml").openStream()) {
            //按行输出
            BufferedReader br1 = new BufferedReader(new InputStreamReader(inputStream));
            while (br1.ready()) {
                System.out.println(br1.readLine());
            }
        }
    }

    @Test
    public void test66() {
        //随机生成以下字符串
        //A2F359ADEFBD76592D53
        //BE2555877CD98CD5BA78
        //DA774CC94437B6BB49B5
        String str = "ABCDEF0123456789";
        for (int i = 0; i < 3; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < 20; j++) {
                int number = new Random().nextInt(16);
                sb.append(str.charAt(number));
            }
            System.out.println(sb);
        }

    }


}
