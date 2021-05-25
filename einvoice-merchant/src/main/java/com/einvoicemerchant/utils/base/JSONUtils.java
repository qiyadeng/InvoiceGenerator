package com.einvoicemerchant.utils.base;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

public class JSONUtils {

	private static Logger log = LoggerFactory.getLogger(JSONUtils.class);

	public static final String TAG = "JSONUtils";

	/**
	 * 空的 {@code JSON} 数据 - <code>"{}"</code>。
	 */
	public static final String EMPTY_JSON = "{}";
	/**
	 * 空的 {@code JSON} 数组(集合)数据 - {@code "[]"}。
	 */
	public static final String EMPTY_JSON_ARRAY = "[]";
	/**
	 * 默认的 {@code JSON} 日期/时间字段的格式化模式。
	 */
	// public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss SSS";
	/**
	 * {@code Google Gson} 的 <code>@Since</code> 注解常用的版本号常量 - {@code 1.0}。
	 */
	public static final double SINCE_VERSION_10 = 1.0d;
	/**
	 * {@code Google Gson} 的 <code>@Since</code> 注解常用的版本号常量 - {@code 1.1}。
	 */
	public static final double SINCE_VERSION_11 = 1.1d;
	/**
	 * {@code Google Gson} 的 <code>@Since</code> 注解常用的版本号常量 - {@code 1.2}。
	 */
	public static final double SINCE_VERSION_12 = 1.2d;
	/**
	 * {@code Google Gson} 的 <code>@Until</code> 注解常用的版本号常量 - {@code 1.0}。
	 */
	public static final double UNTIL_VERSION_10 = SINCE_VERSION_10;
	/**
	 * {@code Google Gson} 的 <code>@Until</code> 注解常用的版本号常量 - {@code 1.1}。
	 */
	public static final double UNTIL_VERSION_11 = SINCE_VERSION_11;
	/**
	 * {@code Google Gson} 的 <code>@Until</code> 注解常用的版本号常量 - {@code 1.2}。
	 */
	public static final double UNTIL_VERSION_12 = SINCE_VERSION_12;

	/**
	 * <p>
	 * <code>JSONUtils</code> instances should NOT be constructed in standard programming. Instead, the class should be used as <code>JSONUtils.fromJson("foo");</code>.
	 * </p>
	 * <p>
	 * This constructor is public to permit tools that require a JavaBean instance to operate.
	 * </p>
	 */
	public JSONUtils() {
		super();
	}

	/**
	 * 将给定的目标对象根据指定的条件参数转换成 {@code JSON} 格式的字符串。
	 * <p/>
	 * <strong>该方法转换发生错误时，不会抛出任何异常。若发生错误时，曾通对象返回 <code>"{}"</code>； 集合或数组对象返回 <code>"[]"</code> </strong>
	 * 
	 * @param target 目标对象。
	 * @param targetType 目标对象的类型。
	 * @param isSerializeNulls 是否序列化 {@code null} 值字段。
	 * @param version 字段的版本号注解。
	 * @param datePattern 日期字段的格式化模式。
	 * @param excludesFieldsWithoutExpose 是否排除未标注 {@literal @Expose} 注解的字段。
	 * @return 目标对象的 {@code JSON} 格式的字符串。
	 * @since 1.0
	 */
	public static String toJson(Object target, Type targetType, boolean isSerializeNulls, Double version, String datePattern, boolean excludesFieldsWithoutExpose, Class<?> typeClass, Object typeAdapter) {
		if (target == null)
			return EMPTY_JSON;
		GsonBuilder builder = new GsonBuilder();
		if (typeAdapter != null)
			builder.registerTypeAdapter(typeClass, typeAdapter);
		if (isSerializeNulls)
			builder.serializeNulls();
		if (version != null)
			builder.setVersion(version.doubleValue());
		if (Strings.isNullOrEmpty(datePattern))
			datePattern = DateUtils.DateTimeFormat;
		builder.setDateFormat(datePattern);
		if (excludesFieldsWithoutExpose)
			builder.excludeFieldsWithoutExposeAnnotation();
		return toJson(target, targetType, builder);
	}

	public static String toJson(Object target, Type targetType, boolean isSerializeNulls, Double version, String datePattern, boolean excludesFieldsWithoutExpose) {
		return toJson(target, targetType, isSerializeNulls, version, datePattern, excludesFieldsWithoutExpose, null, null);
	}

