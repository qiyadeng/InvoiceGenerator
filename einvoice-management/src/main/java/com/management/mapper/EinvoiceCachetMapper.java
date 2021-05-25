package com.management.mapper;

import com.baomidou.mybatisplus.mapper.AutoMapper;
import com.management.model.EinvoiceCachet;

/**
 * @author Effort
 * @description
 * @date 2020/10/15 5:31 下午
 */
public interface EinvoiceCachetMapper extends AutoMapper<EinvoiceCachet> {
    Long save(EinvoiceCachet cachet);
}
