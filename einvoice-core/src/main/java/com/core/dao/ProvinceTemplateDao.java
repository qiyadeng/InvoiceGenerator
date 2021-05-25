package com.core.dao;

import com.core.bean.ProvinceTemplate;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * @author Effort
 * @description
 * @date 2020/10/21 9:56 上午
 */
public interface ProvinceTemplateDao extends CrudRepository<ProvinceTemplate, Long> {
    @Query("from ProvinceTemplate p where p.id=:id and p.status=1")
    ProvinceTemplate getProvinceTemplateById(@Param("id") Long id);
}
