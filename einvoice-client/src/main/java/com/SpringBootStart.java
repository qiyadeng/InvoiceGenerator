package com;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * Created by sdyang on 2016/10/16.
 */
@SpringBootApplication
public class SpringBootStart {
    public static void main(String[] args) {
       SpringApplication.run(SpringBootStart.class, args);
    }
}
