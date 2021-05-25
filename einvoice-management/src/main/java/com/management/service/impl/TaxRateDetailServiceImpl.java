package com.management.service.impl;

import com.baomidou.framework.service.impl.SuperServiceImpl;
import com.management.mapper.TaxRateDetailMapper;
import com.management.model.TaxRateDetail;
import com.management.service.TaxRateDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Effort
 * @description
 * @date 2020/10/14 9:37 上午
 */
@Service
public class TaxRateDetailServiceImpl extends SuperServiceImpl<TaxRateDetailMapper, TaxRateDetail> implements TaxRateDetailService {
    @Autowired
    private TaxRateDetailMapper mapper;

    @Override
    public List<TaxRateDetail> taxRateDetailList(String q) {
        return mapper.taxRateDetailList(q);
    }

    @Override
    public TaxRateDetail getByMergeCode(String mergeCode) {
        return mapper.getByMergeCode(mergeCode);
    }

    @Override
    public TaxRateDetail getById(Long id) {
        return mapper.getById(id);
    }
}
