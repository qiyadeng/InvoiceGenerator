package com.management.service;

import com.baomidou.framework.service.ISuperService;
import com.management.model.TaxRateDetail;

import java.util.List;

/**
 * @author Effort
 * @description
 * @date 2020/10/14 9:37 上午
 */
public interface TaxRateDetailService extends ISuperService<TaxRateDetail> {
    List<TaxRateDetail> taxRateDetailList(String q);
    TaxRateDetail getByMergeCode(String mergeCode);
    TaxRateDetail getById(Long id);
}
