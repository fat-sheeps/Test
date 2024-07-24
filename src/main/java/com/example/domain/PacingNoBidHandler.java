//package com.example.domain;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.experimental.Accessors;
//import org.apache.commons.lang3.time.DateUtils;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.springframework.stereotype.Service;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Map.Entry;
//import java.util.Queue;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.ConcurrentLinkedQueue;
//
///**
// * Thread that reads the no-bids queue and sends no-bid messages to metrics
// */
//@Service
//public class PacingNoBidHandler extends Thread{
//    private final Map<AdExchange, Queue<SingleNoBid>>adxMap=new ConcurrentHashMap<>();
//    public PacingNoBidHandler(){
//        setName("NoBidHandler");
//        Globals.setPacingNoBidHandler(this);
//        start();
//    }
//    public void enqueue(AdExchange adx,SingleNoBid noBid) {
//        Queue<SingleNoBid> queue=adxMap.get(adx);
//        if (queue==null){
//            adxMap.put(adx,new ConcurrentLinkedQueue());
//            queue=adxMap.get(adx);
//        }
//        queue.offer(noBid);
//    }
//    @Override
//    public void run(){
//        while(true){
//            try{
//                for(Entry<AdExchange, Queue<SingleNoBid>> adxEntry:adxMap.entrySet()) {
//                    AdExchange adx=adxEntry.getKey();
//                    Map<ImpType, CountTime> impTypeMap=new HashMap<>();
//                    Queue<SingleNoBid> noBidQueue=adxEntry.getValue();
//                    SingleNoBid noBid=noBidQueue.poll();
//                    while (noBid!=null) {
//                        ImpType impType=noBid.getImpType();
//                        CountTime countTime=impTypeMap.get(impType);
//                        if (countTime==null) {
//                            impTypeMap.put(impType,new CountTime());
//                            countTime=impTypeMap.get(impType);
//                        }
//                        countTime.setCount(countTime.getCount()+1).setTotalTime(countTime.getTotalTime()+noBid.getTotalTime()).setAdexProcessTime(countTime.getAdexProcessTime()+noBid.getAdexProcessTime());
//                        noBid=noBidQueue.poll();
//                    }
//                    for (Entry<ImpType, CountTime> impTypeEntry:impTypeMap.entrySet()) {
//                        ImpType impType=impTypeEntry.getKey();
//                        CountTime countTime=impTypeEntry.getValue();
//                        Globals.getPacingClusterInfo().getCountSender().sendMessage(1, "NOBID", adx.getName(), "", System.currentTimeMillis(),"127.0.0.1", "", "", "", "", 0.0D, 0.0D, 0L, countTime.getTotalTime(), 0L, countTime.getCount(),impType.name(), countTime.getAdexProcessTime());
//                    }
//                }
//            } catch (final Throwable ex) {
//                logger.error(ex.getMessage(), ex);
//            }
//        }
//    }
//
//    public enum AdExchange {
//        BES("BES", 100),
//        MOMO("MOMO", 102),
//        NONE("NONE", -1);
//
//        private final String name;
//        private final int id;
//        AdExchange(String name, int id) {
//            this.name = name;
//            this.id = id;
//        }
//
//        public static AdExchange getAdExchange(int id) {
//            AdExchange[] myArray = AdExchange.values();
//            for (AdExchange element : myArray) {
//                if (id == element.id) {
//                    return element;
//                }
//            }
//            return NONE;
//        }
//
//        public String getName() {
//            return this.name;
//        }
//    }
//
//    public enum ImpType {
//        SPLASH(1),
//        NATIVE(4),
//        ;
//
//        private final int value;
//        ImpType(int value) {
//            this.value = value;
//        }
//
//    }
//
//    @Data
//    @Accessors(chain = true)
//    public static class CountTime {
//        private long totalTime;
//        private long adexProcessTime;
//        private long count;
//    }
//
//    /**
//     * Contains information for a single no-bid.
//     */
//    @AllArgsConstructor
//    @Data
//    public static class SingleNoBid {
//        private long totalTime;
//        private long adexProcessTime;
//        private ImpType impType;
//    }
//}