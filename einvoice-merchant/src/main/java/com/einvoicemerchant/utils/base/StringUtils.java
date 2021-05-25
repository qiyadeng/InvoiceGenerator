package com.einvoicemerchant.utils.base;

import com.google.common.base.Function;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
	public static String val(String s) {
		return (s == null) ? "" : s;
	}

	public static String padLeft(String s, String pad) {
		return (s == null) ? null : pad + s;
	}

	public static String padRight(String str, int strLength) {
		int strLen = str.length();
		if (strLen < strLength) {
			while (strLen < strLength) {
				StringBuffer sb = new StringBuffer();
				sb.append(str).append("0");// 右补0
				str = sb.toString();
				strLen = str.length();
			}
		}

		return str;
	}

	public static String insertSpiltSign(String text, String match, String insertText) {
		int location = text.indexOf(match);
		if (location != -1) {
			String url = text.substring(0, location);
			if (url.contains("?")) {
				return "&" + insertText;
			} else {
				return "?" + insertText;
			}
		} else{
			if (text.contains("?")) {
				return "&" + insertText;
			} else {
				return "?" + insertText;
			}
		}

	}

	/**
	 * @param templateString : hello {name}
	 * @param params         name=andrew
	 * @return hello andrew
	 */
	public static String format(String templateString, Map<String, Object> params) {
		if (params != null && params.size() > 0) {
			for (Map.Entry<String, Object> entry : params.entrySet())
				templateString = templateString.replace("{" + entry.getKey() + "}", entry.getValue().toString());
		}
		return templateString;
	}

	private static final Pattern CRLF = Pattern.compile("(\r\n|\r|\n|\n\r)");

	/**
	 * 过滤回车
	 */
	public static String filterReturn(String text) {
		Matcher m = CRLF.matcher(text);
		if (m.find())
			text = m.replaceAll("");
		return text;
	}

	/**
	 * 过滤回车和前后空格
	 */
	public static String purify(String text) {
		if (Strings.isNullOrEmpty(text))
			return text;
		else
			return filterReturn(text.trim());
	}

	/**
	 * 字符串匹配插入
	 *
	 * @param text       待判别的字符串
	 * @param match      判断字符
	 * @param insertText 要插入的字符串
	 */
	public static String insertBefore(String text, String match, String insertText) {
		int location = text.indexOf(match);
		if (location != -1)
			return text.substring(0, location) + insertText + text.substring(location, text.length());
		else
			return text + insertText;
	}

	public static String filterTrim(String text) {
		if (Strings.isNullOrEmpty(text))
			return text;
		else {
			return text.replaceAll(" ", "");

		}
	}

	public static List<String> split(String text, final String separator) {
		return Splitter.on(separator).omitEmptyStrings().splitToList(text);
	}

	public static List<Integer> Split2Int(String text, final String separator) {
		return Lists.transform(split(text, separator), new Function<String, Integer>() {
			@Override
			public Integer apply(String value) {
				return Integer.parseInt(value);
			}
		});
	}

	public static String read2String(InputStream in) throws IOException {
		StringBuffer sb = new StringBuffer();
		if (in != null) {
			byte[] b = new byte[1024];
			int len = -1;
			while ((len = in.read(b)) != -1) {
				String str = new String(b, 0, len);
				sb.append(str);
			}
		}
		return sb.toString();
	}
	public static String filterSensitiveWords(String s)  {
		if(s==null &&s==""){
			return "";
		}else {

			return s.replaceAll("习近平","").replaceAll("李克强","").replaceAll("王岐山","");
		}

	}

	/**
	 * Object转换字符串，空返回空字符串""，不为空返回字符串
	 * @param obj
	 * @return
	 */
	public static String objToString(Object obj){
		if (obj!=null){
			return obj.toString();
		}
		return "";
	}

	/**
	 * 截取钱length字节的字符串
	 * @param str
	 * @param length
	 * @return
	 * @throws Exception
	 */
	public static String getSubString(String str, int length) throws Exception {
		byte[] bytes = str.getBytes("UTF-8");
		int i = 0;
		int n = 0;
		for(; i < bytes.length && n < length; i++) {
		  if(i % 2 == 1) {
			n++;
		  } else {
			if(bytes[i] != 0) {
			  n++;
			}
		  }
		}
		//去掉半个汉字
		if(i % 2 == 1) {
		  if(bytes[i-1] != 0) {
			i = i -1;
		  } else {
			i = i + 1;
		  }
		}
		return new String(bytes,0,i,"UTF-8");

    }

}