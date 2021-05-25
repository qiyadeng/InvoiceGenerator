package com.core.service.impl;

import com.core.bean.InvoicePdf;
import com.core.dao.InvoicePdfDao;
import com.core.service.InvoicePdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author Effort
 * @description
 * @date 2020/10/21 9:30 上午
 */
@Service
@Transactional
public class InvoicePdfServiceImpl implements InvoicePdfService {
    @Autowired
    private InvoicePdfDao pdfDao;

    @Override
    public InvoicePdf save(InvoicePdf pdf) {
        pdf.setCreateTime(new Date());
        pdf.setStatus(1);
        return pdfDao.save(pdf);
    }

    @Override
    public InvoicePdf findByFileName(String fileName) {

        return pdfDao.findByFileName(fileName);
    }
}
