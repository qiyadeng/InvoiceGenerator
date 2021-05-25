package com.task.task;

import com.task.service.EinvoiceXmlPdfService;
import com.google.zxing.WriterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

/**
 * 根据数据库表里的发票信息生成电子发票
 * Created by sdyang on 2016/10/7.
 */
@Component
public class XmlPdfTask {
    @Autowired
    private EinvoiceXmlPdfService xmlPdfService;

    /**
     * 生成xml
     */
    @Scheduled(fixedRate = 5000)
    public void genXml(){
        xmlPdfService.genXml();
    }

    /**
     * 更新xml信息
     */
    @Scheduled(fixedRate = 3000)
    public void readXml() {
        xmlPdfService.readXml();
    }
    /**
     * 生成pdf
     */
    @Scheduled(fixedRate = 5000)
    public void genPdf(){
        xmlPdfService.genPdf();
    }
}
