package com.service.controller;

import com.core.bean.ApiCodeEnum;
import com.core.bean.ApiOutput;
import com.core.bean.EinvoiceHead;
import com.core.bean.RequestXml;
import com.core.constant.EinvoiceStatus;
import com.core.service.EinvoiceService;
import com.core.service.RequestXmlService;
import com.core.util.ConstantUtil;
import com.core.util.JsonUtil;
import com.service.service.InvoiceService;
import com.service.vo.InvoiceOrder;
import com.service.vo.InvoiceOrderResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.List;

@Controller
public class EinvoiceController {
    private static Logger logger = Logger.getLogger(InvoiceController.class);
    @Autowired
    private InvoiceService invoiceService;
    //保存发票信息
    @RequestMapping(value = "/sendinvoice", method = { RequestMethod.POST })
    @ResponseBody
    public String getInvoiceInfo(@RequestBody String  orderjson){
        InvoiceOrder invoiceOrder = JsonUtil.parseObject(orderjson,InvoiceOrder.class);
        InvoiceOrderResponse response =invoiceService.saveInvoiceInfo(invoiceOrder,false);
        return JsonUtil.toJSONString(response);
    }
    //直接提交开票
    @RequestMapping(value = "/directSubmission", method = { RequestMethod.POST })
    @ResponseBody
    public String directSubmission(@RequestBody String  orderjson){
        InvoiceOrder invoiceOrder = JsonUtil.parseObject(orderjson,InvoiceOrder.class);
        InvoiceOrderResponse response =invoiceService.saveInvoiceInfo(invoiceOrder,true);
        return JsonUtil.toJSONString(response);
    }
    //查询
    @RequestMapping(value = "/queryinvoice", method = { RequestMethod.POST })
    @ResponseBody
    public String query(@RequestBody String  orderjson){
        InvoiceOrder invoiceOrder = JsonUtil.parseObject(orderjson,InvoiceOrder.class);
        InvoiceOrderResponse response =invoiceService.query(invoiceOrder);
        return JsonUtil.toJSONString(response);
    }
}
