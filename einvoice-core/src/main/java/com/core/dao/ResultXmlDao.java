package com.core.dao;

import com.core.bean.InvoicePdf;
import com.core.bean.ResultXml;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * @author Effort
 * @description
 * @date 2020/10/21 9:26 上午
 */
public interface ResultXmlDao extends CrudRepository<ResultXml, Long> {
    @Query("from  ResultXml r where r.status=1 and r.uuid=:fileName")
    ResultXml  findByFileName(@Param("fileName") String fileName);
}
