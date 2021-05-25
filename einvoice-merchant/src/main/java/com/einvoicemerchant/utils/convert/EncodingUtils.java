package com.einvoicemerchant.utils.convert;

import java.nio.charset.Charset;

public class EncodingUtils {
	public final static String UTF8_ENCODING = "UTF-8";
	public final static Charset UTF8_CHARSET = Charset.forName("UTF-8");

	public static String decodeUTF8(byte[] bytes) {
		return new String(bytes, UTF8_CHARSET);
	}

	public static byte[] encodeUTF8(String string) {
		return string.getBytes(UTF8_CHARSET);
	}
}
