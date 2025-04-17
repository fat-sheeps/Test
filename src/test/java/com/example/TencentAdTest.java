package com.example;


import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.tencent.ads.ApiContextConfig;
import com.tencent.ads.ApiException;
import com.tencent.ads.container.v3.DynamicCreativeReviewResultsApiContainer;
import com.tencent.ads.model.PromotedObjectType;
import com.tencent.ads.model.PromotedObjectsGetResponseData;
import com.tencent.ads.model.v3.*;
import com.tencent.ads.v3.TencentAds;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.*;

@Slf4j
public class TencentAdTest {

    private final static String accessToken = "2209125331824ccf4880810b847d01e9";

    public static void main(String[] args) {
        // 创建一个Date对象，表示2024年12月1日 1732982400
        Date date = DateUtil.parseDate("2024-12-01");
        long timestamp = date.getTime() / 1000;
        System.out.println("2024年12月1日的时间戳（秒）：" + timestamp);
    }
    @Test
    public void getAdvertiser() throws ApiException {
        Long accountId = 45392489L;//上海雪泽科技有限公司
        TencentAds tencentAds = TencentAds.getInstance();
        tencentAds.init(new ApiContextConfig(accessToken, false));

        List<String> fields = Arrays.asList("account_id","registration_type","daily_budget","system_status","reject_message","corporation_name","corporation_licence","certification_image_id","certification_image","individual_qualification","system_industry_id","customized_industry","introduction_url","corporate_brand_name","contact_person","contact_person_email","contact_person_telephone","contact_person_mobile");
        AdvertiserGetResponseData result = tencentAds.advertiser().advertiserGet(fields, PaginationMode.NORMAL.getValue(), 10L, null, accountId, null, 1L, null);
        System.out.println(JSON.toJSONString(result));
    }
//    @Test
//    public void getAgency() throws ApiException {
//        Long accountId = 45392489L;//上海雪泽科技有限公司
//        TencentAds tencentAds = TencentAds.getInstance();
//        tencentAds.init(new ApiContextConfig(accessToken, false));
//
//        List<String> fields = Arrays.asList("account_id","corporation_name","system_status","reject_message","parent_agency_id","memo");
//        AgencyGetResponseData result = tencentAds.agency().agencyGet(fields, 1L, 10L, null);
//        System.out.println(JSON.toJSONString(result));
//    }
    @Test
    public void getCreative() throws ApiException {

//        Long creativeId =  1681354411L;//图片
        Long creativeId =  1726427503L;//图片
//        Long accountId = 45392489L;
//        Long accountId = 49396344L;//小程序链接
//        Long accountId = 53371937L;//第三方落地页
        Long accountId = 49341804L;
        TencentAds tencentAds = TencentAds.getInstance();
        tencentAds.init(new ApiContextConfig(accessToken, false));

        /*List<FilteringStruct> filtering = new ArrayList<>();
        FilteringStruct filteringStruct = new FilteringStruct();
        filteringStruct.setField("created_time");
        filteringStruct.setOperator(FilterOperator.GREATER_EQUALS);
        filteringStruct.setValues(Collections.singletonList(1733000000 + ""));
        filtering.add(filteringStruct);*/

        List<FilteringStruct> filtering = new ArrayList<>();
        FilteringStruct filteringStruct = new FilteringStruct();
        filteringStruct.setField("dynamic_creative_id");
        filteringStruct.setOperator(FilterOperator.EQUALS);
        filteringStruct.setValues(Collections.singletonList(creativeId + ""));
        filtering.add(filteringStruct);

        List<String> fields = Arrays.asList("dynamic_creative_id", "dynamic_creative_name", "created_time", "last_modified_time", "adgroup_id", "configured_status","system_status", "creative_components", "delivery_mode");
        DynamicCreativesGetResponseData result = tencentAds.dynamicCreatives().dynamicCreativesGet(accountId, filtering, 1L, 10L, fields, null, PaginationMode.NORMAL.getValue(), null);
        DynamicCreativesAddRequest data = new DynamicCreativesAddRequest();
//        tencentAds.dynamicCreatives().dynamicCreativesAdd(data);
        System.out.println(JSON.toJSONString(result));
    }
    //创意预览
    @Test
    public void creative_template_previews() throws ApiException {

//        Long creativeId =  1808076924L;//视频
//        Long accountId = 49341828L;
        Long accountId = 54479923L;
        Long creativeId =  1808341417L;//图片
        TencentAds tencentAds = TencentAds.getInstance();
        tencentAds.init(new ApiContextConfig(accessToken, false));
        CreativeTemplatePreviewsGetRequest request = new CreativeTemplatePreviewsGetRequest();
        request.setAccountId(accountId);
        request.setDynamicCreativeId(creativeId);
        CreativeTemplatePreviewsGetResponseData result = tencentAds.creativeTemplatePreviews().creativeTemplatePreviewsGet(request);
        System.out.println(JSON.toJSONString(result));
    }

