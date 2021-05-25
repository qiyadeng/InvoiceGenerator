package com.einvoicemerchant.utils.result;

/**
 * @author Effort
 * @description
 * @date 2020/9/22 11:24 上午
 */
public enum ResultCodeEnum {
    SUCCESS("200","成功"),
    NOT_FOUND("204","无返回内容"),
    FAIL("1", "失败"),
    ERROR("500","系统出现未知错误");

    private final String code;
    private final String msg;

    ResultCodeEnum(String code, String message) {
        this.code = code;
        this.msg = message;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
