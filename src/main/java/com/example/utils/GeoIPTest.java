package com.example.utils;

import com.alibaba.fastjson.JSON;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.City;
import com.maxmind.geoip2.record.Country;
import com.maxmind.geoip2.record.Location;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.jbig2.util.log.Logger;
import org.apache.pdfbox.jbig2.util.log.LoggerFactory;
import org.springframework.core.io.FileSystemResourceLoader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
public class GeoIPTest {
    private static final String GEO_LITE_CITY_PATH = "C:\\Users\\wayio\\Desktop\\GeoLite2-City.mmdb";
    private static DatabaseReader reader;

    static {
        try {
            InputStream inputStream = new FileSystemResourceLoader().getResource(GEO_LITE_CITY_PATH).getInputStream();
            reader = new DatabaseReader.Builder(inputStream).build();
        } catch (Exception e) {
            log.error("Load reader database error.");
            throw new IllegalArgumentException();
        }
    }

    public static void main(String[] args) {


//        List<String> ips = Arrays.asList("120.244.41.77","120.245.22.49","120.244.61.99","115.171.169.183","223.160.131.141","111.205.232.186","223.72.121.14","61.51.157.118","120.244.218.189","106.121.166.49","123.117.179.181","114.248.127.223","114.240.17.146","117.133.86.234");
        List<String> ips = Arrays.asList("45.116.153.188", "45.116.152.78", "45.116.152.60", "223.160.143.48", "220.205.240.168", "220.205.252.54", "220.205.244.245", "220.205.253.138", "223.160.169.100", "220.205.252.166", "220.205.253.152", "220.205.233.19", "223.160.168.224", "223.160.226.80", "223.160.222.86", "223.160.176.47", "223.160.185.187", "223.160.148.125", "223.160.157.210", "223.160.190.20", "223.160.222.217", "223.160.139.234", "223.160.160.166", "223.160.201.90", "223.160.196.157", "223.160.188.176", "223.160.113.46", "223.160.234.77", "223.160.114.182", "223.160.152.191", "223.160.150.74", "223.160.140.34", "223.160.119.191", "223.160.140.92", "223.160.201.35", "223.160.119.96", "223.160.183.31", "223.160.201.40");


        for (String ip : ips) {
            GeoLocation location = getLocation(ip);
            System.out.println( ip + "：" + JSON.toJSONString(location));
        }
    }

    public static GeoLocation getLocation(String ipAddress) {
        try {
            InetAddress ip = InetAddress.getByName(ipAddress);
            Optional<CityResponse> optional = Optional.ofNullable(reader.city(ip));
            if (optional.isPresent()) {
                CityResponse response = optional.get();
                City city = response.getCity();
                Country country = response.getCountry();
//                Location location = response.getLocation();
                return GeoLocation.builder()
                        .countryIsoCode(country == null ? null : country.getIsoCode())
                        .country((country == null || country.getNames() == null) ? null : country.getNames().get("zh-CN"))
                        .city((city == null || city.getNames() == null) ? null : city.getNames().get("zh-CN"))
//                        .latitude(location.getLatitude())
//                        .longitude(location.getLongitude())
                        .build();
            }
        } catch (Exception e) {
            log.error("Geo ip get location error. ipAddress={}", ipAddress);
        }
        return null;
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class GeoLocation implements Serializable {
        private static final long serialVersionUID = -446831836931739094L;
        private String country;
        private String countryIsoCode;
        private String city;
//        private Double latitude;
//        private Double longitude;
    }
}
