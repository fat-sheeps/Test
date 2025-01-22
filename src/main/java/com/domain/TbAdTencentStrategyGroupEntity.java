package com.domain;
import java.util.Date;
import java.io.Serializable;
import lombok.Data;


/**
 * (TbAdTencentStrategyGroup)实体类
 *
 * @author makejava
 * @since 2025-01-16 11:09:13
 */


@Data

public class TbAdTencentStrategyGroupEntity implements Serializable {
    private static final long serialVersionUID = -52559612306705206L;
/**
     * 策略id
     */
    private Long strategyGroupId;

/**
     * 搭建模式 1-新建广告 2-已有广告
     */
    private Integer adMode;

/**
     * 配置数据(json)
     */
    private Object configData;

/**
     * 创建人
     */
    private Integer createdBy;

/**
     * 创建时间
     */
    private Date createdOn;

/**
     * 是否有效
     */
    private Integer isActive;

/**
     * 营销目的
     */
    private Integer marketingGoal;

/**
     * 备注
     */
    private String remark;

/**
     * 策略组名称
     */
    private String strategyGroupName;

/**
     * 更新人
     */
    private Integer updatedBy;

/**
     * 更新时间
     */
    private Date updatedOn;



}

