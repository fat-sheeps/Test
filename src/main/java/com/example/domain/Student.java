package com.example.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.Map;

@Data
@Accessors(chain = true)
public class Student {
    private Integer id;
    private String name;
    private Integer age;
    private Map<String, String> desc = new HashMap<>();
    private String remark;

}
