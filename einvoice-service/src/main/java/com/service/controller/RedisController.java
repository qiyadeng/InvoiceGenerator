package com.service.controller;

import com.service.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Effort
 * @description
 * @date 2020/10/17 3:56 下午
 */
@Controller
@RequestMapping("/redis")
public class RedisController {
    @Autowired
   private RedisService redisService;

   @RequestMapping("/getInvoice")
   @ResponseBody
   public String getInvoice(@RequestParam("key") String key) {
      String value = redisService.get(key);
      return value != null?value:null;
   }
   @RequestMapping("/saveInvoice")
   @ResponseBody
   public boolean saveInvoice(@RequestParam("key")String key,@RequestParam("value")String value){
       try{
           redisService.setOrUpdate(key,value,Long.valueOf(60*60*24));
           return true;
       }catch (Exception e) {
           return false;
       }
   }
}
