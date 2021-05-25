package com.management.client.util;

import com.management.client.vo.InvoiceOrder;
import com.management.client.vo.InvoiceOrderBody;
import com.management.client.vo.InvoiceVo;
import com.management.commons.utils.StringUtils;
import com.management.model.vo.UserVo;
import com.management.util.BigDecimalHelper;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class EinvoiceUtil {
    public static InvoiceOrder build(InvoiceVo vo, UserVo userVo) throws ParseException {
        //发票抬头信息
        InvoiceOrder order = new InvoiceOrder();
        order.setVersion("1.0");
        order.setMerchant_code(vo.getMerchant_code());
        order.setSign_type("MD5");
        order.setMerchant_name(vo.getMerchant_name());
        order.setApp_no(userVo.getApp_no());
        order.setSubmitdate(new Date());
        order.setFpqqlsh(vo.getFpqqlsh());
        order.setKplx(vo.getKplx());
        order.setXsf_nsrsbh(userVo.getTax_nsrsbh());
        order.setXsf_mc(userVo.getTax_merchant_name());
        order.setXsf_dzdh(userVo.getTax_dzdh());
        order.setXsf_yhzh(userVo.getTax_yhzh());
        order.setGmf_mc(vo.getGmf_mc());
        order.setKpr(userVo.getTax_shenheren());
        order.setSkr(userVo.getTax_shenheren());
        order.setFhr(userVo.getTax_shenheren());
        order.setJshj(BigDecimalHelper.getCurrencyOfDouble(vo.getJshj()));
        Double tax = BigDecimalHelper.doubleMul(vo.getJshj(),Double.valueOf(vo.getTax_sl())/100.0);
        //备注
        order.setBz(vo.getBz());
        order.setOrder_no(vo.getOrder_no());
        order.setYfp_hm(vo.getYfp_hm());
        order.setYfp_dm(vo.getYfp_dm());
        order.setGmf_email(vo.getGmf_email());
        order.setBmbbbh(userVo.getBmbbbh());
        order.setGmf_phone(vo.getGmf_phone());
        order.setTax_spbm(userVo.getTax_spbm());
        order.setGmf_nsrsbh(vo.getGmf_nsrsbh());
        String gmf_dz = vo.getGmf_dz();
        String gmf_yh = vo.getGmf_yh();
        String gmf_dh = vo.getGmf_dh();
        String gmf_zh = vo.getGmf_zh();
        order.setGmf_dzdh(gmf_dz+" "+gmf_dh);
        order.setGmf_yhzh(gmf_yh+" "+gmf_zh);

        //发票内容
        List<InvoiceOrderBody> businessOrderBodyList = new ArrayList<InvoiceOrderBody>();
        InvoiceOrderBody invoiceOrderBody = new InvoiceOrderBody();
        //商品编码
        invoiceOrderBody.setTax_spbm(vo.getTax_spbm());
        //单位
        invoiceOrderBody.setDw("");
        invoiceOrderBody.setGgxh("");
        invoiceOrderBody.setFpqqlsh(vo.getFpqqlsh());
        invoiceOrderBody.setRow_no(1);
        invoiceOrderBody.setXmsl(1);
        invoiceOrderBody.setXmmc(vo.getXmmc());
        //税率
        invoiceOrderBody.setSl(BigDecimalHelper.getCurrencyOfDouble(Double.valueOf(vo.getTax_sl())/100.0));
        //税额
        invoiceOrderBody.setSe(BigDecimalHelper.getCurrencyOfDouble(vo.getJshj()/(1+invoiceOrderBody.getSl())*invoiceOrderBody.getSl()));
        invoiceOrderBody.setXmje(BigDecimalHelper.getCurrencyOfDouble(vo.getJshj()-invoiceOrderBody.getSe()));
        invoiceOrderBody.setXmdj(BigDecimalHelper.getCurrencyOfDouble(vo.getJshj()-invoiceOrderBody.getSe()));
        //合计金额
        order.setHjje(BigDecimalHelper.getCurrencyOfDouble(  invoiceOrderBody.getXmje()));
        //合计税额
        order.setHjse(BigDecimalHelper.getCurrencyOfDouble(invoiceOrderBody.getSe()));
        invoiceOrderBody.setFphxz("1");
        businessOrderBodyList.add(invoiceOrderBody);
        order.setInvoiceOrderBodyList(businessOrderBodyList);
        Map<String,String> mapData = MapUtil.beanToMap(order);
        String sign = MD5Util.createSign(mapData,userVo.getMd5key());
        order.setMerchant_sign(sign);
        return order;
    }
}
