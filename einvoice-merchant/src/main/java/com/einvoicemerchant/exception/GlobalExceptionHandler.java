package com.einvoicemerchant.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: Effort
 * @date: 2021-04-16
 * @time: 10:58 上午
 * @description: 全局异常处理
 */
@CrossOrigin
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    public void errorHandler(HttpServletRequest request, HttpServletResponse response, Exception e) {
        LOGGER.error("系统出现异常",e);
    }
}
