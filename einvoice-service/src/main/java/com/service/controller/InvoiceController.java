package com.service.controller;

import com.alibaba.fastjson.JSONObject;
import com.core.bean.*;
import com.core.constant.EinvoiceStatus;
import com.core.service.*;

import com.core.util.ConstantUtil;
import com.core.util.JsonUtil;
import com.google.gson.JsonObject;
import com.service.service.RedisService;
import org.apache.commons.lang.Validate;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Effort
 * @description
 * @date 2020/9/23 10:05 上午
 */
@Controller
@RequestMapping("/invoiceAction")
public class InvoiceController {
    private static Logger logger = LoggerFactory.getLogger(InvoiceController.class);
    @Autowired
    private EinvoiceService einvoiceService;
    @Autowired
    private UserService userService;
    @Value("${downloadUrl}")
    private String downloadUrl;
    @Value("${einvoiceDir}")
    private String einvoiceDir;
    @Autowired
    private InvoicePdfService pdfService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private RequestXmlService requestXmlService;
    @Autowired
    private ResultXmlService resultXmlService;

    @RequestMapping("/getHeadByFpqqlsh")
    @ResponseBody
    public ApiOutput getHeadByFpqqlsh (@RequestHeader("token")String token,@RequestParam("fpqqlsh") String fpqqlsh) {
        String user = redisService.get(token);
        if (null == user) {
            return new ApiOutput(ApiCodeEnum.NEED_LOGIN);
        }
        List<EinvoiceHead> heads = einvoiceService.getByFpqqlsh(fpqqlsh);
        if (heads.size() != 1) {
            return new ApiOutput(ApiCodeEnum.NOT_EXIST);
        }else {
            return new ApiOutput(heads.get(0));
        }
    }

