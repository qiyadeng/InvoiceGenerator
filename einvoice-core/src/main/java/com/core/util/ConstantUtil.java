package com.core.util;

import java.io.File;

/**
 * @author Effort
 * @description
 * @date 2020/10/22 9:13 上午
 */
public class ConstantUtil {
    private final static String HomeDirectory = "einvoice";//主目录
    private final static String Template = "template";//模板文件目录
    private final static String cachet = "cachet";//公章存放目录
    private final static String XML_Request = "request";//请求开票xml文件
    private final static String XML_Result = "result";//开票结果xml文件
    private final static String PDF = "pdf";//生成电子发票pdf文件
    private final static String SUFFIX_XML = ".xml";//XML文件后缀
    private final static String INVOICE_RESULT = "_开票结果";//开票结果文件名
    private final static String SUFFIX_PDF = ".pdf";//PDF文件后缀
    private final static String SUFFIX_CACHE=".png";//公章文件后缀

    public static String getSuffixXml(){
        return SUFFIX_XML;
    }
    public static String getSuffixPdf() {
        return SUFFIX_PDF;
    }

    public static String getPdfSavePath(String rootPath,String date,String serialNumber) {
       return getPdfRootPath(rootPath).concat(getPdfPath(date,serialNumber));
    }
    public static String getPdfRootPath(String rootPath) {
        return rootPath+HomeDirectory+ File.separator+PDF+File.separator;
    }
    public static String getPdfPath(String date,String serialNumber) {
        return date+File.separator+serialNumber+SUFFIX_PDF;
    }

    public static String getRequestXmlSavePath(String rootPath,String date,String serialNumber) {
        return  getRequestXmlRootPath(rootPath).concat(getRequestXml(date,serialNumber));
    }
    public static String getRequestXmlRootPath(String rootPath) {
        return rootPath+HomeDirectory+ File.separator+XML_Request+File.separator;
    }
    public static String getRequestXml(String date,String serialNumber) {
        return date+File.separator+serialNumber+SUFFIX_XML;
    }

    public static String getResultXmlSavePath(String rootPath,String date,String serialNumber) {
        return  getResultXmlRootPath(rootPath).concat(getResultXml(date,serialNumber));
    }
    public static String getResultXmlRootPath(String rootPath) {
        return rootPath+HomeDirectory+ File.separator+XML_Result+File.separator;
    }
    public static String getResultXml(String date,String serialNumber) {
        return date+File.separator+serialNumber+INVOICE_RESULT+SUFFIX_XML;
    }

    public static String getTemplateSavePath(String rootPath,String date,String serialNumber) {
        return  getTemplateRootPath(rootPath).concat(getTemplate(date,serialNumber));
    }
    public static String getTemplateRootPath(String rootPath) {
        return rootPath+HomeDirectory+ File.separator+Template+File.separator;
    }
    public static String getTemplate(String date,String serialNumber) {
        return date+File.separator+serialNumber+SUFFIX_PDF;
    }

    public static String getCachetSavePath(String rootPath,String date,String serialNumber) {
        return  getCachetRootPath(rootPath).concat(getCachet(date,serialNumber));
    }
    public static String getCachetRootPath(String rootPath) {
        return rootPath+HomeDirectory+ File.separator+cachet+File.separator;
    }
    public static String getCachet(String date,String serialNumber) {
        return date+File.separator+serialNumber+SUFFIX_CACHE;
    }
}
