package com.service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Effort
 * @description
 * @date 2021/3/3 2:33 下午
 */
@RequestMapping
@RestController
public class HomeController {
    @RequestMapping("")
    public String index() {
        return "";
    }
}
