package com.task.util;

import com.alibaba.druid.util.StringUtils;
import com.core.bean.EinvoiceBody;
import com.core.bean.EinvoiceHead;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;


/**
 * Created by sdyang on 2016/10/4.
 */
public class XmlUtil {

    private static Logger logger = Logger.getLogger(XmlUtil.class);

    /**
     * 创建XML文件
     * @param head
     * @param filename
     * @return
     */
    public static boolean createXML(EinvoiceHead head,String filename){
        boolean result = false;
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("business");
        root.addAttribute("id","FPKJ");
        root.addAttribute("comment","发票开具");
        Element REQUEST_COMMON_FPKJ = root.addElement("REQUEST_COMMON_FPKJ");
        REQUEST_COMMON_FPKJ.addAttribute("class","REQUEST_COMMON_FPKJ");
        try {
            Element COMMON_FPKJ_FPT = REQUEST_COMMON_FPKJ.addElement("COMMON_FPKJ_FPT");
            COMMON_FPKJ_FPT.addAttribute("class", "COMMON_FPKJ_FPT");

            Element FPQQLSH = COMMON_FPKJ_FPT.addElement("FPQQLSH");
            FPQQLSH.addText(head.getFpqqlsh());

            Element KPLX = COMMON_FPKJ_FPT.addElement("KPLX");
            KPLX.addText(head.getKplx());

            Element BMB_BBH = COMMON_FPKJ_FPT.addElement("BMB_BBH");//编码表版本号
            BMB_BBH.addText(StringUtils.isEmpty(head.getBmbbbh())?"":head.getBmbbbh());

            Element XSF_NSRSBH = COMMON_FPKJ_FPT.addElement("XSF_NSRSBH");
            XSF_NSRSBH.addText(head.getXsf_nsrsbh());

            Element XSF_MC = COMMON_FPKJ_FPT.addElement("XSF_MC");
            XSF_MC.addText(head.getXsf_mc());

            Element XSF_DZDH = COMMON_FPKJ_FPT.addElement("XSF_DZDH");
            XSF_DZDH.addText(head.getXsf_dzdh());

            Element XSF_YHZH = COMMON_FPKJ_FPT.addElement("XSF_YHZH");
            XSF_YHZH.addText(head.getXsf_yhzh());

            Element GMF_NSRSBH = COMMON_FPKJ_FPT.addElement("GMF_NSRSBH");
            GMF_NSRSBH.addText(getValue(head.getGmf_nsrsbh()));

            Element GMF_MC = COMMON_FPKJ_FPT.addElement("GMF_MC");
            GMF_MC.addText(head.getGmf_mc());

            Element GMF_DZDH = COMMON_FPKJ_FPT.addElement("GMF_DZDH");
            GMF_DZDH.addText(getValue(head.getGmf_dzdh()));

            Element GMF_YHZH = COMMON_FPKJ_FPT.addElement("GMF_YHZH");
            GMF_YHZH.addText(getValue(head.getGmf_yhzh()));

            Element KPR = COMMON_FPKJ_FPT.addElement("KPR");
            KPR.addText(head.getKpr());

            Element SKR = COMMON_FPKJ_FPT.addElement("SKR");
            SKR.addText(getValue(head.getSkr()));

            Element FHR = COMMON_FPKJ_FPT.addElement("FHR");
            FHR.addText(getValue(head.getFhr()));

            Element YFP_DM = COMMON_FPKJ_FPT.addElement("YFP_DM");
            YFP_DM.addText(getValue(head.getYfp_dm()));

            Element YFP_HM = COMMON_FPKJ_FPT.addElement("YFP_HM");
            YFP_HM.addText(getValue(head.getYfp_hm()));

            Element JSHJ = COMMON_FPKJ_FPT.addElement("JSHJ");
            JSHJ.addText(head.getJshj() + "");

            Element HJJE = COMMON_FPKJ_FPT.addElement("HJJE");
            HJJE.addText(head.getHjje() + "");

            Element HJSE = COMMON_FPKJ_FPT.addElement("HJSE");
            HJSE.addText(head.getHjse() + "");
            Element HSBZ = COMMON_FPKJ_FPT.addElement("HSBZ");
            HSBZ.addText("0");
            Element BZ = COMMON_FPKJ_FPT.addElement("BZ");
            BZ.addText(getValue(head.getBz()));

            List<EinvoiceBody> einvoiceBodies = head.getEinvoiceBodies();
            Element COMMON_FPKJ_XMXXS = REQUEST_COMMON_FPKJ.addElement("COMMON_FPKJ_XMXXS");
            COMMON_FPKJ_XMXXS.addAttribute("class", "COMMON_FPKJ_XMXX");
            COMMON_FPKJ_XMXXS.addAttribute("size", einvoiceBodies.size() + "");

            for (EinvoiceBody body : einvoiceBodies) {
                Element COMMON_FPKJ_XMXX = COMMON_FPKJ_XMXXS.addElement("COMMON_FPKJ_XMXX");
                Element FPHXZ = COMMON_FPKJ_XMXX.addElement("FPHXZ");
                FPHXZ.addText(body.getFphxz());

                Element SPBM = COMMON_FPKJ_XMXX.addElement("SPBM");
                SPBM.addText(StringUtils.isEmpty(body.getSpbm())?"":body.getSpbm());
                //自行编码
                Element ZXBM = COMMON_FPKJ_XMXX.addElement("ZXBM");
                ZXBM.addText("");
                //优惠政策标识
                Element YHZCBS = COMMON_FPKJ_XMXX.addElement("YHZCBS");
                YHZCBS.addText("");
                //零税率标识
                Element LSLBS = COMMON_FPKJ_XMXX.addElement("LSLBS");
                LSLBS.addText("");
                //增值税特殊管理
                Element ZZSTSGL = COMMON_FPKJ_XMXX.addElement("ZZSTSGL");
                ZZSTSGL.addText("");

                Element XMMC = COMMON_FPKJ_XMXX.addElement("XMMC");
                XMMC.addText(body.getXmmc());

                Element DW = COMMON_FPKJ_XMXX.addElement("DW");
                DW.addText(getValue(body.getDw()));

                Element GGXH = COMMON_FPKJ_XMXX.addElement("GGXH");
                GGXH.addText(getValue(body.getGgxh()));

                Element XMSL = COMMON_FPKJ_XMXX.addElement("XMSL");
                XMSL.addText(body.getXmsl() + "");

                Element XMDJ = COMMON_FPKJ_XMXX.addElement("XMDJ");
                XMDJ.addText(body.getXmdj() + "");

                Element XMJE = COMMON_FPKJ_XMXX.addElement("XMJE");
                XMJE.addText(body.getXmje() + "");
                //税率
                Element SL = COMMON_FPKJ_XMXX.addElement("SL");
                SL.addText(body.getSl() + "");
                //税额
                Element SE = COMMON_FPKJ_XMXX.addElement("SE");
                SE.addText(body.getSe() + "");
                //扣除额
                Element KCE = COMMON_FPKJ_XMXX.addElement("KCE");
                KCE.addText("0.0");
            }
        }catch (Exception e){
            logger.error("创建XML文件失败：",e);
        }
        StringWriter strWtr = new StringWriter();
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("UTF-8");
        XMLWriter xmlWriter =new XMLWriter(strWtr, format);
        try {
            xmlWriter.write(document);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        try {
            logger.debug("准备创建文件："+filename);
            FileUtil.isExist(filename);
            File file = new File(filename);
            if (!file.getParentFile().exists())
                file.getParentFile().mkdirs();
            file.createNewFile();
            XMLWriter out = new XMLWriter(new FileWriter(file));
            out.write(document);
            out.flush();
            out.close();
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    //读取XML文件信息
    public static boolean  readXML(String filename,EinvoiceHead head) throws DocumentException {
        Boolean result = false;
        SAXReader reader = new SAXReader();
        File file = new File(filename);
        try {
            Document document = reader.read(file);
            Element root = document.getRootElement();
            Element RESPONSE_COMMON_FPKJ = root.element("RESPONSE_COMMON_FPKJ");
            String FPQQLSH = RESPONSE_COMMON_FPKJ.elementText("FPQQLSH");
            if (head.getFpqqlsh().equals(FPQQLSH)) {//发票流水号相同
                String returncode = RESPONSE_COMMON_FPKJ.elementText("RETURNCODE");
                head.setReturncode(returncode);
                head.setReturnmsg(RESPONSE_COMMON_FPKJ.elementText("RETURNMSG"));
                if ("0000".equals(returncode)) {//开票成功
                    head.setJqbh(RESPONSE_COMMON_FPKJ.elementText("JQBH"));
                    head.setFp_dm(RESPONSE_COMMON_FPKJ.elementText("FP_DM"));
                    head.setFp_hm(RESPONSE_COMMON_FPKJ.elementText("FP_HM"));
                    head.setKprq(DateUtil.StringToDate(RESPONSE_COMMON_FPKJ.elementText("KPRQ"), "yyyyMMddHHmmss"));
                    head.setFpmw(RESPONSE_COMMON_FPKJ.elementText("FP_MW"));
                    head.setJym(RESPONSE_COMMON_FPKJ.elementText("JYM"));
                    head.setEwm(genEwmInfo(head));
                    head.setBz_return(RESPONSE_COMMON_FPKJ.elementText("BZ"));
                    result = true;
                }
            }
        }catch (Exception e){
            logger.error("读取XML文件异常："+e.getMessage());
        }
        return result;
    }

    /**
     * 生成二维码
     * @param head
     * @return
     */
    private static String genEwmInfo(EinvoiceHead head){
        StringBuffer sb = new StringBuffer();
        sb.append("01,10,");
        sb.append(head.getJqbh()+",");
        sb.append(head.getFp_hm()+",");
        sb.append(head.getHjje()+",");
        sb.append(DateUtil.dateToString(head.getKprq(),"yyyyMMdd")+",");
        sb.append(head.getJym()+",");
        String ccr = CRCUtil.crc(sb.toString());
        sb.append(ccr+",");
        return sb.toString();
    }

    private static String getValue(String  str){
        String result = "";
        if(str != null){
            result = str;
        }
        return result;
    }
}
