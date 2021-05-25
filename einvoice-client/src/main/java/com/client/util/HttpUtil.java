package com.client.util;

import com.client.vo.InvoiceOrder;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class HttpUtil {
    private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    public static String get(String url) throws IOException {
        CloseableHttpClient httpClient = getHttpClient();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response =  httpClient.execute(httpGet);
        String entity = EntityUtils.toString(response.getEntity());
        httpClient.close();
        response.close();
        return entity;
    }

    public static String post(InvoiceOrder order, String url) throws IOException {
        String result = null;
        CloseableHttpClient httpClient = getHttpClient();
        HttpPost post = new HttpPost(url);
        String json = JsonUtil.toJSONString(order);
        logger.info("发送数据:"+json);
        StringEntity stringEntity = new StringEntity(json, "UTF-8");
        post.setEntity(stringEntity);
        logger.info("POST 请求...." + post.getURI());
        CloseableHttpResponse httpResponse = httpClient.execute(post);
        HttpEntity entity = httpResponse.getEntity();
        if (null != entity) {
            result = EntityUtils.toString(entity);
            logger.info("-------------------------------------------------------");
            logger.info(result);
            logger.info("-------------------------------------------------------");
            return result;
        }
        closeHttpClient(httpClient);
        return result;
    }

    private static CloseableHttpClient getHttpClient() {
        return HttpClients.createDefault();
    }

    private static void closeHttpClient(CloseableHttpClient client) throws IOException {
        if (client != null) {
            client.close();
        }
    }
}
