package com.client.util;


import com.client.constants.InvoiceConstants;
import com.client.constants.InvoiceParamKeys;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created by sdyang on 2016/10/8.
 */
public class MD5Util {

    private static Logger logger = LoggerFactory.getLogger(MD5Util.class);


    /**
     * 创建md5摘要,规则是:按参数名称a-z排序,遇到空值的参数不参加签名。
     */
    public static String createSign(Map<String, String> map,String key) {
        TreeMap<String, String> treemap = new TreeMap<String, String>(map);
        StringBuffer sb = new StringBuffer();
        Set<Map.Entry<String, String>> es = treemap.entrySet();
        Iterator<Map.Entry<String, String>> it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            if (StringUtil.isNotEmpty(v) && !InvoiceParamKeys.MERCHANT_SIGN.equals(k) && !InvoiceParamKeys.KEY.equals(k)
                    &&!InvoiceParamKeys.INVOICE_ORDERBODY_LIST.equals(k)&&!InvoiceParamKeys.SERVER_SIGN.equals(k)) {
                sb.append(k + InvoiceConstants.SYMBOL_EQUAL + v + InvoiceConstants.SYMBOL_AND);
            }
        }
        String requestParams = sb.append(InvoiceParamKeys.KEY + InvoiceConstants.SYMBOL_EQUAL + key).toString();
        logger.info("==========original signData==========：" + requestParams);
        String sign = sign(requestParams);

        return sign.toUpperCase();
    }

    public static boolean verifySignature(String data, String signature) throws Exception {
        logger.info("-----------验证签名-------------");
        String localSign = null;
        try {
            // 签名
            localSign = DigestUtils.md5Hex(data).toLowerCase();
            if (!signature.equalsIgnoreCase(localSign)) {
                logger.error("签名串：" + data.toString());
                logger.error("本地签名：" + localSign);
                logger.error("返回签名：" + signature);
                throw new Exception("验签失败！");
            }
            // 加密
            logger.info("-----------验签成功！-------------");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String sign(String data) {
        logger.info("-----------报文签名-------------");
        String ciphertext = null;
        try {
            // 签名
            StringBuilder buff = new StringBuilder(data);
            ciphertext = DigestUtils.md5Hex(buff.toString()).toLowerCase();
            // 加密
            logger.info("密文：" + ciphertext);
            logger.info("-----------签名成功！-------------");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ciphertext;
    }
    public static String getMD5New(String str) {

        String md5str = "";
        try {
            //1 创建一个提供信息摘要算法的对象，初始化为md5算法对象
            MessageDigest md = MessageDigest.getInstance("MD5");

            //2 将消息变成byte数组
            byte[] input = str.getBytes("utf-8");

            //3 计算后获得字节数组,这就是那128位了
            byte[] buff = md.digest(input);

            //4 把数组每一字节（一个字节占八位）换成16进制连成md5字符串
            md5str = bytesToHex(buff);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return md5str;
    }
    public static String bytesToHex(byte[] bytes) {
        StringBuffer md5str = new StringBuffer();
        //把数组每一字节换成16进制连成md5字符串
        int digital;
        for (int i = 0; i < bytes.length; i++) {
            digital = bytes[i];

            if(digital < 0) {
                digital += 256;
            }
            if(digital < 16){
                md5str.append("0");
            }
            md5str.append(Integer.toHexString(digital));
        }
        return md5str.toString().toUpperCase();
    }
}
