package com.management.mapper;

import com.baomidou.mybatisplus.mapper.AutoMapper;
import com.management.model.TaxRate;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Effort
 * @description 增值税税率表
 * @date 2020/9/27 2:32 下午
 */
public interface TaxRateMapper extends AutoMapper<TaxRate> {
    List<TaxRate> listTaxRate();
    void createTaxRate(TaxRate taxRate);
    void updateTaxRate(TaxRate taxRate);
    void deleteTaxRateById(@Param("id") Long id);
}