    @Test
    public void getCreativeReviewResults() throws ApiException {
        List<Long> creativeIds = Arrays.asList(1726427503L);
        Long accountId = 49341804L;
        TencentAds tencentAds = TencentAds.getInstance();
        tencentAds.init(new ApiContextConfig(accessToken, false));

        List<String> fields = null;

        DynamicCreativeReviewResultsGetResponseData result = tencentAds.dynamicCreativeReviewResults().dynamicCreativeReviewResultsGet(accountId, creativeIds, fields);

        System.out.println(JSON.toJSONString(result));
    }
    @Test
    public void getCreativeReviewResults2() throws ApiException {
        List<Map<Long, String>> creativeStatusMap = new ArrayList<>();
        //1726619792-审核中，1726619711-审核中，1726619597-审核中，1702435086-投放中，1702435077-投放中，1702434012-部分投放中
        List<Long> creativeIds = Arrays.asList(1726619792L,1726619711L,1726619597L,1702435086L,1702435077L,1702434012L);
        Long accountId = 53371929L;
        TencentAds tencentAds = TencentAds.getInstance();
        tencentAds.init(new ApiContextConfig(accessToken, false));

        List<String> fields = Arrays.asList("dynamic_creative_id", "element_result_list");

        DynamicCreativeReviewResultsGetResponseData result = tencentAds.dynamicCreativeReviewResults().dynamicCreativeReviewResultsGet(accountId, creativeIds, fields);
        for (ReviewResultListStruct listStruct : result.getList()) {
            //所有的创意审核结果都为NORMAL 则对应的creativeIdStatus value为投放中
            if (listStruct.getSiteSetResultList().stream().allMatch(i -> i.getSystemStatus().equals(ReviewResultStatus.NORMAL))) {
                creativeStatusMap.add(new HashMap<Long, String>() {{
                    put(listStruct.getDynamicCreativeId(), "投放中");
                }});
            }
            //所有的创意审核结果只要有一个为PENDING 则对应的creativeIdStatus value为审核中
            if (listStruct.getSiteSetResultList().stream().anyMatch(i -> i.getSystemStatus().equals(ReviewResultStatus.PENDING))) {
                creativeStatusMap.add(new HashMap<Long, String>() {{
                    put(listStruct.getDynamicCreativeId(), "审核中");
                }});
            }
            //所有的创意审核结果都为DENIED 则对应的creativeIdStatus value为审核不通过
            if (listStruct.getSiteSetResultList().stream().allMatch(i -> i.getSystemStatus().equals(ReviewResultStatus.DENIED))) {
                creativeStatusMap.add(new HashMap<Long, String>() {{
                    put(listStruct.getDynamicCreativeId(), "审核不通过");
                }});
            }
            //创意审核结果DENIED、PARTIALLY_NORMAL、PENDING & NORMAL都有 则对应的creativeIdStatus value为部分投放中
            if (listStruct.getSiteSetResultList().stream().anyMatch(i -> i.getSystemStatus().equals(ReviewResultStatus.NORMAL))
                    && (listStruct.getSiteSetResultList().stream().anyMatch(i -> i.getSystemStatus().equals(ReviewResultStatus.DENIED))
                        || listStruct.getSiteSetResultList().stream().anyMatch(i -> i.getSystemStatus().equals(ReviewResultStatus.PENDING))
                        || listStruct.getSiteSetResultList().stream().anyMatch(i -> i.getSystemStatus().equals(ReviewResultStatus.PARTIALLY_NORMAL))
                       )
                ) {
                creativeStatusMap.add(new HashMap<Long, String>() {{
                    put(listStruct.getDynamicCreativeId(), "部分投放中");
                }});
            }
        }
        System.out.println(JSON.toJSONString(creativeStatusMap));
    }

