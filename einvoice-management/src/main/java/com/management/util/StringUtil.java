package com.management.util;

import java.util.UUID;

/**
 * @author Effort
 * @description
 * @date 2020/10/26 9:13 上午
 */
public class StringUtil {
    public static String generateUUid() {
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}
