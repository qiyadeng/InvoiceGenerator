package com.core.bean;

import com.core.util.JSONUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.base.Strings;
import com.google.gson.JsonObject;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiOutput {
	private int code;
	private String msg;
	private Object data;
	private String sign;

	/*
	 * success
	 */
	public ApiOutput() {
		this.initSuccess(null);
	}

	/*
	 * success
	 */
	public ApiOutput(Object data) {
		this.initSuccess(data);
	}

	/*
	 * success
	 */
	public ApiOutput(JsonObject data) {
		this.initSuccess(JSONUtils.toJson(data));
	}

	public ApiOutput(int code) {
		this.init(code, null);
	}

	public ApiOutput(int code, String message) {
		this.init(code, message);
	}

	public ApiOutput(ApiCodeEnum code) {
		this.init(code.getCode(), null);
	}

	public ApiOutput(ApiCodeEnum code, String message) {
		this.init(code.getCode(), message);
	}

	public void fail(ApiCodeEnum code, String message) {
		this.init(code.getCode(), message);
	}

	private void init(int code, String message) {
		this.code = code;
		this.msg = message;
		this.data = null;
		if (Strings.isNullOrEmpty(this.msg)) {
			if (this.code == ApiCodeEnum.FAIL.getCode())
				this.msg = "出错了";
			else if (this.code == ApiCodeEnum.NOT_EXIST.getCode())
				this.msg = "数据不存在";
			else if (this.code == ApiCodeEnum.EXIST.getCode())
				this.msg = "数据已存在";
			else if (this.code == ApiCodeEnum.PARAM_ERROR.getCode())
				this.msg = "参数错误";
			else if (this.code == ApiCodeEnum.CAPTCHA_ERROR.getCode())
				this.msg = "验证码错误";
			else if (this.code == ApiCodeEnum.TOKEN_FAIL.getCode())
				this.msg = "操作超时";
			else if (this.code == ApiCodeEnum.NEED_LOGIN.getCode())
				this.msg = "请先登录";
		}
	}

	private void initSuccess(Object data) {
		this.code = ApiCodeEnum.SUCCESS.getCode();
		this.msg = null;
		this.data = data;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	@JsonIgnore
	@Override
	public String toString() {
		return String.format("ApiOutput [code=%s, msg=%s, data=%s, sign=%s]", code, msg, data, sign);
	}

	@JsonIgnore
	public boolean isSuccess() {
		return (this.code == ApiCodeEnum.SUCCESS.getCode());
	}
}