	/**
	 * 将给定的目标对象转换成 {@code JSON} 格式的字符串。<strong>此方法只用来转换普通的 {@code JavaBean} 对象。</strong>
	 * <ul>
	 * <li>该方法只会转换标有 {@literal @Expose} 注解的字段；</li>
	 * <li>该方法不会转换 {@code null} 值字段；</li>
	 * <li>该方法会转换所有未标注或已标注 {@literal @Since} 的字段；</li>
	 * <li>该方法转换时使用默认的 日期/时间 格式化模式 - {@code yyyy-MM-dd HH:mm:ss SSS}；</li>
	 * </ul>
	 * 
	 * @param target 要转换成 {@code JSON} 的目标对象。
	 * @return 目标对象的 {@code JSON} 格式的字符串。
	 * @since 1.0
	 */
	public static String toJson(Object target) {
		return toJson(target, null, false, null, null, true);
	}

	public static String toJson(Object target, Class<?> typeClass, Object typeAdapter) {
		return toJson(target, null, false, null, null, true, typeClass, typeAdapter);
	}

	/**
	 * 将给定的目标对象转换成 {@code JSON} 格式的字符串。<strong>此方法只用来转换普通的 {@code JavaBean} 对象。</strong>
	 * <ul>
	 * <li>该方法只会转换标有 {@literal @Expose} 注解的字段；</li>
	 * <li>该方法不会转换 {@code null} 值字段；</li>
	 * <li>该方法会转换所有未标注或已标注 {@literal @Since} 的字段；</li>
	 * </ul>
	 * 
	 * @param target 要转换成 {@code JSON} 的目标对象。
	 * @param datePattern 日期字段的格式化模式。
	 * @return 目标对象的 {@code JSON} 格式的字符串。
	 * @since 1.0
	 */
	public static String toJson(Object target, String datePattern) {
		return toJson(target, null, false, null, datePattern, true);
	}

	/**
	 * 将给定的目标对象转换成 {@code JSON} 格式的字符串。<strong>此方法只用来转换普通的 {@code JavaBean} 对象。</strong>
	 * <ul>
	 * <li>该方法只会转换标有 {@literal @Expose} 注解的字段；</li>
	 * <li>该方法不会转换 {@code null} 值字段；</li>
	 * <li>该方法转换时使用默认的 日期/时间 格式化模式 - {@code yyyy-MM-dd HH:mm:ss SSS}；</li>
	 * </ul>
	 * 
	 * @param target 要转换成 {@code JSON} 的目标对象。
	 * @param version 字段的版本号注解({@literal @Since})。
	 * @return 目标对象的 {@code JSON} 格式的字符串。
	 * @since 1.0
	 */
	public static String toJson(Object target, Double version) {
		return toJson(target, null, false, version, null, true);
	}

	/**
	 * 将给定的目标对象转换成 {@code JSON} 格式的字符串。<strong>此方法只用来转换普通的 {@code JavaBean} 对象。</strong>
	 * <ul>
	 * <li>该方法不会转换 {@code null} 值字段；</li>
	 * <li>该方法会转换所有未标注或已标注 {@literal @Since} 的字段；</li>
	 * <li>该方法转换时使用默认的 日期/时间 格式化模式 - {@code yyyy-MM-dd HH:mm:ss SSS}；</li>
	 * </ul>
	 * 
	 * @param target 要转换成 {@code JSON} 的目标对象。
	 * @param excludesFieldsWithoutExpose 是否排除未标注 {@literal @Expose} 注解的字段。
	 * @return 目标对象的 {@code JSON} 格式的字符串。
	 * @since 1.0
	 */
	public static String toJson(Object target, boolean excludesFieldsWithoutExpose) {
		return toJson(target, null, false, null, null, excludesFieldsWithoutExpose);
	}

	/**
	 * 将给定的目标对象转换成 {@code JSON} 格式的字符串。<strong>此方法只用来转换普通的 {@code JavaBean} 对象。</strong>
	 * <ul>
	 * <li>该方法不会转换 {@code null} 值字段；</li>
	 * <li>该方法转换时使用默认的 日期/时间 格式化模式 - {@code yyyy-MM-dd HH:mm:ss SSS}；</li>
	 * </ul>
	 * 
	 * @param target 要转换成 {@code JSON} 的目标对象。
	 * @param version 字段的版本号注解({@literal @Since})。
	 * @param excludesFieldsWithoutExpose 是否排除未标注 {@literal @Expose} 注解的字段。
	 * @return 目标对象的 {@code JSON} 格式的字符串。
	 * @since 1.0
	 */
	public static String toJson(Object target, Double version, boolean excludesFieldsWithoutExpose) {
		return toJson(target, null, false, version, null, excludesFieldsWithoutExpose);
	}

