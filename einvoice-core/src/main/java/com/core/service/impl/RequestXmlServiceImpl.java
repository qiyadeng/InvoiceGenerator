package com.core.service.impl;

import com.core.bean.RequestXml;
import com.core.dao.RequestXmlDao;
import com.core.service.RequestXmlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author Effort
 * @description
 * @date 2020/10/21 9:31 上午
 */
@Service
@Transactional
public class RequestXmlServiceImpl implements RequestXmlService {
    @Autowired
    private RequestXmlDao xmlDao;

    @Override
    public RequestXml save(RequestXml requestXml) {
        requestXml.setCreateTime(new Date());
        requestXml.setStatus(1);
        return xmlDao.save(requestXml);
    }

    @Override
    public RequestXml findByFileName(String fileName) {
        return xmlDao.findByFileName(fileName);
    }
}
