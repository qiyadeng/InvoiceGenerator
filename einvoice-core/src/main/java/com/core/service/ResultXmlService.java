package com.core.service;

import com.core.bean.ResultXml;

/**
 * @author Effort
 * @description
 * @date 2020/10/21 9:30 上午
 */
public interface ResultXmlService {
    public ResultXml getByFileName(String fileName);
    public ResultXml save(ResultXml resultXml);
}
