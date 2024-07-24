package com.example.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("user")
@Builder
public class User extends Model<User> {

    @TableId("id")
    private Long id;

    @TableField("name")
    private String name;

    @TableField("age")
    private Integer age;

    @TableField("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @TableField(exist = false)
    private Date date;

//    private List<Integer> types;

    @TableField(exist = false)
    private int priority;
    @TableField(exist = false)
    private float priorityPercent;
    @TableField(exist = false)
    private List<SubUser> subUsers;






}
