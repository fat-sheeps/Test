//package com.example;
//import com.example.domain.ip.AsnInfo;
//import com.example.domain.ip.CityInfo;
//import com.maxmind.geoip2.record.City;
//import org.junit.jupiter.api.Test;
//
//public class CityTest {
//    private static City cityDb;
//
//    static {
//        try {
//            cityDb = new City("c:/tiantexin/download/mydata4full.ipdb");
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }
//
//    @Test
//    public void findAsnInfo() {
//        try {
//            CityInfo cityInfo = cityDb.findCity("27.190.250.125", "CN");
//            System.out.println(cityInfo);
//            System.out.println(cityInfo.getDistrictInfo());
//            System.out.println("\n");
//            System.out.println(cityInfo.getRoute());
//            System.out.println("\n");
//            System.out.println(cityInfo.getUsageType());
//            AsnInfo[] infos = cityInfo.getAsnInfos();
//            if (infos != null) {
//                for (int i = 0; i < infos.length; i++) {
//                    AsnInfo info = infos[i];
//                    System.out.println(info.ASN);
//                    System.out.println(info.Registry);
//                    System.out.println(info.Country);
//                    System.out.println(info.NetName);
//                    System.out.println(info.OrgName);
//                }
//                System.out.println(infos.length);
//            }
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }
//
//    @Test
//    public void find() {
//
//        try {
//            CityInfo cityInfo = cityDb.findCity("111.199.81.160", "CN");
//            System.out.println(cityInfo.getDistrictInfo());
//            System.out.println("\n");
//            System.out.println(cityInfo.toString());
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }
//}
