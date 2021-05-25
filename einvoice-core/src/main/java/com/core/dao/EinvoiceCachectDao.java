package com.core.dao;

import com.core.bean.EinvoiceCachet;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


/**
 * @author Effort
 * @description
 * @date 2020/10/15 5:27 下午
 */
public interface EinvoiceCachectDao  extends CrudRepository<EinvoiceCachet, Long> {
    @Query("from EinvoiceCachet e where e.id=:id and e.status=1")
    EinvoiceCachet findById(@Param("id") Long id);
}