	/**
	 * 将给定的目标对象转换成 {@code JSON} 格式的字符串。<strong>此方法通常用来转换使用泛型的对象。</strong>
	 * <ul>
	 * <li>该方法只会转换标有 {@literal @Expose} 注解的字段；</li>
	 * <li>该方法不会转换 {@code null} 值字段；</li>
	 * <li>该方法会转换所有未标注或已标注 {@literal @Since} 的字段；</li>
	 * <li>该方法转换时使用默认的 日期/时间 格式化模式 - {@code yyyy-MM-dd HH:mm:ss SSSS}；</li>
	 * </ul>
	 * 
	 * @param target 要转换成 {@code JSON} 的目标对象。
	 * @param targetType 目标对象的类型。
	 * @return 目标对象的 {@code JSON} 格式的字符串。
	 * @since 1.0
	 */
	public static String toJson(Object target, Type targetType) {
		return toJson(target, targetType, false, null, null, false);
	}

	/**
	 * 将给定的目标对象转换成 {@code JSON} 格式的字符串。<strong>此方法通常用来转换使用泛型的对象。</strong>
	 * <ul>
	 * <li>该方法只会转换标有 {@literal @Expose} 注解的字段；</li>
	 * <li>该方法不会转换 {@code null} 值字段；</li>
	 * <li>该方法转换时使用默认的 日期/时间 格式化模式 - {@code yyyy-MM-dd HH:mm:ss SSSS}；</li>
	 * </ul>
	 * 
	 * @param target 要转换成 {@code JSON} 的目标对象。
	 * @param targetType 目标对象的类型。
	 * @param version 字段的版本号注解({@literal @Since})。
	 * @return 目标对象的 {@code JSON} 格式的字符串。
	 * @since 1.0
	 */
	public static String toJson(Object target, Type targetType, Double version) {
		return toJson(target, targetType, false, version, null, true);
	}

	/**
	 * 将给定的目标对象转换成 {@code JSON} 格式的字符串。<strong>此方法通常用来转换使用泛型的对象。</strong>
	 * <ul>
	 * <li>该方法不会转换 {@code null} 值字段；</li>
	 * <li>该方法会转换所有未标注或已标注 {@literal @Since} 的字段；</li>
	 * <li>该方法转换时使用默认的 日期/时间 格式化模式 - {@code yyyy-MM-dd HH:mm:ss SSS}；</li>
	 * </ul>
	 * 
	 * @param target 要转换成 {@code JSON} 的目标对象。
	 * @param targetType 目标对象的类型。
	 * @param excludesFieldsWithoutExpose 是否排除未标注 {@literal @Expose} 注解的字段。
	 * @return 目标对象的 {@code JSON} 格式的字符串。
	 * @since 1.0
	 */
	public static String toJson(Object target, Type targetType, boolean excludesFieldsWithoutExpose) {
		return toJson(target, targetType, false, null, null, excludesFieldsWithoutExpose);
	}

	/**
	 * 将给定的目标对象转换成 {@code JSON} 格式的字符串。<strong>此方法通常用来转换使用泛型的对象。</strong>
	 * <ul>
	 * <li>该方法不会转换 {@code null} 值字段；</li>
	 * <li>该方法转换时使用默认的 日期/时间 格式化模式 - {@code yyyy-MM-dd HH:mm:ss SSS}；</li>
	 * </ul>
	 * 
	 * @param target 要转换成 {@code JSON} 的目标对象。
	 * @param targetType 目标对象的类型。
	 * @param version 字段的版本号注解({@literal @Since})。
	 * @param excludesFieldsWithoutExpose 是否排除未标注 {@literal @Expose} 注解的字段。
	 * @return 目标对象的 {@code JSON} 格式的字符串。
	 * @since 1.0
	 */
	public static String toJson(Object target, Type targetType, Double version, boolean excludesFieldsWithoutExpose) {
		return toJson(target, targetType, false, version, null, excludesFieldsWithoutExpose);
	}

	public static <T> T fromJsonObject(JsonObject json, Class<T> clazz) {
		return fromJsonObject(json, clazz, null, null, null);
	}

	public static <T> T fromJsonObject(JsonObject json, Class<T> clazz, String datePattern) {
		return fromJsonObject(json, clazz, datePattern, null, null);
	}

