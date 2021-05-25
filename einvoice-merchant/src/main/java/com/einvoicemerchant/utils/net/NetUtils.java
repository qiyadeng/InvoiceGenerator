package com.einvoicemerchant.utils.net;

import com.einvoicemerchant.utils.base.JSONUtils;
import com.einvoicemerchant.utils.convert.EncodingUtils;
import com.einvoicemerchant.utils.generate.GenerateUtils;
import com.google.common.base.Strings;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class NetUtils {

	private static Logger log = LoggerFactory.getLogger(NetUtils.class);

	public static boolean downloadFileV2(URL url, String savePath, String name,String logTitle) throws IOException {
		try{
			if (logTitle != null) {
				log.info(String.format("httpDownload(%s): url=[%s], data=[%s] start", logTitle, url, savePath + name));
			}
			File file = new File(savePath, name);
			byte[] buffer = new byte[1024 * 8];
			int read;
			BufferedInputStream bin = new BufferedInputStream(url.openStream());
			BufferedOutputStream bout = new BufferedOutputStream(new FileOutputStream(file));
			while ((read = bin.read(buffer)) > -1) {
				bout.write(buffer, 0, read);
			}
			bout.flush();
			bout.close();
			if (logTitle != null) {
				log.info(String.format("httpDownload(%s): url=[%s], data=[%s] done!",  logTitle, url,savePath+name));
			}
			return true;
			}catch (Exception e) {
				log.error("文件下载失败,url = [%s]",e,url);
			}
			return false;
	}


	public static String getPost( String url, Map<String, String> params, String logTitle) {
		String guid = GenerateUtils.getUUID();
		if (logTitle != null)
			log.info(String.format("httpPost(%s)(%s): url=[%s], data=[%s]", guid, logTitle, url, params));

		HttpClient client = HttpClientBuilder.create().build();
		String resp = "";
		HttpPost httpPost = new HttpPost(url);
		if (params != null && params.size() > 0) {
			List<NameValuePair> formParams = new ArrayList<NameValuePair>();
			Iterator<Map.Entry<String, String>> itr = params.entrySet().iterator();
			while (itr.hasNext()) {
				Map.Entry<String, String> entry = itr.next();
				formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
			try {
				UrlEncodedFormEntity postEntity = new UrlEncodedFormEntity(formParams, EncodingUtils.UTF8_ENCODING);
				httpPost.setEntity(postEntity);
			}catch (Exception ex){
				log.error("Error exception", ex);
				ex.printStackTrace();
			}
		}
		CloseableHttpResponse response = null;
		try {
			response = (CloseableHttpResponse) client.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK) {
				try {
					resp = EntityUtils.toString(response.getEntity(), EncodingUtils.UTF8_ENCODING);

					if (logTitle != null)
						log.info(String.format("httpPost(%s)(%s) success: url=[%s], result=[%s]", guid, logTitle, url, resp));
				} catch (Exception e) {
					log.error(String.format("httpPost(%s)(%s) fail: url=[%s]", guid, logTitle, url), e);
				}
			} else {
				log.error(String.format("httpPost(%s)(%s) fail: url=[%s], statusCode=[%d]", guid, logTitle, url, statusCode));
			}
		} catch (Exception e) {
			log.error("Error exception", e);
			e.printStackTrace();
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					// log
					log.error("Error exception", e);
					e.printStackTrace();
				}
			}
		}
		return resp;
	}

	public static JsonObject httpGet(String token, String url,String logTitle) {
		JsonObject jsonObject = null;
		String result = httpGetString(token, url, logTitle);
		if (!Strings.isNullOrEmpty(result)) {
			jsonObject = JSONUtils.parse(result);
		}
		return jsonObject;
	}

	public static String httpGetString(String token,String url, String logTitle) {
		String guid = GenerateUtils.getUUID();

		if (logTitle != null)
			log.info(String.format("httpGet(%s)(%s): url=[%s]", guid, logTitle, url));

		String strResult = null;
		try {
			HttpClient client = HttpClientBuilder.create().build();
			// 发送get请求
			HttpGet request = new HttpGet(url);
			request.addHeader("token",token);
			HttpResponse response = client.execute(request);
			/** 请求发送成功，并得到响应 **/
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK) {
				/** 读取服务器返回过来的json字符串数据 **/
				strResult = EntityUtils.toString(response.getEntity(), EncodingUtils.UTF8_ENCODING);
				if (logTitle != null)
					log.info(String.format("httpGet(%s)(%s) success: url=[%s], result=[%s]", guid, logTitle, url, strResult));
			} else {
				log.error(String.format("httpGet(%s)(%s) fail: url=[%s], statusCode=[%d]", guid, logTitle, url, statusCode));
			}
		} catch (IOException e) {
			log.error(String.format("httpGet(%s)(%s) fail: url=[%s]", guid, logTitle, url), e);
		}
		return strResult;
	}
}
