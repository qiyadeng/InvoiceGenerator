package com.management.service;

import com.baomidou.framework.service.ISuperService;
import com.management.model.TaxRate;

import java.util.List;

/**
 * @author Effort
 * @description
 * @date 2020/9/27 3:30 下午
 */
public interface TaxRateService extends ISuperService<TaxRate> {
    List<TaxRate> listTaxRate();
}
