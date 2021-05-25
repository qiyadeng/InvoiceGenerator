package com.management.service.impl;

import com.baomidou.framework.service.impl.SuperServiceImpl;
import com.management.mapper.TaxRateMapper;
import com.management.model.TaxRate;
import com.management.service.TaxRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Effort
 * @description
 * @date 2020/9/27 3:31 下午
 */
@Service
public class TaxRateServiceImpl extends SuperServiceImpl<TaxRateMapper, TaxRate> implements TaxRateService {
    @Autowired
    private TaxRateMapper taxRateMapper;

    @Override
    public List<TaxRate> listTaxRate() {
        return taxRateMapper.listTaxRate();
    }
}