    /**
     * pdf文件下载
     * @param serialNumber 流水号
     * @return
     */
    @RequestMapping("/downloadGeneratedPdf/{serialNumber}")
    @ResponseBody
    public ApiOutput downloadGeneratedPdf(@PathVariable("serialNumber") String serialNumber, HttpServletResponse response) {
        String realPath = ConstantUtil.getPdfRootPath(einvoiceDir);
        InvoicePdf pdf = pdfService.findByFileName(serialNumber);
        String fileName = pdf.getUrl();
        File file = new File(realPath,fileName);
        if (file.exists()) {
            try (FileInputStream fis = new FileInputStream(file); BufferedInputStream bis = new BufferedInputStream(fis)){
                response.setHeader("content-type", "application/octet-stream");
                response.setContentType("application/octet-stream");
                response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(serialNumber+ConstantUtil.getSuffixPdf(),"UTF-8"));
                byte[] buffer = new byte[1024];
                OutputStream os = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
                return new ApiOutput(ApiCodeEnum.SUCCESS);
            } catch (Exception e) {
               e.printStackTrace();
               logger.error(e.getMessage());
            }
        }
        return new ApiOutput(ApiCodeEnum.FAIL);
    }

    /**
     * xml文件下载
     * @param fileName
     */
    @RequestMapping("/downloadXmlFile")
    @ResponseBody
    public void  downloadXmlFile( @RequestParam("fileName") String fileName, HttpServletResponse response){
        RequestXml requestXml = requestXmlService.findByFileName(fileName);
        String realPath = ConstantUtil.getRequestXmlRootPath(einvoiceDir);
        String fileNameSuffix = requestXml.getUrl();
        File file = new File(realPath,fileNameSuffix);
        if (file.exists()) {
            try ( FileInputStream fis = new FileInputStream(file);BufferedInputStream bis = new BufferedInputStream(fis)){
                response.setHeader("content-type", "application/x-msdownload");
                response.setContentType("application/octet-stream");
                response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName.concat(ConstantUtil.getSuffixXml()), "UTF-8"));
                byte[] buffer = new byte[1024];
                OutputStream os = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
                os.flush();
            } catch (Exception e) {
                logger.error("xml下载失败",e.getMessage());
            }
        }
    }

    /**
     * 取消生成发票
     * @param serialNumber
     * @return
     */
    @RequestMapping(value = "/cancelInvoice")
    @ResponseBody
    public ApiOutput cancelInvoice(@RequestParam(value = "serialNumber") String serialNumber) {
        List<EinvoiceHead> headList = einvoiceService.getByFpqqlsh(serialNumber);
        if (null != headList && headList.size() == 1) {
            EinvoiceHead head = headList.get(0);
            int status = head.getStatus();
            head.setStatus(EinvoiceStatus.CANCELING_INVOICE.getIndex());
            String filePath = null;
            switch (status){
                //待提交
                case 0:
                    //已接收数据
                case 1:
                    head.setStatus(EinvoiceStatus.CANCELED_INVOICE.getIndex());
                    break;
                //生成xml成功
                case 3:
                    RequestXml requestXml = requestXmlService.findByFileName(serialNumber);
                    filePath = ConstantUtil.getRequestXmlRootPath(einvoiceDir).concat(requestXml.getUrl());
                    deleteFile (filePath);
                    break;
            }
            einvoiceService.updateEinvoiceHead(head);
        }
        return new ApiOutput(ApiCodeEnum.SUCCESS);
    }

    @RequestMapping(value = "/canceledInvoice")
    @ResponseBody
    public ApiOutput canceledInvoice(@RequestParam(value = "serialNumber") String serialNumber) {
        List<EinvoiceHead> headList = einvoiceService.getByFpqqlsh(serialNumber);
        if (null != headList && headList.size() == 1) {
            EinvoiceHead head = headList.get(0);
            head.setStatus(EinvoiceStatus.CANCELED_INVOICE.getIndex());
            einvoiceService.updateEinvoiceHead(head);
            return new ApiOutput(ApiCodeEnum.SUCCESS);
        }
        return new ApiOutput(ApiCodeEnum.FAIL);
    }

    public void deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
    }


    /**
     * 拉取发票信息
     * @return
     */
    @RequestMapping(value = "/listInvoiceInfo")
    @ResponseBody
    public ApiOutput listInvoiceInfo(@RequestHeader("token") String token) throws UnknownHostException {
        if (null == token) {
            return new ApiOutput(ApiCodeEnum.NEED_LOGIN);
        }
        User user = JsonUtil.parseObject(redisService.get(token),User.class);
        List result = new ArrayList();
        List<EinvoiceHead> invoiceHeadList = einvoiceService.getInvoiceInfo(user.getMerchant_code(), EinvoiceStatus.GENXML_SUCCESS);
        for (EinvoiceHead head : invoiceHeadList) {
            Map map = new HashMap();
            map.put("url",downloadUrl+"?fileName="+head.getFpqqlsh());
            map.put("head",head);
            result.add(map);
        }
        return new ApiOutput(result);
    }

    @RequestMapping(value = "/listCancelInvoice")
    @ResponseBody
    public ApiOutput listCancelInvoice(@RequestHeader("token") String token) {
        if (null == token) {
            return new ApiOutput(ApiCodeEnum.NEED_LOGIN);
        }
        User user = JsonUtil.parseObject(redisService.get(token),User.class);
        List result = new ArrayList();
        List<EinvoiceHead> invoiceHeadList = einvoiceService.getInvoiceInfo(user.getMerchant_code(), EinvoiceStatus.CANCELING_INVOICE);
        for (EinvoiceHead head : invoiceHeadList) {
            result.add(head);
        }
        return new ApiOutput(result);
    }

    /**
     * 拉取已经下载的发票信息
     * @return
     */
    @RequestMapping(value = "/pullDownloadInvoice")
    @ResponseBody
    public ApiOutput pullDownloadInvoice(@RequestHeader("token")String token)  {
        if (null == token) {
            return new ApiOutput(ApiCodeEnum.NEED_LOGIN);
        }
        User user = JsonUtil.parseObject(redisService.get(token),User.class);
        if (null == user) {
            return new ApiOutput(ApiCodeEnum.LOGIN_FAIL);
        }
        List result = new ArrayList();
        List<EinvoiceHead> invoiceHeadList = einvoiceService.getInvoiceInfo(user.getMerchant_code(), EinvoiceStatus.XML_FILE_DOWNLOAD);
        for (EinvoiceHead head : invoiceHeadList) {
            Map map = new HashMap();
            map.put("url",downloadUrl+"?fileName="+head.getFpqqlsh());
            map.put("head",head);
            result.add(map);
        }
        return new ApiOutput(result);
    }


    /**
     * 更改发票状态
     * @param fpqqlsh 发票流水号
     */
    @RequestMapping(value = "/fileDownloadYet")
    @ResponseBody
    public ApiOutput fileDownloadYet(@RequestHeader("token")String token,@RequestParam("fpqqlsh") String fpqqlsh) {
        if (null == token) {
            return new ApiOutput(ApiCodeEnum.NEED_LOGIN);
        }
        try {
            List<EinvoiceHead> heads = einvoiceService.getByFpqqlsh(fpqqlsh);
            Validate.isTrue(heads.size()==1,"can not found the fpqqlsh");
            EinvoiceHead head = heads.get(0);
            head.setStatus(EinvoiceStatus.XML_FILE_DOWNLOAD.getIndex());
            einvoiceService.updateEinvoiceHead(head);
        }catch (Exception e) {
            e.printStackTrace();
            logger.info("更新发票状态失败，失败原因:{}",e.getMessage());
            return new ApiOutput(ApiCodeEnum.FAIL);
        }
        return new ApiOutput(ApiCodeEnum.SUCCESS);
    }

    /**
     * 税控机生成发票结果保存并且更新状态
     * @return
     */
    @RequestMapping(value = "/uploadInvoiceResultFile")
    @ResponseBody
    public ApiOutput uploadInvoiceResultFile(@RequestHeader("token")String token,@RequestParam("uploadFile")MultipartFile file,@RequestParam("fileName")String fileName,HttpServletRequest request) {
        if(null == token) {
            return new ApiOutput(ApiCodeEnum.NEED_LOGIN);
        }
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(new Date()).replaceAll("-", "");
        String targetFilePath = ConstantUtil.getResultXmlRootPath(einvoiceDir);
        String filePath = ConstantUtil.getResultXml(date,fileName);
        File targetFile = new File(targetFilePath + filePath);
        if (!targetFile.getParentFile().exists()) {
            targetFile.getParentFile().mkdirs();
        }
        boolean flag = true;
        try( FileOutputStream fileOutputStream = new FileOutputStream(targetFile)) {
            IOUtils.copy(file.getInputStream(), fileOutputStream);
            if (null == resultXmlService.getByFileName(fileName)) {
                ResultXml resultXml = new ResultXml();
                resultXml.setUrl(filePath);
                resultXml.setUuid(fileName);
                resultXmlService.save(resultXml);
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("税控机生成发票结果保存失败", e);
            return new ApiOutput(ApiCodeEnum.FAIL,"税控机生成发票结果保存失败");
        }
        String returncode = request.getParameter("RETURNCODE");
        String returnmsg = request.getParameter("RETURNMSG");

        //更新发票状态
        List<EinvoiceHead> heads = einvoiceService.getByFpqqlsh(fileName);
        for (EinvoiceHead head : heads) {
            int status;
            if (returncode != null) {
                 status = EinvoiceStatus.GENXML_FAIL.getIndex();
                 head.setReturncode(returncode);
                 head.setReturnmsg(returnmsg);
            }else {
                 status = flag?EinvoiceStatus.GENXML_SUCCESS.getIndex():EinvoiceStatus.GENXML_FAIL.getIndex();
            }
            head.setStatus(status);
            einvoiceService.updateEinvoiceHead(head);
        }
        return new ApiOutput(ApiCodeEnum.SUCCESS);
    }

    /**
     * 商户端登录
     * @return
     */
    @RequestMapping("/merchantLogin")
    @ResponseBody
    public ApiOutput merchantLogin(@RequestParam("appId")String appId,@RequestParam("appSecret")String appSecret) {
        try {
            User user  = userService.merchantLogin(appId, appSecret);
            if (null != user) {
                String token = UUID.randomUUID().toString().replace("-", "").toLowerCase();
                redisService.setOrUpdate(token, JSONObject.toJSONString(user),Long.valueOf(60*60*2));
                return new ApiOutput(token);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return new ApiOutput(ApiCodeEnum.FAIL);
    }
}
