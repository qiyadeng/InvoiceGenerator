package com.einvoicemerchant.utils.base;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonUtils {
	/*
	 * 全局设置
	 */
	public static void init() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL); // NULL不显示
	}
}
