package com.task.service;

import com.core.bean.*;
import com.core.constant.EinvoiceStatus;
import com.core.service.*;
import com.core.util.ConstantUtil;
import com.google.zxing.WriterException;
import com.itextpdf.text.DocumentException;
import com.task.util.EmailUtil;
import com.task.util.FileUtil;
import com.task.util.PdfUtil;
import com.task.util.XmlUtil;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class EinvoiceXmlPdfService {
    private static Logger logger = LoggerFactory.getLogger(EinvoiceXmlPdfService.class);
    @Autowired
    private EinvoiceService einvoiceService;
    @Autowired
    private ResultXmlService resultXmlService;
    @Autowired
    private TaxRateDetailService detailService;
    @Autowired
    private RequestXmlService requestXmlService;
    @Autowired
    private InvoicePdfService pdfService;
    @Autowired
    private UserService userService;
    @Value("${insideServiceUrl}")
    private String insideServiceUrl;
    @Autowired
    private EinvoiceCachetService cachetService;
    @Autowired
    private ProvinceTemplateService templateService;
    @Value("${einvoiceDir}")
    private String einvoiceDir;

    /**
     * 生成开票XML文件
     */
    public void genXml(){
        List<EinvoiceHead> headList = einvoiceService.getByStatus(EinvoiceStatus.RECEVICE_SUCCESS);
        if(headList == null || headList.size() == 0) return;
        String date =new SimpleDateFormat("yyyyMMdd").format(new Date());
        for(EinvoiceHead head : headList){
            String serialNumber = head.getFpqqlsh();
            String filename  = ConstantUtil.getRequestXmlSavePath(einvoiceDir,date,serialNumber);
            if(XmlUtil.createXML(head,filename)) {
                RequestXml xml = requestXmlService.findByFileName(head.getFpqqlsh());
                if (null == xml) {
                    RequestXml requestXml = new RequestXml();
                    requestXml.setUrl(ConstantUtil.getRequestXml(date,serialNumber));
                    requestXml.setUuid(serialNumber);
                    requestXmlService.save(requestXml);
                }
                einvoiceService.updateEinvoiceStatus(head.getPk_einvoicehead(), EinvoiceStatus.GENXML_SUCCESS);
            }
        }
    }

    /**
     * 读取开票结果，更新发票信息
     */
    public void readXml() {
        List<EinvoiceHead> headList = einvoiceService.getByStatus(EinvoiceStatus.GENXML_SUCCESS);
        if (headList == null || headList.size() == 0) return;
        try {
            for (EinvoiceHead head : headList) {
                String serialNumber = head.getFpqqlsh();
                ResultXml resultXml = resultXmlService.getByFileName(serialNumber);
                if (null != resultXml) {
                    String filename= ConstantUtil.getResultXmlRootPath(einvoiceDir)+resultXml.getUrl();
                    if (XmlUtil.readXML(filename, head)) {
                        if ("0000".equals(head.getReturncode())) {
                            head.setStatus(EinvoiceStatus.BILLING_SUCCESS.getIndex());
                        } else {
                            head.setStatus(EinvoiceStatus.BILLING_FIAL.getIndex());
                        }
                        einvoiceService.updateEinvoiceHead(head);//更新发票信息
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("error message:{}", e);
        }
    }

    //生成PDF文件
    public void genPdf()  {
        List<EinvoiceHead> headList = einvoiceService.getByStatus(EinvoiceStatus.BILLING_SUCCESS);
        if(headList == null || headList.size() == 0) return;
        String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
        for(EinvoiceHead head:headList){
            String serialNumber = head.getFpqqlsh();
            String merchantCode = head.getMerchant_code();
            if (null == merchantCode)showMessage("当前发票中无商户号");
            User user = userService.getByMerchant_code(merchantCode);
            if (null == user)showMessage("未找到该商户");
            Long cachetId = user.getEinvoiceCachetId();
            if (null == cachetId) showMessage("该商户未设置公章");
            EinvoiceCachet einvoiceCachet = cachetService.findById(cachetId);
            ProvinceTemplate template = templateService.findById(user.getTemplateId());
            String  filename = ConstantUtil.getPdfSavePath(einvoiceDir,date,serialNumber);
            if(FileUtil.isExist(filename)) {
                String templateFile = ConstantUtil.getTemplateRootPath(einvoiceDir)+template.getTemplateUrl();
                String cachet = ConstantUtil.getCachetRootPath(einvoiceDir)+einvoiceCachet.getUrl();
                try {
                    PdfUtil.createPDF(head, templateFile, filename,cachet,detailService);
                } catch (Exception e) {
                    logger.error("pdf生成失败,失败原因:{}",e.getMessage());
                    e.printStackTrace();
                }
                einvoiceService.updateEinvoiceStatus(head.getPk_einvoicehead(), EinvoiceStatus.GENPDF_SUCCESS);//更新发票状态
                //保存pdf文件路径
                InvoicePdf invoicePdf = new InvoicePdf();
                invoicePdf.setUuid(serialNumber);
                invoicePdf.setUrl(ConstantUtil.getPdfPath(date,serialNumber));
                pdfService.save(invoicePdf);
                try {
                    EmailUtil.sendEmail(insideServiceUrl,serialNumber);
                } catch (IOException e) {
                    e.printStackTrace();
                    logger.error("邮件发送失败,失败原因：{}",e.getMessage());
                }
            }
        }
    }
    public static void showMessage(String message) {
        logger.error(message);
        throw new IllegalArgumentException(message);
    }
}
