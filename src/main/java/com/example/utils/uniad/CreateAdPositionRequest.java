package com.example.utils.uniad;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CreateAdPositionRequest extends CommonParams {
    private String app_id;
    private String dcloud_adp_id;
    private String adp_name;
    private int platform;
    private int adp_type;
    private int adp_style;
    private Integer adp_proportion; // nullable
    private Integer adp_height; // nullable
    private Integer adp_site; // nullable
}
