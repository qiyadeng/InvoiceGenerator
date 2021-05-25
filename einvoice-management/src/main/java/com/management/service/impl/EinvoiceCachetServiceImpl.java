package com.management.service.impl;

import com.baomidou.framework.service.impl.SuperServiceImpl;
import com.management.mapper.EinvoiceCachetMapper;
import com.management.model.EinvoiceCachet;
import com.management.service.IEinvoiceCachetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author Effort
 * @description
 * @date 2020/10/15 5:33 下午
 */
@Service
public class EinvoiceCachetServiceImpl extends SuperServiceImpl<EinvoiceCachetMapper, EinvoiceCachet> implements IEinvoiceCachetService {
    @Autowired
    private EinvoiceCachetMapper mapper;

    @Override
    public Long save(EinvoiceCachet cachet) {
        cachet.setCreateTime(new Date());
        cachet.setStatus(1);
        return mapper.save(cachet);
    }
}
