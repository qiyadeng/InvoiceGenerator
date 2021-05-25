package com.service.util;


import com.service.constants.InvoiceConstants;
import com.service.constants.SignType;
import com.service.vo.InvoiceOrderResponse;

public class ResponseUtil {

    public static InvoiceOrderResponse buildResponse(){
        InvoiceOrderResponse response = new InvoiceOrderResponse();
        response.setCharset(InvoiceConstants.CHARSET_UTF8);
        response.setSign_type(SignType.MD5);
        response.setVersion(InvoiceConstants.VERSION);
        return response;
    }
}