	public static <T> T fromJsonObject(JsonObject json, Class<T> clazz, String datePattern, Class<?> typeClass, Object typeAdapter) {
		if (json == null || json.isJsonNull()) {
			return null;
		}
		GsonBuilder builder = new GsonBuilder();
		if (Strings.isNullOrEmpty(datePattern)) {
			datePattern = DateUtils.DateTimeFormat;
		}

		Gson gson = (typeAdapter != null) ? builder.registerTypeAdapter(typeClass, typeAdapter).create() : builder.create();
		try {
			return gson.fromJson(json, clazz);
		} catch (Exception ex) {
			log.error(TAG, json + " 无法转换为 " + clazz.getName() + " 对象!", ex);
			return null;
		}
	}

	/**
	 * 将给定的 {@code JSON} 字符串转换成指定的类型对象。
	 * 
	 * @param <T> 要转换的目标类型。
	 * @param json 给定的 {@code JSON} 字符串。
	 * @param token {@code com.google.gson.reflect.TypeToken} 的类型指示类对象。
	 * @param datePattern 日期格式模式。
	 * @return 给定的 {@code JSON} 字符串表示的指定的类型对象。
	 * @since 1.0
	 */
	public static <T> T fromJson(String json, TypeToken<T> token, String datePattern) {
		if (Strings.isNullOrEmpty(json)) {
			return null;
		}
		GsonBuilder builder = new GsonBuilder();
		if (Strings.isNullOrEmpty(datePattern)) {
			datePattern = DateUtils.DateTimeFormat;
		}
		Gson gson = builder.create();
		try {
			return gson.fromJson(json, token.getType());
		} catch (Exception ex) {
			log.error(TAG, json + " 无法转换为 " + token.getRawType().getName() + " 对象!", ex);
			return null;
		}
	}

	/**
	 * 将给定的 {@code JSON} 字符串转换成指定的类型对象。
	 * 
	 * @param <T> 要转换的目标类型。
	 * @param json 给定的 {@code JSON} 字符串。
	 * @param token {@code com.google.gson.reflect.TypeToken} 的类型指示类对象。
	 * @return 给定的 {@code JSON} 字符串表示的指定的类型对象。
	 * @since 1.0
	 */
	public static <T> T fromJson(String json, TypeToken<T> token) {
		return fromJson(json, token, null);
	}

	/**
	 * 将给定的 {@code JSON} 字符串转换成指定的类型对象。<strong>此方法通常用来转换普通的 {@code JavaBean} 对象。</strong>
	 * 
	 * @param <T> 要转换的目标类型。
	 * @param json 给定的 {@code JSON} 字符串。
	 * @param clazz 要转换的目标类。
	 * @param datePattern 日期格式模式。
	 * @return 给定的 {@code JSON} 字符串表示的指定的类型对象。
	 * @since 1.0
	 */
	public static <T> T fromJson(String json, Class<T> clazz, String datePattern, Class<?> typeClass, Object typeAdapter) {
		if (Strings.isNullOrEmpty(json)) {
			return null;
		}
		GsonBuilder builder = new GsonBuilder();
		if (Strings.isNullOrEmpty(datePattern)) {
			datePattern = DateUtils.DateTimeFormat;
		}
		builder.setDateFormat(datePattern);

		Gson gson = (typeAdapter != null) ? builder.registerTypeAdapter(typeClass, typeAdapter).create() : builder.create();
		try {
			return gson.fromJson(json, clazz);
		} catch (Exception ex) {
			log.error(TAG, json + " 无法转换为 " + clazz.getName() + " 对象!", ex);
			return null;
		}
	}

	/**
	 * 将给定的 {@code JSON} 字符串转换成指定的类型对象。<strong>此方法通常用来转换普通的 {@code JavaBean} 对象。</strong>
	 * 
	 * @param <T> 要转换的目标类型。
	 * @param json 给定的 {@code JSON} 字符串。
	 * @param clazz 要转换的目标类。
	 * @return 给定的 {@code JSON} 字符串表示的指定的类型对象。
	 * @since 1.0
	 */
	public static <T> T fromJson(String json, Class<T> clazz) {
		return fromJson(json, clazz, null, null, null);
	}

	public static <T> T fromJson(String json, Class<T> clazz, Class<?> typeClass, Object typeAdapter) {
		return fromJson(json, clazz, null, typeClass, typeAdapter);
	}

