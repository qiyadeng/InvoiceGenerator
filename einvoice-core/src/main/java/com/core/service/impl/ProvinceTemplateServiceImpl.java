package com.core.service.impl;

import com.core.bean.ProvinceTemplate;
import com.core.dao.ProvinceTemplateDao;
import com.core.service.ProvinceTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Effort
 * @description
 * @date 2020/10/21 9:57 上午
 */
@Service
@Transactional
public class ProvinceTemplateServiceImpl implements ProvinceTemplateService {
    @Autowired
    private ProvinceTemplateDao templateDao;

    @Override
    public ProvinceTemplate findById(Long id) {
        return templateDao.getProvinceTemplateById(id);
    }
}
