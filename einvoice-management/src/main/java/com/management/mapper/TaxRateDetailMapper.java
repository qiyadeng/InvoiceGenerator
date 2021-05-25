package com.management.mapper;

import com.baomidou.mybatisplus.mapper.AutoMapper;
import com.management.model.TaxRateDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Effort
 * @description
 * @date 2020/10/14 9:09 上午
 */
public interface TaxRateDetailMapper extends AutoMapper<TaxRateDetail> {
    List<TaxRateDetail> taxRateDetailList(@Param("q")String q);
    TaxRateDetail getByMergeCode(@Param("mergeCode")String mergeCode);
    TaxRateDetail getById(@Param("id")Long id);
}