	/**
	 * 将给定的目标对象根据{@code GsonBuilder} 所指定的条件参数转换成 {@code JSON} 格式的字符串。
	 * <p/>
	 * 该方法转换发生错误时，不会抛出任何异常。若发生错误时，{@code JavaBean} 对象返回 <code>"{}"</code>； 集合或数组对象返回 <code>"[]"</code>。 其本基本类型，返回相应的基本值。
	 * 
	 * @param target 目标对象。
	 * @param targetType 目标对象的类型。
	 * @param builder 可定制的{@code Gson} 构建器。
	 * @return 目标对象的 {@code JSON} 格式的字符串。
	 * @since 1.1
	 */
	public static String toJson(Object target, Type targetType, GsonBuilder builder) {
		if (target == null)
			return EMPTY_JSON;
		Gson gson = null;
		if (builder == null) {
			gson = new Gson();
		} else {
			gson = builder.create();
		}
		String result = EMPTY_JSON;
		try {
			if (targetType == null) {
				result = gson.toJson(target);
			} else {
				result = gson.toJson(target, targetType);
			}
		} catch (Exception ex) {
			log.error(TAG, "目标对象 " + target.getClass().getName() + " 转换 JSON 字符串时，发生异常！", ex);
			if (target instanceof Collection<?> || target instanceof Iterator<?> || target instanceof Enumeration<?> || target.getClass().isArray()) {
				result = EMPTY_JSON_ARRAY;
			}
		}
		return result;
	}

	public static JsonElement toJsonElement(Object target) {
		return toJsonElement(target, null, false, null, null, false);
	}

	public static JsonElement toJsonElement(Object target, Type targetType) {
		return toJsonElement(target, targetType, false, null, null, false);
	}

	public static JsonElement toJsonElement(Object target, Type targetType, boolean isSerializeNulls, Double version, String datePattern, boolean excludesFieldsWithoutExpose) {
		return toJsonElement(target, targetType, isSerializeNulls, version, datePattern, excludesFieldsWithoutExpose, null, null);
	}

	public static JsonElement toJsonElement(Object target, Type targetType, boolean isSerializeNulls, Double version, String datePattern, boolean excludesFieldsWithoutExpose, Class<?> typeClass, Object typeAdapter) {
		if (target == null)
			return JsonNull.INSTANCE;
		GsonBuilder builder = new GsonBuilder();
		if (typeAdapter != null)
			builder.registerTypeAdapter(typeClass, typeAdapter);
		if (isSerializeNulls)
			builder.serializeNulls();
		if (version != null)
			builder.setVersion(version.doubleValue());
		if (Strings.isNullOrEmpty(datePattern))
			datePattern = DateUtils.DateTimeFormat;
		builder.setDateFormat(datePattern);
		if (excludesFieldsWithoutExpose)
			builder.excludeFieldsWithoutExposeAnnotation();
		return toJsonElement(target, targetType, builder);
	}

	public static JsonElement toJsonElement(Object target, Type targetType, GsonBuilder builder) {
		if (target == null)
			return JsonNull.INSTANCE;
		Gson gson = null;
		if (builder == null) {
			gson = new Gson();
		} else {
			gson = builder.create();
		}
		try {
			if (targetType == null) {
				return gson.toJsonTree(target);
			} else {
				return gson.toJsonTree(target, targetType);
			}
		} catch (Exception ex) {
			log.error(TAG, "目标对象 " + target.getClass().getName() + " 转换 JSON 字符串时，发生异常！", ex);
			if (target instanceof Collection<?> || target instanceof Iterator<?> || target instanceof Enumeration<?> || target.getClass().isArray()) {
				return new JsonArray();
			} else
				return JsonNull.INSTANCE;
		}
	}

	public static JsonObject parse(String json) {
		return new JsonParser().parse(json).getAsJsonObject();
	}

	public static String getElementString(JsonObject jo, String name) {
		if (jo == null)
			return null;

		JsonElement je = jo.get(name);
		return (je == null || je.isJsonNull()) ? null : je.getAsString();
	}

	public static String getElementString(JsonObject jo, String name, String subName) {
		if (jo == null)
			return null;

		JsonElement je = jo.get(name);
		if (je == null || je.isJsonNull())
			return null;
		else
			return getElementString(je.getAsJsonObject(), subName);
	}

	public static BigDecimal getElementDecimal(JsonObject jo, String name) {
		if (jo == null)
			return null;

		JsonElement je = jo.get(name);
		return (je == null || je.isJsonNull()) ? null : je.getAsBigDecimal();
	}



}