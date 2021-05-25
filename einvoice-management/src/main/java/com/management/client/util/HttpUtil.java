package com.management.client.util;

import com.management.client.vo.InvoiceOrder;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by sdyang on 2016/10/25.
 */
public class HttpUtil {

    public static String get(String url) {
        String result = null;
        CloseableHttpClient client = getHttpClient();
        HttpGet httpGet = new HttpGet(url);
        try {
            CloseableHttpResponse execute = client.execute(httpGet);
            HttpEntity entity = execute.getEntity();
            result =  EntityUtils.toString(entity);
        } catch (IOException e) {
            e.printStackTrace();
        }
         return result;
    }


    public static String  post(InvoiceOrder order, String url){
        String result = null;
        CloseableHttpClient httpClient = getHttpClient();
        try {
            HttpPost post = new HttpPost(url);
            String json = JsonUtil.toJSONString(order);
            System.out.println("发送的数据："+json);
            StringEntity stringEntity = new StringEntity(json,"UTF-8");
            post.setEntity(stringEntity);
            System.out.println("POST 请求...." + post.getURI());
            //执行请求
            CloseableHttpResponse httpResponse = httpClient.execute(post);
            try{
                HttpEntity entity = httpResponse.getEntity();
                if (null != entity){
                    result = EntityUtils.toString(entity);
                    System.out.println("-------------------------------------------------------");
                    System.out.println(result);
                    System.out.println("-------------------------------------------------------");
                }
            } finally{
                httpResponse.close();
            }

        } catch( UnsupportedEncodingException e){
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally{
            try{
                closeHttpClient(httpClient);
            } catch(Exception e){
                e.printStackTrace();
            }
        }
        return result;
    }

    private static CloseableHttpClient getHttpClient(){
        return HttpClients.createDefault();
    }

    private static void closeHttpClient(CloseableHttpClient client) throws IOException {
        if (client != null){
            client.close();
        }
    }

}
