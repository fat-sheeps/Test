package com.example.utils;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.dingtalkim_1_0.models.ChatIdToOpenConversationIdHeaders;
import com.aliyun.dingtalkim_1_0.models.ChatIdToOpenConversationIdResponse;
import com.aliyun.dingtalkoauth2_1_0.models.GetAccessTokenResponse;
import com.aliyun.dingtalkrobot_1_0.models.OrgGroupSendResponse;
import com.aliyun.tea.*;
import com.aliyun.teautil.models.RuntimeOptions;
import lombok.extern.slf4j.Slf4j;

@Slf4j
/**
 * 钉钉机器人api
 * https://open.dingtalk.com/document/orgapp/the-robot-sends-a-group-message?spm=ding_open_doc.document.0.0.349566daDVNOfH
 * <!--钉钉机器人-->
 *         <dependency>
 *             <groupId>com.aliyun</groupId>
 *             <artifactId>dingtalk</artifactId>
 *             <version>2.1.14</version>
 *         </dependency>
 */
public class DingTalk {

    //监控助手-机器人
    private static final String ROBOT_CODE = "dingz4humefpehhbqin7";
    private static final String APP_KEY = ROBOT_CODE;
    private static final String APP_SECRET = "wJL1gTXBT6ibzI_GAfyqGYI25zt11CirALLQatCq5f2w-uL431AEr0dhdF46qJ8x";
    //WAY_ADX(告警) 群会话id
//    private static final String OPEN_CONVERSATION_ID = "cidF/uq7k2UF5AmCQq+7s0S0g==";
    //test
    private static final String OPEN_CONVERSATION_ID = "cidyUYHVzqIcI89bppbsD8ukA==";
    //两小时有效，需缓存起来，请勿频繁调用getAccessToken()接口
    private static String ACCESS_TOKEN = "46930f451e22366a941ba09d6d02aa4a";

    /**
     * 使用 Token 初始化账号Client
     * @return Client
     * @throws Exception
     */
    public static com.aliyun.dingtalkoauth2_1_0.Client createClient2() throws Exception {
        com.aliyun.teaopenapi.models.Config config = new com.aliyun.teaopenapi.models.Config();
        config.protocol = "https";
        config.regionId = "central";
        return new com.aliyun.dingtalkoauth2_1_0.Client(config);
    }

    /**
     * 获取accessToken
     * 两小时有效，需缓存起来，请勿频繁调用getAccessToken()接口
     * @return
     * @throws Exception
     */
    public static String getAccessToken() throws Exception {
        com.aliyun.dingtalkoauth2_1_0.Client client = DingTalk.createClient2();
        com.aliyun.dingtalkoauth2_1_0.models.GetAccessTokenRequest getAccessTokenRequest = new com.aliyun.dingtalkoauth2_1_0.models.GetAccessTokenRequest()
                .setAppKey(APP_KEY)
                .setAppSecret(APP_SECRET);
        try {
            GetAccessTokenResponse res = client.getAccessToken(getAccessTokenRequest);
            log.info("DingRobot getAccessToken res:{}", JSONObject.toJSONString(res.getBody()));
            // TODO 缓存accessToken
            //ACCESS_TOKEN = res.getBody().getAccessToken();
            //过期秒数：res.getBody().getExpireIn();
            return res.getBody().getAccessToken();
        } catch (TeaException err) {
            if (!com.aliyun.teautil.Common.empty(err.code) && !com.aliyun.teautil.Common.empty(err.message)) {
                // err 中含有 code 和 message 属性，可帮助开发定位问题
                log.error("DingRobot getAccessToken error code:{},msg:{}", err.code, err.message);
                return null;
            }

        } catch (Exception _err) {
            TeaException err = new TeaException(_err.getMessage(), _err);
            if (!com.aliyun.teautil.Common.empty(err.code) && !com.aliyun.teautil.Common.empty(err.message)) {
                // err 中含有 code 和 message 属性，可帮助开发定位问题
                log.error("DingRobot getAccessToken error code:{},msg:{}", err.code, err.message);
                return null;
            }

        }
        return null;
    }
    /**
     * 使用 Token 初始化账号Client
     * @return Client
     * @throws Exception
     */
    public static com.aliyun.dingtalkrobot_1_0.Client createClient() throws Exception {
        com.aliyun.teaopenapi.models.Config config = new com.aliyun.teaopenapi.models.Config();
        config.protocol = "https";
        config.regionId = "central";
        return new com.aliyun.dingtalkrobot_1_0.Client(config);
    }

