package com.management.controller;

import com.management.commons.base.BaseController;
import com.management.commons.utils.IOUtils;
import com.management.commons.utils.PageInfo;
import com.management.model.ProvinceTemplate;
import com.management.service.ProvinceTemplateService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Effort
 * @description
 * @date 2020/9/25 5:30 下午
 */
@Controller
@RequestMapping("/provinceTemplate")
public class ProvinceTemplateController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(ProvinceTemplateController.class);

    @Autowired
    private ProvinceTemplateService templateService;
    @Value("${file.root.path}")
    private String fileRootPath;

    @RequestMapping(value = "/manager", method = RequestMethod.GET)
    public String  manager() {
        return "provinceTemplate/head";
    }

    /**
     * Pdf模板列表
     * @param province
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping(value = "/listProvinceTemplate",method = RequestMethod.POST)
    @ResponseBody
    public Object listProvinceTemplate(String province,Integer page, Integer rows) {
        PageInfo pageInfo = new PageInfo(page, rows,"create_time","desc");
        Map<String, Object> condition = new HashMap<String, Object>();
        if (StringUtils.isNotEmpty(province)) {
            condition.put("province",province);
        }
        pageInfo.setCondition(condition);
        templateService.listProvinceTemplate(pageInfo);
        List<ProvinceTemplate> infoRows = pageInfo.getRows();
        for (ProvinceTemplate row : infoRows) {
            row.setTemplateUrl("/provinceTemplate/downloadTemplate/"+row.getUuid());
        }
        pageInfo.setRows(infoRows);
        return  pageInfo;
    }
    @RequestMapping(value = "/listTemplate")
    @ResponseBody
    public Object listTemplate(){
        return templateService.listTemplate();
    }

    /**
     *添加模板页面
     * @return
     */
    @RequestMapping(value = "/addPage", method = RequestMethod.GET)
    public String provinceAdd() {
        return "provinceTemplate/provinceAdd";
    }

    /**
     * 添加PDF模板
     * @return
     */
    @RequestMapping(value = "/saveTemplate", method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    @ResponseBody
    public Object saveTemplate(String province,@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("上传失败，请选择文件");
        }
        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        if (!suffix.equals(".pdf")) {
            throw new IllegalArgumentException("上传失败，文件格式错误");
        }
        ProvinceTemplate provinceTemplate = templateService.getTemplateByProvince(province);
        if (null != provinceTemplate) {
            throw  new IllegalArgumentException("该省份已存在");
        }
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        String date=format.format(new Date()).replaceAll("-", "");
        String targetFilePath = fileRootPath +"einvoice"+File.separator+"template"+File.separator+date+File.separator;
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        File targetFile = new File(targetFilePath + uuid+".pdf");
        //创建目录
        if (!targetFile.getParentFile().exists()) {
            targetFile.getParentFile().mkdirs();
        }
        boolean flag = true;
        try( FileOutputStream fileOutputStream=new FileOutputStream(targetFile)) {
            IOUtils.copy(file.getInputStream(), fileOutputStream);
        } catch (IOException e) {
            logger.error("文件上传失败，原因：{}",e.getMessage());
            flag = false;
        }
        if (flag) {
            ProvinceTemplate template = new ProvinceTemplate();
            template.setProvince(province);
            template.setUuid(uuid);
            template.setTemplateUrl(date+File.separator+uuid+".pdf");
            templateService.saveProvinceTemplate(template);
            return renderSuccess("保存成功！");
        }else {
            return renderError("保存失败！");
        }
    }

    /**
     * 发票模板下载
     * @return
     */
    @RequestMapping("/downloadTemplate/{uuid}")
    @ResponseBody
    public String downloadTemplate(@PathVariable("uuid") String uuid, HttpServletResponse response) {
        String realPath =fileRootPath+"einvoice"+File.separator+"template"+File.separator;
        ProvinceTemplate template = templateService.getTemplateByUuid(uuid);
        String fileName = template.getTemplateUrl();
        File file = new File(realPath,fileName);
        if (file.exists()) {
            try ( FileInputStream fis = new FileInputStream(file);BufferedInputStream bis = new BufferedInputStream(fis)){
                response.setHeader("content-type", "application/octet-stream");
                response.setContentType("application/octet-stream");
                response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(uuid+".pdf", "UTF-8"));
                byte[] buffer = new byte[1024];
                OutputStream os = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
                os.flush();
                return "success";
            } catch (Exception e) {
                return "failed";
            }
        }
        return "failed";
    }
    /**
     * 删除模板
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete",produces = "application/json;charset=utf-8")
    @ResponseBody
    public Object delete(Long id) {
        templateService.deleteProvinceTemplate(id);
        return renderSuccess("删除成功！");
    }
}
