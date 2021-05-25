package com.einvoicemerchant.utils.base;

import java.util.HashMap;
import java.util.Map;

public class Message {

	public static String SUCCESS = "success";
	public static String FAILURE = "failure";

	protected String status;
	protected String message;
	protected Map<String,Object> data;

	public Message( ){

	}

	public Message(String status,String message){
		this.status=status;
		this.message=message;
	}

	public Message(String status,String message, Map<String,Object> data){
		this.status=status;
		this.message=message;
		this.data=data;
	}

	public static Message genSuccessMessage(String message){
		return new Message(SUCCESS,message);
	}

	public static Message genFailMessage(String message){
		return new Message(FAILURE,message);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

	public void setValue(String key,String value){
		if(data==null){
			data = new HashMap<String, Object>();
			data.put(key, value);
		}
		else {
			data.put(key, value);
		}
	}

	public void setValue(String key,Object value){
		if(data==null){
			data = new HashMap<String, Object>();
			data.put(key, value);
		}
		else {
			data.put(key, value);
		}
	}


}
