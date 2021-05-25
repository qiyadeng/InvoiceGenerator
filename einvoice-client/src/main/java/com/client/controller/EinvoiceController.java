package com.client.controller;

import com.client.util.MD5Util;
import com.core.bean.EinvoiceHead;
import com.core.bean.ResponseEntity;
import com.core.bean.User;
import com.core.constant.EinvoiceStatus;
import com.core.service.EinvoiceService;
import com.core.service.UserService;
import com.client.util.EinvoiceUtil;
import com.client.util.HttpUtil;
import com.client.vo.InvoiceOrder;
import com.client.vo.ResultVo;
import com.core.util.CommonValidator;
import com.core.util.VerifyUtil;
import com.google.common.base.Strings;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
public class EinvoiceController {
    private Logger log = LoggerFactory.getLogger(EinvoiceController.class);
    @Autowired
    private EinvoiceService einvoiceService;
    @Value("${inside.service.url}")
    private String insideServiceUrl;
    @Value("${service.url}")
    private String serviceUrl;
    @Value("${client.url}")
    private String clientUrl;
    @Autowired
    private UserService userService;
    @Value("${private.key}")
    private String privateKey;

    /**
     * pdf查看页面
     *
     * @return
     */
    @RequestMapping("/pdfViewer")
    public String pdfViewer() {
        return "viewer";
    }
    @RequestMapping(value = {"/","/index"})
    @ResponseBody
    public String index() {
        return "success";
    }

