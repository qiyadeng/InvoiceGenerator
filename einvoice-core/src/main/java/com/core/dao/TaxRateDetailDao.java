package com.core.dao;

import com.core.bean.TaxRateDetail;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author Effort
 * @description
 * @date 2020/10/14 9:30 上午
 */
public interface TaxRateDetailDao extends CrudRepository<TaxRateDetail, Long> {
    @Query("from TaxRateDetail")
    List<TaxRateDetail> taxRateDetailList();
    @Query(value = "from TaxRateDetail t where t.mergeCode=:mergeCode")
    TaxRateDetail getTaxMergeCode(@Param("mergeCode") String mergeCode);
}
