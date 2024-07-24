package com.example.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
public class ObjectUtil {
    public static <T> Map<String, Object> toMap(T entity) {
        Map<String, Object> map = new HashMap<>();
        if (entity == null) {
            return map;
        }

        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(entity.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                String propertyName = propertyDescriptor.getName();
                if (!"class".equals(propertyName)) {
                    Method readMethod = propertyDescriptor.getReadMethod();
                    Object propertyValue = readMethod.invoke(entity);
                    map.put(propertyName, propertyValue);
                }
            }
        } catch (Exception e) {
            // Handle exception
        }
        return map;
    }
}
