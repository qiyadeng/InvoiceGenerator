package com.management.controller;

import com.management.service.TaxRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Effort
 * @description
 * @date 2020/9/27 4:42 下午
 */
@Controller
@RequestMapping("/taxRate")
public class TaxRateController {
    @Autowired
    private TaxRateService taxRateService;

    @RequestMapping(value = "/list",method = RequestMethod.POST)
    @ResponseBody
    public Object listTaxRate() {
        return taxRateService.listTaxRate();
    }
}
