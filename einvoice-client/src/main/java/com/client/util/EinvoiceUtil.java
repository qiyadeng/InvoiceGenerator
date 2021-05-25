package com.client.util;

import com.client.vo.InvoiceOrder;
import com.client.vo.InvoiceOrderBody;
import com.client.vo.InvoiceVo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by sdyang on 2016/10/25.
 */
public class EinvoiceUtil {

    public static InvoiceOrder buildQuery(String merchant_code,String merchant_name,String order_no,String key){
        InvoiceOrder order = new InvoiceOrder();
        order.setVersion("1.0");
        order.setMerchant_code(merchant_code);
        order.setSign_type("MD5");
        order.setMerchant_name(merchant_name);
        order.setOrder_no(order_no);
        Map<String,String> mapData = MapUtil.beanToMap(order);
        String sign = MD5Util.createSign(mapData,key);
        order.setMerchant_sign(sign);
        return order;
    }

}
