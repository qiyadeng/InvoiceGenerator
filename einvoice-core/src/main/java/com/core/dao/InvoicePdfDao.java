package com.core.dao;

import com.core.bean.InvoicePdf;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * @author Effort
 * @description
 * @date 2020/10/21 9:26 上午
 */
public interface InvoicePdfDao extends CrudRepository<InvoicePdf, Long> {
    @Query("from InvoicePdf i where i.status=1 and i.uuid=:fileName")
    public InvoicePdf findByFileName(@Param("fileName") String fileName);
}
