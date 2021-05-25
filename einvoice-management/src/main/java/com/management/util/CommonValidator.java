package com.management.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Effort
 * @description
 * @date 2020/10/13 8:54 上午
 */
public class CommonValidator {
    public static String verifyParam(String string,String message) {
        if (string == null || string.length() == 0) {
            throw new IllegalArgumentException(message);
        }
        return string.trim();
    }
    /**
     * 一个字符串的内容是否全为数字
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) { //都为数字
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (isNum.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断是不是double类型(简单判断)
     * @param str
     * @return
     */
    public static boolean isDouble(String str){
        Pattern pattern = Pattern.compile("^\\d+(\\.\\d+)?$");
        Matcher isNum = pattern.matcher(str);
        if (isNum.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 检查对象的属性是否为空
     *
     * @param obj
     * @param attributeName
     * @return 属性中一个值为null时，返回false;否则返回true
     */
    public static boolean isObjectAttributeNotNull(Object obj, String... attributeName) {
        Class<? extends Object> clazz = obj.getClass();
        for (String attribute : attributeName) {
            try {
                PropertyDescriptor pd = new PropertyDescriptor(attribute, clazz);
                Method getMethod = pd.getReadMethod();//获取get方法
                Object value = getMethod.invoke(obj);//执行get方法返回一个object
                if (value == null) {//检查属性的值为空
                    return false;
                }
            } catch (Exception ex) {
                //logger.info("get object attribute value exception {}", ex);
                return false;
            }
        }
        return true;
    }
}
