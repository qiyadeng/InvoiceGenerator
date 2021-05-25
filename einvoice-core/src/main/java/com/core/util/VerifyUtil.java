package com.core.util;

/**
 * @author Effort
 * @description
 * @date 2020/10/13 8:46 上午
 */
public class VerifyUtil {
    public static boolean verifyEmail(String email) {
        if(email == null||email.equals("")) {
            return false;
        }
        String regex = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        return  email.matches(regex);
    }
}
