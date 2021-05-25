package com.core.service;


import com.core.bean.TaxRateDetail;

import java.util.List;

/**
 * @author Effort
 * @description
 * @date 2020/10/14 9:12 上午
 */
public interface TaxRateDetailService {
    List<TaxRateDetail> listTaxRateDetail();
    TaxRateDetail getByMergeCode(String mergeCode);
}
