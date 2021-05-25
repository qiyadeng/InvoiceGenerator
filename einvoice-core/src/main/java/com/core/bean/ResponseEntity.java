package com.core.bean;

/**
 * @author Effort
 * @description
 * @date 2020/10/17 5:04 下午
 */
public class ResponseEntity<T> {
    private Integer status;
    private T data;
    private String message;

    public ResponseEntity(Integer status, T data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }
    public ResponseEntity(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
