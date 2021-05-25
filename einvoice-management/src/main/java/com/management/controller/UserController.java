package com.management.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

import com.management.commons.base.BaseController;
import com.management.commons.utils.DigestUtils;
import com.management.commons.utils.PageInfo;
import com.management.commons.utils.StringUtils;
import com.management.model.*;
import com.management.model.vo.UserVo;
import com.management.service.IEinvoiceCachetService;
import com.management.service.IUserService;
import com.management.service.ProvinceTemplateService;
import com.management.service.TaxRateDetailService;
import com.management.util.NetUtil;
import com.management.util.RandomKey;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


/**
 * @description：用户管理
 * @author：zhixuan.wang
 * @date：2015/10/1 14:51
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private IUserService userService;

    @Value(value = "${file.root.path}")
    private String fileRootPath;

    @Autowired
    private IEinvoiceCachetService cachetService;

    @Autowired
    private ProvinceTemplateService templateService;

    @Autowired
    private TaxRateDetailService detailService;

    @Value("${management.url}")
    private String managementUrl;

    /**
     * 用户管理页
     *
     * @return
     */
    @RequestMapping(value = "/manager", method = RequestMethod.GET)
    public String manager() {
        return "admin/user";
    }

    /**
     * 用户管理列表
     *
     * @param userVo
     * @param page
     * @param rows
     * @param sort
     * @param order
     * @return
     */
    @RequestMapping(value = "/dataGrid", method = RequestMethod.POST)
    @ResponseBody
    public Object dataGrid(UserVo userVo, Integer page, Integer rows, String sort, String order) {
        PageInfo pageInfo = new PageInfo(page, rows);
        Map<String, Object> condition = new HashMap<String, Object>();

        if (StringUtils.isNotBlank(userVo.getName())) {
            condition.put("name", userVo.getName());
        }
        if (userVo.getOrganizationId() != null) {
            condition.put("organizationId", userVo.getOrganizationId());
        }
        if (userVo.getCreatedateStart() != null) {
            condition.put("startTime", userVo.getCreatedateStart());
        }
        if (userVo.getCreatedateEnd() != null) {
            condition.put("endTime", userVo.getCreatedateEnd());
        }
        pageInfo.setCondition(condition);
        userService.selectDataGrid(pageInfo);
        return pageInfo;
    }

    /**
     * 添加用户页
     *
     * @return
     */
    @RequestMapping(value = "addPage", method = RequestMethod.GET)
    public String addPage() {
        return "admin/userAdd";
    }

    /**
     * 添加用户
     *
     * @param userVo
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    @ResponseBody
    public Object add(UserVo userVo,@RequestParam("file") MultipartFile file) {
        User u = userService.selectByLoginName(userVo.getLoginName());
        if (u != null) {
            return renderError("用户名已存在!");
        }
        Subject subject = SecurityUtils.getSubject();
        User user = userService.selectByLoginName(subject.getPrincipal().toString());
        if (file.isEmpty()) {
            throw new IllegalArgumentException("上传失败，请选择文件");
        }
        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        if (!suffix.equals(".png")) {
            throw new IllegalArgumentException("文件格式错误");
        }
        String fileName = UUID.randomUUID().toString().replaceAll("-","");
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(new Date()).replaceAll("-", "");
        String filePath = fileRootPath+"einvoice"+File.separator+"cachet"+ File.separator+date+File.separator;
        boolean upload = NetUtil.upload(file,filePath, fileName, suffix);
        if (!upload) {
            throw new IllegalArgumentException("图章上传失败");
        }
        EinvoiceCachet cachet = new EinvoiceCachet();
        cachet.setUploadUserId(user.getId());
        cachet.setUuid(fileName);
        cachet.setPath(date+File.separator+fileName+suffix);
        cachetService.save(cachet);
        userVo.setEinvoice_cachet_id(cachet.getId());
        //商户编码
        userVo.setPassword(DigestUtils.md5Hex(userVo.getPassword()));
        userVo.setMd5key(RandomKey.getRandomKey());
        userVo.setStatus(0);
        userService.insertByVo(userVo);
        return renderSuccess("添加成功");
    }

    /**
     * 编辑用户页
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/editPage")
    public String editPage(Long id, Model model) {
        UserVo userVo = userService.selectVoById(id);
        List<Role> rolesList = userVo.getRolesList();
        List<Long> ids = new ArrayList<Long>();
        for (Role role : rolesList) {
            ids.add(role.getId());
        }
        String taxBankNo = userVo.getTax_yhzh();
        String taxAddressPhone = userVo.getTax_dzdh();
        userVo.setTax_dh(taxAddressPhone.substring(taxAddressPhone.indexOf(" ")+1));
        userVo.setTax_zh(taxBankNo.substring(taxBankNo.indexOf(" ")+1));
        userVo.setTax_dz(taxAddressPhone.substring(0,taxAddressPhone.indexOf(" ")));
        userVo.setTax_yh(taxBankNo.substring(0,taxBankNo.indexOf(" ") ));
        Long detailId = userVo.getTax_rate_detail_id();
        String goods = detailService.getById(detailId).getDescriptionGoods();
        ProvinceTemplate template = templateService.getTemplateById(userVo.getTemplate_id());
        model.addAttribute("user", userVo);
        model.addAttribute("roleIds", ids);
        model.addAttribute("template",template);
        model.addAttribute("serviceName",goods);
        return "admin/userEdit";
    }

    /**
     * 编辑用户
     *
     * @param userVo
     * @return
     */
    @RequestMapping(value = "/edit",produces = "application/json;charset=utf-8")
    @ResponseBody
    public Object edit(UserVo userVo,@RequestParam(value = "file",required = false) MultipartFile file) {
        User u = userService.selectByLoginName(userVo.getLoginName());
        if (u != null && u.getId() != userVo.getId()) {
            return renderError("用户名已存在!");
        }
        if (StringUtils.isNotBlank(userVo.getPassword())) {
            userVo.setPassword(DigestUtils.md5Hex(userVo.getPassword()));
        }else {
            userVo.setPassword(null);
        }
        Subject subject = SecurityUtils.getSubject();
        User user = userService.selectByLoginName(subject.getPrincipal().toString());
        if (file != null && !file.isEmpty()) {
            String originalFilename = file.getOriginalFilename();
            String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
            if (!suffix.equals(".png")) {
                throw new IllegalArgumentException("文件格式错误");
            }
            String fileName = UUID.randomUUID().toString().replaceAll("-","");
            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
            String date=format.format(new Date()).replaceAll("-", "");
            String filePath = fileRootPath+"einvoice"+File.separator+"cachet"+ File.separator+date+File.separator;
            boolean upload = NetUtil.upload(file,filePath, fileName, suffix);
            if (!upload) {
                throw new IllegalArgumentException("图章上传失败");
            }
            EinvoiceCachet cachet = new EinvoiceCachet();
            cachet.setUploadUserId(user.getId());
            cachet.setUuid(fileName);
            cachet.setPath(date+File.separator+fileName+suffix);
            cachetService.save(cachet);
            userVo.setEinvoice_cachet_id(cachet.getId());
        }
        userService.updateByVo(userVo);
        return renderSuccess("修改成功！");
    }

    /**
     * 修改密码页
     *
     * @return
     */
    @RequestMapping(value = "/editPwdPage", method = RequestMethod.GET)
    public String editPwdPage() {
        return "admin/userEditPwd";
    }

    /**
     * 修改密码
     *
     * @param oldPwd
     * @param pwd
     * @return
     */
    @RequestMapping(value = "/editUserPwd",produces = "application/json;charset=utf-8")
    @ResponseBody
    public Object editUserPwd(String oldPwd, String pwd) {
        if (!getCurrentUser().getPassword().equals(DigestUtils.md5Hex(oldPwd))) {
            return renderError("老密码不正确!");
        }

        userService.updatePwdByUserId(getUserId(), DigestUtils.md5Hex(pwd));
        return renderSuccess("密码修改成功！");
    }

    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete",produces = "application/json;charset=utf-8")
    @ResponseBody
    public Object delete(Long id) {
        userService.deleteUserById(id);
        return renderSuccess("删除成功！");
    }

    @RequestMapping(value = "/merchantLogin",method = RequestMethod.POST)
    @ResponseBody
    public String merchantLogin(String username, String password) {
        User user = userService.selectByLoginName(username);
        Map map = new HashMap();
        if (null != user && password.equals(DigestUtils.md5Hex(user.getPassword()))) {
            return RandomKey.getRandomKey();
        }
        return null;
    }
}
