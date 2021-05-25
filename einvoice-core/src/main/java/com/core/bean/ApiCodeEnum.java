package com.core.bean;

/**
 * @author Effort
 * @description
 * @date 2020/10/26 10:54 上午
 */
public enum ApiCodeEnum {
    SUCCESS(0, "成功"),
    BUSY(-1, "繁忙"),
    FAIL(1, "失败"),
    LOCK_FAIL(2, "资源锁定失败"),
    OPERATION_FAST(3, "操作频繁"),
    CAPTCHA_SEND_FAST(301, "验证码发送频繁"),
    ADDRESS_PARSE_ERROR(302, "地址解析错误"),
    NOT_EXIST(400, "数据不存在"),
    EXIST(401, "数据已存在"),
    PARAM_ERROR(402, "参数错误"),
    CAPTCHA_ERROR(403, "验证码错误"),
    DEPRECATED(411, "接口废弃"),
    FINISHED(412, "已完成"),
    LOGIN_FAIL(500, "登录失败"),
    NEED_LOGIN(501, "需登录"),
    TOKEN_FAIL(502, "tokens失效"),
    SIGN_FAIL(601, "签名错误"),
    UPLOAD_MAX_SIZE(1000, "超过最大上传"),
    MOBILE_USED(507, "手机号已使用"),
    ACCOUNT_FROZEN(503, "服务不可用"),
    ACCOUNT_WARNING(506, "服务出错");

    private final int code;
    private final String msg;

    ApiCodeEnum(int code, String message) {
        this.code = code;
        this.msg = message;
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.msg;
    }
}
