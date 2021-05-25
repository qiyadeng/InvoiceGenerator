package com.management.service;

import com.baomidou.framework.service.ISuperService;
import com.management.commons.utils.PageInfo;
import com.management.model.ProvinceTemplate;

import java.util.List;

/**
 * @author Effort
 * @description
 * @date 2020/9/25 5:24 下午
 */
public interface ProvinceTemplateService extends ISuperService<ProvinceTemplate> {
    void listProvinceTemplate(PageInfo pageInfo);
    void deleteProvinceTemplate(Long id);
    void saveProvinceTemplate(ProvinceTemplate template);
    ProvinceTemplate getTemplateByProvince(String province);
    ProvinceTemplate getTemplateByUuid(String uuid);
    ProvinceTemplate getTemplateById(Long id);
    void updateProvinceTemplate(ProvinceTemplate template);
    List<ProvinceTemplate> listTemplate();
}
