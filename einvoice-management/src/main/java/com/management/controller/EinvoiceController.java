package com.management.controller;

import com.management.client.util.EinvoiceUtil;
import com.management.client.util.HttpUtil;
import com.management.client.util.JsonUtil;
import com.management.client.vo.InvoiceOrder;
import com.management.client.vo.InvoiceVo;
import com.management.commons.base.BaseController;
import com.management.commons.result.Result;
import com.management.commons.utils.PageInfo;
import com.management.commons.utils.StringUtils;
import com.management.model.*;
import com.management.model.vo.EinvoiceParmVo;
import com.management.model.vo.UserVo;
import com.management.server.vo.InvoiceOrderResponse;
import com.management.service.*;
import com.management.util.CommonValidator;
import com.management.util.StringUtil;
import com.management.util.VerifyUtil;
import org.apache.commons.lang.Validate;
import org.apache.logging.log4j.util.Strings;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.*;

/**
 * Created by sdyang on 2016/10/24.
 */
@Controller
@RequestMapping("/einvoice")
public class EinvoiceController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(EinvoiceController.class);

    @Autowired
    private IEinvoiceHeadService einvoiceHeadService;
    @Autowired
    private IEinvoiceBodyService einvoiceBodyService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private TaxRateDetailService detailService;
    //内网service
    @Value("${inside.service.url}")
    private String insideServiceUrl;

    @Value("${service.url}")
    private String serviceUrl;

    @RequestMapping(value = "/manager", method = RequestMethod.GET)
    public String  manager(Model model) {
        model.addAttribute("serviceUrl",serviceUrl+"invoiceAction/");
        return "einvoice/head";
    }

    @RequestMapping(value = "/invoiceDetail")
    public String invoiceDetail(@RequestParam("fpqqlsh") String fpqqlsh,Model model) {
        EinvoiceHead einvoiceHead = einvoiceHeadService.selectEinvoiceHeadByFpqqlsh(fpqqlsh);
        String merchantCode = einvoiceHead.getMerchant_code();
        UserVo userVo = userService.selectVoByMerchantCode(merchantCode);
        Long detailId = userVo.getTax_rate_detail_id();
        TaxRateDetail detail = detailService.getById(detailId);
        List<EinvoiceBody> einvoiceBodies = einvoiceBodyService.selectByfpqqlsh(einvoiceHead.getFpqqlsh());
        model.addAttribute("body",einvoiceBodies.get(0));
        model.addAttribute("detail",detail);
        model.addAttribute("head",einvoiceHead);
        model.addAttribute("user",userVo);
        Date createtime = einvoiceHead.getCreatetime();
        Calendar instance = Calendar.getInstance();
        instance.setTime(createtime);
        instance.add(Calendar.DAY_OF_MONTH,+7);
        model.addAttribute("failureTime",instance.getTime());
        return "einvoice/einvoiceDetail";
    }

    @RequestMapping(value = "/dataGrid",method = RequestMethod.POST)
    @ResponseBody
    public Object dataGrid(EinvoiceParmVo parmVo, Integer page, Integer rows) {
        boolean isAdmin = false;
        Subject subject = SecurityUtils.getSubject();
        User user = userService.selectByLoginName(subject.getPrincipal().toString());
        List<Long> idlist = roleService.selectRoleIdListByUserId(user.getId());
        List<Role> roles = roleService.selectBatchIds(idlist);
        for(Role role:roles){
            if(role.getName().equals("管理员")){
                isAdmin = true;
            }
        }

        PageInfo pageInfo = new PageInfo(page, rows,"pk_einvoicehead","desc");
        Map<String, Object> condition = new HashMap<String, Object>();

        if(!isAdmin){
            condition.put("pk_user", user.getId());
        }

        if (StringUtils.isNotEmpty(parmVo.getFpqqlsh())) {
            condition.put("fpqqlsh", parmVo.getFpqqlsh());
        }
        if (StringUtils.isNotEmpty(parmVo.getStatus())) {
            condition.put("status", parmVo.getStatus());
        }else {
            condition.put("status", "0");
        }
        if(StringUtils.isNotEmpty(parmVo.getOrder_no())){
            condition.put("order_no",parmVo.getOrder_no());
        }

        if(StringUtils.isNotEmpty(parmVo.getXsf_mc())){
            condition.put("xsf_mc",parmVo.getXsf_mc());
        }
        if (parmVo.getSubmit_begin()!=null) {
            condition.put("submit_begin", parmVo.getSubmit_begin());
        }

        if(parmVo.getSubmit_end()!=null){
            condition.put("submit_end",parmVo.getSubmit_end());
        }
        if(condition.size()==2 && condition.get("status")==null && condition.get("pk_user")!=null){
            pageInfo.setCondition(condition);
            einvoiceHeadService.defaultDataGrid(pageInfo);
            return pageInfo;
        }
        pageInfo.setCondition(condition);
        einvoiceHeadService.selectDataGrid(pageInfo);
        return pageInfo;
    }

    @RequestMapping(value = "/body/{pk_einvoicehead}", method = RequestMethod.GET)
    public String  body(Model model,@PathVariable("pk_einvoicehead") Long id) {
        model.addAttribute("id",id);
        return "einvoice/body";
    }


    @RequestMapping(value = "/detail/{pk_einvoicehead}",method = RequestMethod.POST)
    @ResponseBody
    public Object detail(@PathVariable("pk_einvoicehead") Long id) {
        PageInfo pageInfo = new PageInfo(0, 20,"pk_einvoicebody","desc");
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("pk_einvoicehead", id);
        pageInfo.setCondition(condition);
        einvoiceBodyService.selectDataGrid(pageInfo);
        return pageInfo;
    }

    /**
     * 添加发票页
     *
     * @return
     */
    @RequestMapping(value = "/addPage", method = RequestMethod.GET)
    public String addPage(Model model,@RequestParam(required = false) String fpqqlsh) {
        //复制发票
        if (StringUtils.isNotEmpty(fpqqlsh)) {
            List<EinvoiceBody> einvoiceBodies = einvoiceBodyService.selectByfpqqlsh(fpqqlsh);
            if (einvoiceBodies.size() > 0) {
                EinvoiceBody einvoiceBody = einvoiceBodies.get(0);
                model.addAttribute("body",einvoiceBody);
            }else {
                model.addAttribute("body",new EinvoiceBody());
            }
            EinvoiceHead einvoiceHead = einvoiceHeadService.selectEinvoiceHeadByFpqqlsh(fpqqlsh);
            if (null != einvoiceHead) {
                String gmf_dzdh = einvoiceHead.getGmf_dzdh();
                String gmf_yhzh = einvoiceHead.getGmf_yhzh();
                model.addAttribute("dz",gmf_dzdh.substring(0,gmf_dzdh.indexOf(" ")));
                model.addAttribute("dh",gmf_dzdh.substring(gmf_dzdh.indexOf(" ")+1));
                model.addAttribute("yh",gmf_yhzh.substring(0,gmf_yhzh.indexOf(" ")));
                model.addAttribute("zh",gmf_yhzh.substring(gmf_dzdh.indexOf(" ")+1));
                model.addAttribute("head",einvoiceHead);
            }
        }
        Subject subject = SecurityUtils.getSubject();
        User user = userService.selectByLoginName(subject.getPrincipal().toString());
        model.addAttribute("user", user);
        //商品编码
        String mergeCode = user.getTax_spbm();
        Validate.notEmpty(mergeCode,"请填写商品编码");
        model.addAttribute("taxDetail",detailService.getByMergeCode(mergeCode));
        //发票请求流水
        String invoiceFpqqlsh = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        model.addAttribute("fpqqlsh",invoiceFpqqlsh);
        return "einvoice/einvoiceAdd";
    }

    /**
     * 取消生成发票
     * @param serialNumber
     * @return
     */
    @RequestMapping(value = "/cancelInvoice/{serialNumber}")
    public String cancelInvoice(@PathVariable("serialNumber") String serialNumber) {
        return HttpUtil.get(insideServiceUrl + "cancelInvoice?serialNumber=" + serialNumber);
    }

    /**
     * 添加发票
     * @param invoiceVo
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    @ResponseBody
    public Object add(InvoiceVo invoiceVo)  throws ParseException {
        //商户编码
        String merchantCode = CommonValidator.verifyParam(invoiceVo.getMerchant_code(),"商户编码不能为空");
        double jshj = invoiceVo.getJshj();
        if(jshj <= 0) {
            throw new IllegalArgumentException("请填写正确的开票金额");
        }
        String taxSl = CommonValidator.verifyParam(invoiceVo.getTax_sl(),"请填写税率");
        invoiceVo.setTax_sl(taxSl);
        UserVo userVo = userService.selectVoByMerchantCode(merchantCode);

        String submitType = invoiceVo.getSubmitType();
        String result = null;
        // 生成二维码
        if (StringUtils.isNotEmpty(submitType) && submitType.equals("0")) {
            InvoiceOrder order = EinvoiceUtil.build(invoiceVo, userVo);
            result  = HttpUtil.post(order,insideServiceUrl+"sendinvoice");
        }else {
            //直接提交开票
            String gmfMc = CommonValidator.verifyParam(invoiceVo.getGmf_mc(),"请填写开票公司名称");
            invoiceVo.setGmf_mc(gmfMc);
            String gmfNsrsbh = CommonValidator.verifyParam(invoiceVo.getGmf_nsrsbh(),"请填写纳税人识别号");
            invoiceVo.setGmf_nsrsbh(gmfNsrsbh);
            String email = invoiceVo.getGmf_email();
            String newEmail  = CommonValidator.verifyParam(email,"请填写通知邮箱");
            if (!VerifyUtil.verifyEmail(email)) {
                throw new IllegalArgumentException("请填写正确的邮箱");
            }
            invoiceVo.setGmf_email(newEmail);

            String xmmc = CommonValidator.verifyParam(invoiceVo.getXmmc(),"请填写服务名称");
            invoiceVo.setXmmc(xmmc);
            String dzdh = CommonValidator.verifyParam(invoiceVo.getGmf_dz(),"请填写公司地址");
            invoiceVo.setGmf_dz(dzdh);
            String dh = CommonValidator.verifyParam(invoiceVo.getGmf_dh(),"请填写公司电话");
            invoiceVo.setGmf_dh(dh);
            String yhzh = CommonValidator.verifyParam(invoiceVo.getGmf_yh(),"请填写开户行名称");
            invoiceVo.setGmf_yh(yhzh);
            String zh = CommonValidator.verifyParam(invoiceVo.getGmf_zh(),"请填写开户银行账号");
            invoiceVo.setGmf_zh(zh);
            InvoiceOrder order = EinvoiceUtil.build(invoiceVo, userVo);
            result = HttpUtil.post(order, insideServiceUrl+"directSubmission");
        }

        logger.debug("添加发票：{} "+ result);
        InvoiceOrderResponse res = JsonUtil.parseObject(result,InvoiceOrderResponse.class);
        if(res !=null &&res.getReturn_code() != null&& res.getReturn_code().equalsIgnoreCase("0000")){//成功
            String fpqqlsh = res.getFpqqlsh();
            EinvoiceHead einvoiceHead = einvoiceHeadService.selectEinvoiceHeadByFpqqlsh(fpqqlsh);
            Map<String,Object> map = new HashMap<>();
            map.put("user",userVo);
            map.put("einvoiceHead",einvoiceHead);
            Result r = (Result) renderSuccess("添加成功");
            r.setObj(map);
            return r;
        }
        else{
            return renderError("创建失败");
        }
    }
}
