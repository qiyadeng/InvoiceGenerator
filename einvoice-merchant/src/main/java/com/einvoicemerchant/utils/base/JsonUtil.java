package com.einvoicemerchant.utils.base;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletResponse;


import com.einvoicemerchant.utils.constant.DateConstant;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.MappingJsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;

/**
 * Created by 双义 on 14-3-6.
 *
 */
public class JsonUtil {

    private final static Logger logger = LoggerFactory.getLogger(JsonUtil.class);


    private static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 对象转换成json
     * 如果属性没数据则生成null
     * @param json
     * @return
     */
    public static String toString(Object json){
        MappingJsonFactory f = new MappingJsonFactory();
        StringWriter sw = new StringWriter();
        try {
            JsonGenerator generator = f.createJsonGenerator(sw);
            generator.writeObject(json);
            generator.close();
        } catch (Exception e) {
            logger.warn("Exception {}",e);
        }
        return sw.toString();
    }

    /**
     * 处理带日期的类成json 也可以参考（http://wiki.fasterxml.com/JacksonFeatureModules）
     * @param json
     * @param formater（日期的格式  如  yyyy-MM-dd HH:ss:mm(default)）
     * @return
     */
    public static String toString(Object json,String formater){

        if(Strings.isNullOrEmpty(formater)){
        	formater= DateConstant.DATE_yyyyMMddHHssmm;//默认
        }
        objectMapper.setDateFormat(new SimpleDateFormat(formater));
       StringWriter sw = new StringWriter();
        try {
            JsonGenerator generator = objectMapper.getJsonFactory().createJsonGenerator(sw);
            generator.writeObject(json);
            generator.close();
        } catch (Exception e) {
            logger.warn("Exception {}",e);
        }
        return sw.toString();
    }

    /**
     * 从字符json对象获得对象实例
     * @param jsonStr
     * @param c
     * @return
     */
    public static <T>T getInstance(String jsonStr,Class<T> c){
        try {
            T t = c.newInstance();

            t = objectMapper.readValue(jsonStr, c);
            return t;
        } 
        catch (Exception e) {
        	logger.info("Json convert {} error ",c.getName());
            logger.error("Json convert error ",e);
        }
        return null;
    }

    /**
     * 用response写出json
     * @param response
     * @param json
     */
    public static void responseWrite(HttpServletResponse response,String json){
    	PrintWriter pw=null;
		try {
			pw=response.getWriter();
			pw.write(json);
		} catch (IOException e) {
			logger.warn("Exception {}",e);
		}finally{
			if(pw!=null){
				pw.flush();
				pw.close();
			}
		}
    }
}
