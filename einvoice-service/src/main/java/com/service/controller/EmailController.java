package com.service.controller;

import com.core.bean.EinvoiceHead;
import com.core.bean.InvoicePdf;
import com.core.service.EinvoiceService;
import com.core.service.InvoicePdfService;
import com.core.util.ConstantUtil;
import com.service.service.EmailService;
import com.service.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

/**
 * @author Effort
 * @description
 * @date 2020/10/14 4:51 下午
 */
@Controller
@RequestMapping("/email")
public class EmailController {
    @Autowired
    private EmailService emailService;
    @Value("${email.tamplate}")
    private String emailTemplate;
    @Value("${serviceUrl}")
    private String serviceUrl;
    @Value("${einvoiceDir}")
    private String einvoiceDir;
    @Autowired
    private EinvoiceService einvoiceService;
    @Autowired
    private InvoicePdfService pdfService;

    @RequestMapping("/sendAttachmentMail")
    @ResponseBody
    public  boolean sendAttachmentMail (String to,String subject,String content) {
        emailService.sendAttachmentMail(to,subject,content);
        return true;
    }
    @RequestMapping("/sendAttachmentMailFile")
    @ResponseBody
    public  boolean sendAttachmentMailFile (String fplsh) {
        List<EinvoiceHead> heads = einvoiceService.getByFpqqlsh(fplsh);
        if (heads == null || heads.size() != 1) {
            return false;
        }
        EinvoiceHead head = heads.get(0);
        String gmf_email = head.getGmf_email();
        String subject = head.getXsf_mc()+"开具的发票信息";
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss" );
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        String kprq = sdf.format( head.getKprq());
        String content = MessageFormat.format(emailTemplate, head.getGmf_mc(), head.getXsf_mc(), head.getFp_dm(), head.getFp_hm(),kprq,
                head.getJshj()+"元",serviceUrl+"invoiceAction/downloadGeneratedPdf/"+head.getFpqqlsh());
        InvoicePdf invoicePdf = pdfService.findByFileName(fplsh);
        String filePaths = ConstantUtil.getPdfRootPath(einvoiceDir).concat(invoicePdf.getUrl());
        emailService.sendAttachmentMail(gmf_email,subject,content,filePaths);
        return true;
    }

    /**
     * 发送邮件
     * @param fpqqlsh
     * @param email
     * @return
     */
    @RequestMapping("/sendAttachmentMailFileToFriend")
    @ResponseBody
    public boolean sendAttachmentMailFileToFriend(String fpqqlsh,String email) {
        List<EinvoiceHead> heads = einvoiceService.getByFpqqlsh(fpqqlsh);
        if (heads == null || heads.size() != 1) {
            return false;
        }
        EinvoiceHead head = heads.get(0);
        String subject = head.getXsf_mc()+"开具的发票信息";
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss" );
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        String kprq = sdf.format( head.getKprq());
        String content = MessageFormat.format(emailTemplate, head.getGmf_mc(), head.getXsf_mc(), head.getFp_dm(), head.getFp_hm(),kprq,
        head.getJshj()+"元",serviceUrl+"invoiceAction/downloadGeneratedPdf/"+head.getFpqqlsh());
        InvoicePdf invoicePdf = pdfService.findByFileName(fpqqlsh);
        String filePaths =ConstantUtil.getPdfRootPath(einvoiceDir).concat(invoicePdf.getUrl());
        emailService.sendAttachmentMail(email,subject,content,filePaths);
        return true;
    }
}
