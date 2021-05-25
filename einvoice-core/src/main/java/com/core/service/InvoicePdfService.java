package com.core.service;

import com.core.bean.InvoicePdf;

/**
 * @author Effort
 * @description
 * @date 2020/10/21 9:30 上午
 */
public interface InvoicePdfService {
    public InvoicePdf save(InvoicePdf pdf);
    public InvoicePdf findByFileName(String fileName);
}
