package com.core.service.impl;

import com.core.bean.TaxRateDetail;
import com.core.dao.TaxRateDetailDao;
import com.core.service.TaxRateDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Effort
 * @description
 * @date 2020/10/14 9:12 上午
 */
@Service
@Transactional
public class TaxRateDetailServiceImpl implements TaxRateDetailService {
    @Autowired
    private TaxRateDetailDao detailDao;

    @Override
    public List<TaxRateDetail> listTaxRateDetail() {
        return detailDao.taxRateDetailList();
    }

    @Override
    public TaxRateDetail getByMergeCode(String mergeCode) {
        return detailDao.getTaxMergeCode(mergeCode);
    }
}
