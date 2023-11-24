package org.example.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashSet;

/**
 * Copyright: Copyright (c) 2019 parkcloud
 *
 * @ClassName: com.clouds.common.utils
 * @Description:
 * @version: v1.0.0
 * @author: atao
 * @date: 2017/9/1   下午12:53
 * Modification History:
 * Date         Author          Version      Description
 * ---------------------------------------------------------*
 * 2017/9/1      atao          v1.0.0          创建
 */
@Component
public class SequenceUtil {
	private static final Logger logger = LoggerFactory.getLogger(SequenceUtil.class);
    /**
     *
     * @param typeEnum 序列枚举值	
     */
//    public static String getNextId(SequenceTypeEnum typeEnum) {
//        SnowflakeIdWorker idWorker =SnowflakeIdWorker.newInstance();
//        long id = idWorker.nextId();
//        return typeEnum.value()+id;
//    }


    /**
    *  序列枚举值
    * @param
    */
   public static Long getNextId() {
       SnowflakeIdWorker idWorker =SnowflakeIdWorker.newInstance();
       long id = idWorker.nextId();
       return id;
   }


    public static void main(String[] args) {
       HashSet<String> idSet = new HashSet<>();
       Runnable r =  new Runnable() {
           @Override
            public void run() {
               String str = "";

               for(int i=0;i<1000;i++){
                   str = SequenceUtil.getNextId() + "";
                   if(!idSet.add(str)){
                       System.out.println("重复 id 产生"+str);
                       break;
                   }else{
                       System.out.println(str);
                   }
               }
            }
        };
       new Thread(r).start();
       new Thread(r).start();
//       new Thread(r).start();
//       new Thread(r).start();
//       new Thread(r).start();
//       new Thread(r).start();
//       new Thread(r).start();
//       new Thread(r).start();
//       new Thread(r).start();
//       new Thread(r).start();
//       new Thread(r).start();

//       long begin = System.currentTimeMillis();
//        System.out.println(getNextId());
//       long end = System.currentTimeMillis();
//        System.out.println(end - begin);
//
//        System.out.println(getNextId());
//        long second = System.currentTimeMillis();
//        System.out.println(second - end);


    }


}
