package com.management.controller;

import com.management.commons.base.BaseController;
import com.management.service.TaxRateDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 * @author Effort
 * @description
 * @date 2020/10/14 9:49 上午
 */
@Controller
@RequestMapping("/taxRateDetail")
public class TaxRateDetailController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(ProvinceTemplateController.class);

    @Autowired
    private TaxRateDetailService detailService;

    @RequestMapping(value = "/list")
    @ResponseBody
    public Object listTaxRate(String q) {
        logger.info("=============detail============");
        return detailService.taxRateDetailList(q);
    }
}
