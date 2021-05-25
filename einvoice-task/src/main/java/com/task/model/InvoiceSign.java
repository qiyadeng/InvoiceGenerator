package com.task.model;

/**
 * @author Effort
 * @description
 * @date 2020/10/15 3:10 下午
 */
public class InvoiceSign {
    /**
     * 需要签章的发票的路径
     */
    private String sourcePath;
    /**
     * 签章后的发票路径
     */
    private String targetPath;
    /**
     * 证书路径
     */
    private String certificatePath;
    /**
     * 私钥
     */
    private String password;
    /**
     * 签名的原因，显示在pdf签名属性
     */
    private String reason;
    /**
     * 签名的地点，显示在pdf签名属性
     */
    private String location;
    /**
     * 图章的图片路径
     */
    private String stampPath;

    public String getSourcePath() {
        return sourcePath;
    }

    public void setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
    }

    public String getTargetPath() {
        return targetPath;
    }

    public void setTargetPath(String targetPath) {
        this.targetPath = targetPath;
    }

    public String getCertificatePath() {
        return certificatePath;
    }

    public void setCertificatePath(String certificatePath) {
        this.certificatePath = certificatePath;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStampPath() {
        return stampPath;
    }

    public void setStampPath(String stampPath) {
        this.stampPath = stampPath;
    }
}
