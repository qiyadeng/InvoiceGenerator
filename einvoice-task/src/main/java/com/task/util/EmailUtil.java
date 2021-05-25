package com.task.util;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;

/**
 * @author Effort
 * @description
 * @date 2020/10/22 10:44 上午
 */
public class EmailUtil {
    /**
     * 发送邮件
     * @param insideServiceUrl service服务内网链接
     * @param serialNumber 发票流水号
     * @throws IOException
     */
    public static void sendEmail(String insideServiceUrl,String serialNumber) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(insideServiceUrl+"email/sendAttachmentMailFile?fplsh=" + serialNumber);
        httpClient.execute(httpGet);
    }
}
