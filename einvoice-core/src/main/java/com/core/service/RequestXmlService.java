package com.core.service;

import com.core.bean.RequestXml;

/**
 * @author Effort
 * @description
 * @date 2020/10/21 9:31 上午
 */
public interface RequestXmlService {
    public RequestXml save(RequestXml requestXml);
    public RequestXml findByFileName(String fileName);
}
