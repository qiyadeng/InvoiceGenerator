package com.core.service.impl;

import com.core.bean.ResultXml;
import com.core.dao.ResultXmlDao;
import com.core.service.ResultXmlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author Effort
 * @description
 * @date 2020/10/21 9:30 上午
 */
@Service
@Transactional
public class ResultXmlServiceImpl implements ResultXmlService {
    @Autowired
    private ResultXmlDao xmlDao;
    @Override
    public ResultXml getByFileName(String fileName) {
        return xmlDao.findByFileName(fileName);
    }

    @Override
    public ResultXml save(ResultXml resultXml) {
        resultXml.setCreateTime(new Date());
        resultXml.setStatus(1);
        return xmlDao.save(resultXml);
    }
}
