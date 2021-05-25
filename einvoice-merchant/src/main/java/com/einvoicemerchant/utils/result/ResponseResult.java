package com.einvoicemerchant.utils.result;

import java.util.Map;

/**
 * @author Effort
 * @description
 * @date 2020/9/22 11:22 上午
 */
public class ResponseResult<T> {
    /**
     * 返回状态码
     */
    private String status;

    /**
     * 返回消息
     */
    private String message;

    /**
     * 返回内容
     */
    private T data;

    /**
     * 分页信息
     */
    private PageInfo page;

    /**
     * 其他内容
     */
    private Map<String, Object> ext;

    public ResponseResult() {
    }

    public ResponseResult(String status, String message, T data, PageInfo page, Map<String, Object> ext) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.page = page;
        this.ext = ext;
    }
    public ResponseResult(ResultCodeEnum resultCodeEnum, T data, PageInfo page, Map<String, Object> ext) {
        this.status = resultCodeEnum.getCode();
        this.message = resultCodeEnum.getMsg();
        this.data = data;
        this.page = page;
        this.ext = ext;
    }
    public ResponseResult(ResultCodeEnum resultCodeEnum, T data) {
        this.status = resultCodeEnum.getCode();
        this.message = resultCodeEnum.getMsg();
        this.data = data;
    }
    public ResponseResult(ResultCodeEnum resultCodeEnum, T data,PageInfo page) {
        this.status = resultCodeEnum.getCode();
        this.message = resultCodeEnum.getMsg();
        this.data = data;
        this.page = page;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public PageInfo getPage() {
        return page;
    }

    public void setPage(PageInfo page) {
        this.page = page;
    }

    public Map<String, Object> getExt() {
        return ext;
    }

    public void setExt(Map<String, Object> ext) {
        this.ext = ext;
    }

    @Override
    public String toString() {
        return "ResponseResult{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", page=" + page +
                ", ext=" + ext +
                '}';
    }
}