    @RequestMapping(value = {"/i"}, method = RequestMethod.GET)
    public String index(Model model, HttpServletRequest request) {
        String fpqqlsh = request.getParameter("lsh");
        if (Strings.isNullOrEmpty(fpqqlsh)) {
            model.addAttribute("title", "参数错误");
            model.addAttribute("msg", "缺少发票流水号");
            return "fail";
        }
        List<EinvoiceHead> einvoiceHeadList = einvoiceService.getByFpqqlsh(fpqqlsh);
        if (einvoiceHeadList.size() != 1) {
            model.addAttribute("title", "NOT FOUND");
            model.addAttribute("msg", "未找到该发票");
            return "fail";
        }
        EinvoiceHead head = einvoiceHeadList.get(0);
        Date createtime = head.getCreatetime();
        Calendar cal = Calendar.getInstance();
        cal.setTime(createtime);
        cal.add(Calendar.DATE,+7);
        Calendar now = Calendar.getInstance();
        if (!now.before(cal)) {
            model.addAttribute("title", "NOT FOUND");
            model.addAttribute("msg", "二维码已失效");
            return "fail";
        }

        User user = userService.getByMerchant_code(head.getMerchant_code());
        if (null == user) {
            model.addAttribute("title", "NOT FOUND");
            model.addAttribute("msg", "未找到该商户");
            return "fail";
        }
        model.addAttribute("head", head);
        model.addAttribute("status", head.getStatus());
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        model.addAttribute("fpqqlsh", df.format(new Date()));
        model.addAttribute("submitdate", new Date());
        model.addAttribute("user", user);
        if (head.getStatus() == EinvoiceStatus.GENPDF_SUCCESS.getIndex()) {
            model.addAttribute("url", serviceUrl + "invoiceAction/downloadGeneratedPdf/" + head.getFpqqlsh());
        } else {
            model.addAttribute("url", "javascript:");
        }
        EinvoiceStatus status = EinvoiceStatus.getByIndex(head.getStatus());

        switch (status) {
            case RECEVICE_FAIL:
            case GENXML_FAIL:
            case GENPDF_FIAL:
            case SIGN_FAIL:
                model.addAttribute("title", "开票失败");
                model.addAttribute("msg", status.getName());
                return "fail";
            case CANCELING_INVOICE:
            case CANCELED_INVOICE:
                model.addAttribute("title", "取消开票");
                model.addAttribute("msg", status.getName());
                return "fail";
            case GENPDF_SUCCESS:
                model.addAttribute("viewerUrl", clientUrl);
                return "detail";
            case WAIT_COMMIT:
                String key = privateKey;
                long timeSwap = new Date().getTime();
                model.addAttribute("key", MD5Util.getMD5New( key + timeSwap));
                model.addAttribute("timeStamp",timeSwap);
                model.addAttribute("statusName", status.getName());
                return "index";
            default:
                return "success";
        }
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public ResultVo updateDate(HttpServletRequest request) {
        ResultVo resultVo;
        try {
            String fpqqlsh = CommonValidator.verifyParam(request.getParameter("fpqqlsh"), "发票流水号不能为空");
            String gmf_mc = CommonValidator.verifyParam(request.getParameter("gmf_mc"), "公司抬头不能为空");
            String gmf_nsrsbh = CommonValidator.verifyParam(request.getParameter("gmf_nsrsbh"), "纳税人识别号不能为空");
            CommonValidator.verifyParam(request.getParameter("gmf_nsrsbh"), "纳税人识别号不能为空");
            String gmf_yh = request.getParameter("gmf_yh").trim();
            String gmf_zh = request.getParameter("gmf_zh").trim();
            String gmf_dz = request.getParameter("gmf_dz").trim();
            String gmf_dh = request.getParameter("gmf_dh").trim();
            String email = CommonValidator.verifyParam(request.getParameter("email"), "请输入邮箱");
            //邮箱校验
            if (!VerifyUtil.verifyEmail(email)) {
                throw new IllegalArgumentException("请输入正确的邮箱");
            }

            List<EinvoiceHead> einvoiceHeadList = einvoiceService.getByFpqqlsh(fpqqlsh);
            Validate.isTrue(einvoiceHeadList.size() == 1, "can not found the fpqqlsh");
            EinvoiceHead einvoiceHead = einvoiceHeadList.get(0);
            einvoiceHead.setGmf_mc(gmf_mc);
            einvoiceHead.setGmf_nsrsbh(gmf_nsrsbh);
            einvoiceHead.setGmf_yhzh(gmf_yh.concat(" ").concat(gmf_zh));
            einvoiceHead.setGmf_dzdh(gmf_dz.concat(" ").concat(gmf_dh));
            einvoiceHead.setGmf_email(email);
            einvoiceHead.setStatus(EinvoiceStatus.RECEVICE_SUCCESS.getIndex());
            einvoiceService.updateEinvoiceHead(einvoiceHead);
            resultVo = ResultVo.success("success");
        } catch (Exception e) {
            log.error(e.getMessage());
            resultVo = ResultVo.error(e.getMessage());
        }
        return resultVo;
    }

    @RequestMapping(value = "query", method = RequestMethod.GET)
    public String queryPage(Model model) {
        return "query";
    }

    @RequestMapping(value = "query", method = RequestMethod.POST)
    public String query(Model model, @RequestParam("merchant_code") String merchant_code, @RequestParam("merchant_name") String merchant_name,
                        @RequestParam("key") String key, @RequestParam("order_no") String order_no) throws ParseException {
        InvoiceOrder order = EinvoiceUtil.buildQuery(merchant_code, merchant_name, order_no, key);
        String result = null;
        try {
            result = HttpUtil.post(order, insideServiceUrl + "queryinvoice");
        } catch (IOException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        model.addAttribute("msg", result);
        return "result";
    }

    /**
     * 将发票发送到邮箱
     *
     * @return
     */
    @RequestMapping("/sendEmail")
    @ResponseBody
    public ResponseEntity sendEmail(@Param("fqqlsh") String fqqlsh, @Param("email") String email) {
        String invoiceLsh = CommonValidator.verifyParam(fqqlsh, "流水号不能为空");
        String invoiceEmail = CommonValidator.verifyParam(email, "邮箱不能为空");
        //邮箱校验
        if (!VerifyUtil.verifyEmail(invoiceEmail)) {
            return new ResponseEntity(500, "请输入正确的邮箱");
        }
        List<EinvoiceHead> fpqqlsh = einvoiceService.getByFpqqlsh(fqqlsh);
        if (fpqqlsh.size() != 1) {
            return new ResponseEntity(500, "当前发票不存在");
        }
        try {
            String s = HttpUtil.get(insideServiceUrl + "redis/getInvoice?key=" + invoiceLsh);
            //获取到redis的值
            if (s != null && !s.equals("")) {
                Long num = Long.valueOf(s);
                if (num > 1) {
                    HttpUtil.get(insideServiceUrl + "redis/saveInvoice?key=" + invoiceLsh + "&value=" + --num);
                }
                //超过设定的次数
                else {
                    return new ResponseEntity(500, "当前发票已经超过发送次数,请明日再试");
                }
            } else {
                HttpUtil.get(insideServiceUrl + "redis/saveInvoice?key=" + invoiceLsh + "&value=" + 3);
            }
        } catch (IOException e) {
            log.error("获取发票邮箱发送次数失败", e.getMessage());
            e.printStackTrace();
            return new ResponseEntity(500, "获取发票邮箱发送次数失败");
        }
        try {
            HttpUtil.get(insideServiceUrl + "email/sendAttachmentMailFileToFriend?fpqqlsh=" + invoiceLsh + "&email=" + email);

        } catch (IOException e) {
            log.error("发票发送到邮箱失败", e.getMessage());
            e.printStackTrace();
        }
        return new ResponseEntity(200, "发送成功,请查收");
    }
}
