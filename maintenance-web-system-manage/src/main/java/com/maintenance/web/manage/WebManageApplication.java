package com.maintenance.web.manage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@Component(value = "com.maintenance.web")
public class WebManageApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebManageApplication.class, args);
    }
}
