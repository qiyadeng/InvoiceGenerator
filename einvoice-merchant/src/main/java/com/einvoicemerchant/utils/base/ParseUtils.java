package com.einvoicemerchant.utils.base;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;

import com.google.common.base.Strings;

public class ParseUtils {
    public static Long Object2Long(Object obj, Long defaultVal) {
        if (obj != null && !Strings.isNullOrEmpty(obj.toString())) {
            return Long.parseLong(obj.toString());
        } else
            return defaultVal;
    }

    public static Long Object2Long(Object obj) {
        if (obj != null && !Strings.isNullOrEmpty(obj.toString())) {
            return Long.parseLong(obj.toString());
        } else
            return null;
    }

    public static String Object2String(Object obj) {
        if (obj != null && !Strings.isNullOrEmpty(obj.toString())) {
            return obj.toString();
        } else
            return null;
    }

    public static void transMap2Bean(Map<String, Object> map, Object obj) {

        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();

                if (map.containsKey(key)) {
                    Object value = map.get(key);
                    // 得到property对应的setter方法
                    Method setter = property.getWriteMethod();
                    setter.invoke(obj, value);
                }

            }

        } catch (Exception e) {
            System.out.println("transMap2Bean Error " + e);
        }

        return;

    }

    public static Map<String, Object> transBean2Map(Map<String, Object> map, Object obj) {

        if (obj == null) {
            return null;
        }
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();

                // 过滤class属性
                if (!key.equals("class")) {
                    // 得到property对应的getter方法
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(obj);

                    map.put(key, value);
                }

            }
        } catch (Exception e) {
            System.out.println("transBean2Map Error " + e);
        }

        return map;

    }
}