    @Test
    public void getAdgroup() throws ApiException {

//        Long adgroupId =  27509152008L;//图片
        Long creativeId =  1681354411L;//图片
//        Long accountId = 45392489L;
        Long adgroupId =  29659824114L;//图片
        Long accountId = 42706622L;
        TencentAds tencentAds = TencentAds.getInstance();
        tencentAds.init(new ApiContextConfig(accessToken, false));

        List<FilteringStruct> filtering = new ArrayList<>();
        FilteringStruct filteringStruct = new FilteringStruct();
        filteringStruct.setField("adgroup_id");
        filteringStruct.setOperator(FilterOperator.EQUALS);
        filteringStruct.setValues(Collections.singletonList(adgroupId + ""));
//        filtering.add(filteringStruct);

        List<String> fields = Arrays.asList("adgroup_id", "adgroup_name", "created_time", "last_modified_time", "system_status", "configured_status", "targeting_translation","marketing_carrier_type","marketing_carrier_detail", "material_package_id", "marketing_goal", "marketing_sub_goal", "marketing_carrier_type", "marketing_target_type", "site_set", "search_expansion_switch", "automatic_site_enabled", "daily_budget", "smart_bid_type", "optimization_goal", "bid_amount", "deep_conversion_type", "deep_conversion_behavior_bid", "conversion_id", "deep_conversion_worth_advanced_rate", "bid_mode", "bid_strategy", "bid_scene", "auto_acquisition_enabled", "auto_acquisition_status", "begin_date", "end_date", "time_series", "first_day_begin_time", "targeting", "targeting_translation", "gender", "age", "education", "app_install_status", "excluded_converted_audience", "user_os", "excluded_os", "network_type", "custom_audience", "excluded_custom_audience", "device_brand_model", "included_list", "excluded_list");
        AdgroupsGetResponseData result = tencentAds.adgroups().adgroupsGet(accountId, filtering, 1L, 5L, null, fields, PaginationMode.NORMAL.getValue(), null);
        AdgroupsAddRequest data = null;
//        tencentAds.adgroups().adgroupsAdd(data, null);
        System.out.println(JSON.toJSONString(result));
    }

    @Test
    public void getVideo() throws ApiException {
        Long creativeId =  1663881986L;//视频
        Long accountId = 50451130L;
        Long videoId = 15177627760L;
        Long videoId2 = 15177628439L;
        TencentAds tencentAds = TencentAds.getInstance();
        tencentAds.init(new ApiContextConfig(accessToken, true));

        List<FilteringStruct> videoFiltering = new ArrayList<>();
        FilteringStruct videoFilteringStruct = new FilteringStruct();
        videoFilteringStruct.setField("created_time");
        videoFilteringStruct.setOperator(FilterOperator.GREATER_EQUALS);
        videoFilteringStruct.setValues(Arrays.asList(1735570000 + ""));

        FilteringStruct videoFilteringStruct2 = new FilteringStruct();
        videoFilteringStruct2.setField("created_time");
        videoFilteringStruct2.setOperator(FilterOperator.LESS_EQUALS);
        videoFilteringStruct2.setValues(Arrays.asList(1735570890 + ""));

        videoFiltering.add(videoFilteringStruct);
        videoFiltering.add(videoFilteringStruct2);
        VideosGetResponseData result = tencentAds.videos().videosGet(accountId, null, videoFiltering, 1L, 10L, null, null, null);
        System.out.println(JSON.toJSONString(result));

//        Long videoId2 = 15177628439L;
//        List<FilteringStruct> videoFiltering2 = new ArrayList<>();
//        FilteringStruct videoFilteringStruct2 = new FilteringStruct();
//        videoFilteringStruct2.setField("media_id");
//        videoFilteringStruct2.setOperator(FilterOperator.EQUALS);
//        videoFilteringStruct2.setValues(Collections.singletonList(videoId2 + ""));
//        videoFiltering2.add(videoFilteringStruct2);
//        VideosGetResponseData result2 = tencentAds.videos().videosGet(accountId, null, videoFiltering2, 1L, 1L, null, null, null);
//        System.out.println(JSON.toJSONString(result2));

    }



