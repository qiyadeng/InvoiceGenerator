package com.einvoicemerchant.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author Effort
 * @description 项目目录相关配置
 * @date 2020/9/22 9:47 上午
 */
@Component
@ConfigurationProperties(prefix = "sysconfig")
@PropertySource("classpath:application.yml")
public class SysConfig {
    /**
     * 发票存放根目录
     */
    private String invoicePath;
    /**
     * 发票api根接口
     */
    private String invoiceAction;
    /**
     * appid
     */
    private String appid;
    /**
     * appSecret
     */
    private String appSecret;



    public String getTempFilePath() {
        return invoicePath+"/temp/";
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getInvoiceAction() {
        return invoiceAction;
    }

    public void setInvoiceAction(String invoiceAction) {
        this.invoiceAction = invoiceAction;
    }

    public String getInvoicePath() {
        return invoicePath;
    }

    public void setInvoicePath(String invoicePath) {
        this.invoicePath = invoicePath;
    }
    /**
     * 拉取发票xml文件存放目录
     * @return
     */
    public String getInvoiceXmlPath() {
        return invoicePath+"/invoiceXmlPath/";
    }

    /**
     *税务机生成文件目录
     * @return
     */
    public String getInvoiceResultPath() {
        return invoicePath+"/invoiceResultPath/";
    }

    /**
     * 拉取发票url
     * @return
     */
    public String getPullInvoiceUrl() {
        return invoiceAction+"listInvoiceInfo";
    }

    public String getPullDownloadInvoiceUrl() {
        return invoiceAction+"pullDownloadInvoice";
    }

    /**
     * 上传税务机生成xml文件接口
     * @return
     */
    public String getUploadResultUrl() {
        return invoiceAction+"uploadInvoiceResultFile";
    }

    /**
     * 登录接口
     * @return
     */
    public String getLoginUrl() {
        return invoiceAction+"merchantLogin";
    }

    /**
     * 更改已经下载文件的状态
     * @return
     */
    public String getFileDownloadYetUrl() {
        return invoiceAction+"fileDownloadYet?fpqqlsh=";
    }

    public String getHeadByFpqqlsh() {
        return invoiceAction+"getHeadByFpqqlsh?fpqqlsh=";
    }

    public String getCancelInvoiceUrl() {
        return invoiceAction+"listCancelInvoice";
    }

    public String canceledInvoice() {
        return invoiceAction+"canceledInvoice?serialNumber=";
    }
}
