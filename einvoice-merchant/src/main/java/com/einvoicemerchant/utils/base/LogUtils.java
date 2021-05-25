package com.einvoicemerchant.utils.base;

import java.util.Map;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.base.Throwables;
import com.google.common.collect.Maps;

public class LogUtils {
	/*
	 * 统一log格式，生成 message : key1=[value1], key2=[value2].
	 */
	public static String genByObject(String message, Map<String, Object> params) {
		String body = Joiner.on("], ").withKeyValueSeparator("=[").join(params);
		String postfix = Strings.isNullOrEmpty(body) ? "" : "].";
		return String.format("%s : %s%s", message, body, postfix);
	}

	/**
	 * 至少有一个，数量是奇数
	 * 
	 * @param message
	 * @param args message, key1, value1, key2, value2...
	 * @return
	 */
	public static String gen(String message, String... args) {
		if (args == null || args.length % 2 == 1)
			return "GENERROR";
		Map<String, String> params = Maps.newHashMap();
		for (int i = 0; i < args.length;) {
			params.put(args[i], args[i + 1]);
			i = i + 2;
		}
		return gen(message, params);
	}

	public static String gen(String message, Map<String, String> params) {
		String body = Joiner.on("], ").useForNull("").withKeyValueSeparator("=[").join(params);
		String postfix = Strings.isNullOrEmpty(body) ? "" : "].";
		return String.format("%s : %s%s", message, body, postfix);
	}

	public static String genException(Exception e) {
		Map<String, String> params = Maps.newHashMap();
		params.put("msg", e.getMessage());
		params.put("stack", Throwables.getStackTraceAsString(Throwables.getRootCause(e)));
		return gen("", params);
	}
}
