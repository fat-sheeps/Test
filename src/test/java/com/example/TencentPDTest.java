package com.example;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.utils.OKHttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class TencentPDTest {

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
//        data.add(new JSONObject(){{put("pro_code", "202412160027");}});
//        data.add(new JSONObject(){{put("pro_code", "202412190023");}});
//        data.add(new JSONObject(){{put("pro_code", "202412240042");}});
        data.add(new JSONObject(){{put("pro_code", "202412250070");}});

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
                JSONArray deal_data  = jsonObject1.getJSONArray("deal_data");
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














}