    public static int sendGroupMsg(String title, String msg, String json) throws Exception {
        com.aliyun.dingtalkrobot_1_0.Client client = DingTalk.createClient();
        com.aliyun.dingtalkrobot_1_0.models.OrgGroupSendHeaders orgGroupSendHeaders = new com.aliyun.dingtalkrobot_1_0.models.OrgGroupSendHeaders();
        //TODO ACCESS_TOKEN放入redis中缓存起来 2小时有效 失效后重新获取
        if (ACCESS_TOKEN == null) {
            DingTalk.getAccessToken();
        }
        orgGroupSendHeaders.xAcsDingtalkAccessToken = ACCESS_TOKEN;
        com.aliyun.dingtalkrobot_1_0.models.OrgGroupSendRequest orgGroupSendRequest = new com.aliyun.dingtalkrobot_1_0.models.OrgGroupSendRequest()
                .setMsgParam(json)
                .setMsgKey("sampleMarkdown")
                .setOpenConversationId(OPEN_CONVERSATION_ID)
                .setRobotCode(ROBOT_CODE);
        try {
            OrgGroupSendResponse orgGroupSendResponse = client.orgGroupSendWithOptions(orgGroupSendRequest, orgGroupSendHeaders, new RuntimeOptions());
            log.info("DingRobot sendMsg res:{}", JSONObject.toJSONString(orgGroupSendResponse.getBody()));
        } catch (TeaException err) {
            if (!com.aliyun.teautil.Common.empty(err.code) && !com.aliyun.teautil.Common.empty(err.message)) {
                // err 中含有 code 和 message 属性，可帮助开发定位问题
                log.error("DingRobot sendMsg error code:{},msg:{}", err.code, err.message);
                if (err.code.equals("InvalidAuthentication")) {
                    ACCESS_TOKEN = null;
                    // TODO token失效了 重试
                    return -2;
                }
                return -1;
            }

        } catch (Exception _err) {
            TeaException err = new TeaException(_err.getMessage(), _err);
            if (!com.aliyun.teautil.Common.empty(err.code) && !com.aliyun.teautil.Common.empty(err.message)) {
                // err 中含有 code 和 message 属性，可帮助开发定位问题
                log.error("DingRobot sendMsg error code:{},msg:{}", err.code, err.message);
                return -1;
            }

        }
        return 0;
    }

    /**
     * 使用 Token 初始化账号Client
     * @return Client
     * @throws Exception
     */
    public static com.aliyun.dingtalkim_1_0.Client createClient3() throws Exception {
        com.aliyun.teaopenapi.models.Config config = new com.aliyun.teaopenapi.models.Config();
        config.protocol = "https";
        config.regionId = "central";
        return new com.aliyun.dingtalkim_1_0.Client(config);
    }

    public static String getOpenConversationId(String chatId) throws Exception {
        com.aliyun.dingtalkim_1_0.Client client = createClient3();
        ChatIdToOpenConversationIdHeaders chatIdToOpenConversationIdHeaders = new ChatIdToOpenConversationIdHeaders();
        chatIdToOpenConversationIdHeaders.xAcsDingtalkAccessToken = ACCESS_TOKEN;
        try {
            ChatIdToOpenConversationIdResponse res = client.chatIdToOpenConversationIdWithOptions(chatId, chatIdToOpenConversationIdHeaders, new RuntimeOptions());
            return res.getBody().getOpenConversationId();
        } catch (TeaException err) {
            if (!com.aliyun.teautil.Common.empty(err.code) && !com.aliyun.teautil.Common.empty(err.message)) {
                // err 中含有 code 和 message 属性，可帮助开发定位问题
                log.error("DingRobot getOpenConversationId error code:{},msg:{}", err.code, err.message);
            }

        } catch (Exception _err) {
            TeaException err = new TeaException(_err.getMessage(), _err);
            if (!com.aliyun.teautil.Common.empty(err.code) && !com.aliyun.teautil.Common.empty(err.message)) {
                // err 中含有 code 和 message 属性，可帮助开发定位问题
                log.error("DingRobot getOpenConversationId error code:{},msg:{}", err.code, err.message);
            }

        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        //
        JSONObject json = new JSONObject();
        json.put("title", "title");
//        json.put("text", "### 请求数告警 \n  陌陌安卓开屏4点请求数：423387，昨日4点请求数：623387，环比下降<font color=#00FF00>50%</font>。 ###### \n 环比上升<font color=#FF0000>50%</font>【测试】");
        json.put("text", "### title \n  - 50166-广告位名称: --, 流量主名称: 爱客美, 量级: 0.0 , 流量主收益(元)环比: -100% \n - 50166-广告位名称: --, 流量主名称: 爱客美, 量级: 0.0 , 流量主收益(元)环比: -100% \n - 50166-广告位名称: --, 流量主名称: 爱客美, 量级: 0.0 , 流量主收益(元)环比: -100%");
        int res = sendGroupMsg("请求数告警", "", json.toJSONString());
        System.out.println(res);
//        String accessToken = DingTalk.getAccessToken();
//        System.out.println(accessToken);


//        String openConversationId = DingTalk.getOpenConversationId("");
//        System.out.println(openConversationId);
    }
}