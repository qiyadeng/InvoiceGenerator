package com.service.controller;

import com.core.bean.TaxRateDetail;
import com.core.service.TaxRateDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author Effort
 * @description
 * @date 2020/10/14 9:20 上午
 */
@Controller
@RequestMapping("/taxRateDetail")
public class TaxRateDetailController {
    @Autowired
    private TaxRateDetailService detailService;

    @RequestMapping("/listTaxRateDetail")
    @ResponseBody
    public List<TaxRateDetail> listTaxRateDetail() {
        return detailService.listTaxRateDetail();
    }
}