    @Test
    public void getImage() throws ApiException {
        Long creativeId =  1681354411L;//图片
        Long accountId = 45392489L;
        Long imageId =  15237064261L;
        TencentAds tencentAds = TencentAds.getInstance();
        tencentAds.init(new ApiContextConfig(accessToken, true));

        List<FilteringStruct> imageFiltering = new ArrayList<>();
        FilteringStruct imageFilteringStruct = new FilteringStruct();
        imageFilteringStruct.setField("image_id");
        imageFilteringStruct.setOperator(FilterOperator.EQUALS);
        imageFilteringStruct.setValues(Collections.singletonList(imageId + ""));
        imageFiltering.add(imageFilteringStruct);
        ImagesGetResponseData result = tencentAds.images().imagesGet(accountId, null, imageFiltering, 1L, 1L, null, null, null);
        System.out.println(JSON.toJSONString(result));
    }
    @Test
    public void getBrand() throws ApiException {
        Long accountId = 53371903L;
        TencentAds tencentAds = TencentAds.getInstance();
        tencentAds.init(new ApiContextConfig(accessToken, false));
        BrandGetResponseData result = tencentAds.brand().brandGet(accountId, 1L, 10L, null);
        System.out.println(JSON.toJSONString(result));
    }
    @Test
    public void getChannel() throws ApiException {
        Long accountId = 53371903L;
        Long appId = 100838841L;
        TencentAds tencentAds = TencentAds.getInstance();
        tencentAds.init(new ApiContextConfig(accessToken, false));

        List<FilteringStruct> filteringStructs = new ArrayList<>();
        FilteringStruct filteringStruct = new FilteringStruct();
        filteringStruct.setField("system_status");
        filteringStruct.setOperator(FilterOperator.IN);
        filteringStruct.setValues(Collections.singletonList("CHANNEL_PACKAGE_STATUS_REVIEWING"));
//        filteringStructs.add(filteringStruct);

        AndroidChannelGetResponseData result = tencentAds.androidChannel().androidChannelGet(accountId, appId, filteringStructs, 1L, 10L, null);
        System.out.println(JSON.toJSONString(result));
    }
    @Test
    public void getUnionPositionPackages() throws ApiException {
        Long accountId = 53371903L;
        TencentAds tencentAds = TencentAds.getInstance();
        tencentAds.init(new ApiContextConfig(accessToken, false));
        UnionPositionPackagesGetResponseData result = tencentAds.unionPositionPackages().unionPositionPackagesGet(accountId, new ArrayList<>(), 1L, 10L, null);
        System.out.println(JSON.toJSONString(result));
    }
    @Test
    public void getComponents() throws ApiException {
        Long accountId = 53371903L;
        TencentAds tencentAds = TencentAds.getInstance();
        tencentAds.init(new ApiContextConfig(accessToken, false));
//        ComponentType.
        ComponentsGetResponseData result = tencentAds.components().componentsGet(accountId, null, new ArrayList<>(), 1L, 10L, null, Arrays.asList("component_value"));
        System.out.println(JSON.toJSONString(result));
    }

    @Test
    public void marketing_target_assets() throws ApiException {
        Long accountId = 45392489L;
//        Long accountId = 53371903L;
        TencentAds tencentAds = TencentAds.getInstance();
        tencentAds.init(new ApiContextConfig(accessToken, true));
        MarketingTargetType marketingTargetType = MarketingTargetType.APP_IOS;
        MarketingTargetAssetsGetResponseData result = tencentAds.marketingTargetAssets().marketingTargetAssetsGet(marketingTargetType.getValue(), accountId, null, 1L, 10L, null, null);
        System.out.println(JSON.toJSONString(result));
    }
    //营销载体
    @Test
    public void promoted_objects() throws ApiException {
//        Long accountId = 45392489L;
        Long accountId = 53371903L;
        com.tencent.ads.TencentAds tencentAds = com.tencent.ads.TencentAds.getInstance();
        tencentAds.useProduction();
        tencentAds.init(new ApiContextConfig(accessToken, false));

        List<com.tencent.ads.model.FilteringStruct> filteringStructs = new ArrayList<>();
        com.tencent.ads.model.FilteringStruct typeFilteringStruct = new com.tencent.ads.model.FilteringStruct();
        typeFilteringStruct.setField("promoted_object_type");
        typeFilteringStruct.setOperator(FilterOperator.EQUALS.getValue());
        typeFilteringStruct.setValues(Collections.singletonList(PromotedObjectType.APP_IOS.getValue()));
//        filteringStructs.add(typeFilteringStruct);

        List<String> fields = Arrays.asList("promoted_object_type", "promoted_object_id", "promoted_object_name", "promoted_object_spec");
        PromotedObjectsGetResponseData result = tencentAds.promotedObjects().promotedObjectsGet(accountId, filteringStructs, 1L, 10L, fields);
        System.out.println(JSON.toJSONString(result));
    }
    /*@Test
    public void marketing_target_types() throws ApiException {
        Long accountId = 53371903L;
        TencentAds tencentAds = TencentAds.getInstance();
        tencentAds.init(new ApiContextConfig(accessToken, false));
        MarketingTargetTypesGetResponseData result = tencentAds.marketingTargetTypes().marketingTargetTypesGet(accountId, null, null);
        System.out.println(JSON.toJSONString(result));
    }*/













}
