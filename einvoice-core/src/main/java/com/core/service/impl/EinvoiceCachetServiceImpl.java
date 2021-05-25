package com.core.service.impl;

import com.core.bean.EinvoiceCachet;
import com.core.dao.EinvoiceCachectDao;
import com.core.service.EinvoiceCachetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Effort
 * @description
 * @date 2020/10/16 5:02 下午
 */
@Service
@Transactional
public class EinvoiceCachetServiceImpl implements EinvoiceCachetService {
    @Autowired
    private EinvoiceCachectDao cachectDao;

    @Override
    public EinvoiceCachet findById(Long id) {
        return cachectDao.findById(id);
    }
}
