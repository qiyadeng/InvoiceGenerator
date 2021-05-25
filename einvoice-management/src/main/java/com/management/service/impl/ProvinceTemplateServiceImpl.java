package com.management.service.impl;

import com.baomidou.framework.service.impl.SuperServiceImpl;
import com.baomidou.mybatisplus.plugins.Page;
import com.management.commons.utils.PageInfo;
import com.management.mapper.ProvinceTemplateMapper;
import com.management.model.ProvinceTemplate;
import com.management.service.ProvinceTemplateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


/**
 * @author Effort
 * @description
 * @date 2020/9/25 4:58 下午
 */
@Service
public class ProvinceTemplateServiceImpl extends SuperServiceImpl<ProvinceTemplateMapper, ProvinceTemplate> implements ProvinceTemplateService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ProvinceTemplateMapper templateMapper;


    @Override
    public void listProvinceTemplate(PageInfo pageInfo) {
        Page<ProvinceTemplate> page = new Page<ProvinceTemplate>(pageInfo.getNowpage(), pageInfo.getSize());
        List<ProvinceTemplate> list = templateMapper.listProvinceTemplate(page, pageInfo.getCondition());
        pageInfo.setRows(list);
    }

    @Override
    public void deleteProvinceTemplate(Long id) {
        templateMapper.deleteProvinceTemplate(id);
    }

    @Override
    public void saveProvinceTemplate(ProvinceTemplate template) {
        template.setCreateTime(new Date());
        template.setStatus(1);
        template.setUpdateTime(new Date());
         templateMapper.insert(template);
    }

    @Override
    public ProvinceTemplate getTemplateByProvince(String province) {
        return templateMapper.getTemplateByProvince(province);
    }

    @Override
    public ProvinceTemplate getTemplateByUuid(String uuid) {
        return templateMapper.getTemplateByUuid(uuid) ;
    }

    @Override
    public ProvinceTemplate getTemplateById(Long id) {
       return  templateMapper.selectById(id);
    }

    @Override
    public void updateProvinceTemplate(ProvinceTemplate template) {
        this.updateSelectiveById(template);
    }

    @Override
    public List<ProvinceTemplate> listTemplate() {
        return templateMapper.listTemplate();
    }
}
