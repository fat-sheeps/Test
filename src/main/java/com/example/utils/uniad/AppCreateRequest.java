package com.example.utils.uniad;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AppCreateRequest extends CommonParams {
    private String dcloud_app_id;
    private String app_name;
    private int industry_id;
    private int os;
    private String detail_url;
    private String keywords;
    private String description;
    private String package_name;
    private String sha1;
    private String domain;
    private String icp;
    private Long icp_picture_img_id;
    private Long soft_right_img_id;
    private String wx_app_id;
    private String wechat_universal_link;
    private String filter_pkg_names;
    private String filter_keywords;
    private int download_suspend;
    private int download_confirm;
    private int skip_time;
    private int close_ground;

}
