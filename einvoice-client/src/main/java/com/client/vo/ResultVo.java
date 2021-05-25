package com.client.vo;

public class ResultVo {

	private Integer code;
	private String message;
	private Object data;

	public static ResultVo success(String message){
		ResultVo resultVo = new ResultVo();
		resultVo.setCode(0);
		resultVo.setMessage(message);
		return resultVo;
	}
	public static ResultVo error(String message) {
		ResultVo resultVo = new ResultVo();
		resultVo.setCode(500);
		resultVo.setMessage(message);
		return resultVo;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
