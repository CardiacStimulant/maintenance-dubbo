package com.maintenance.web.login;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan(value = "com.maintenance.web")
public class WebLoginApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebLoginApplication.class, args);
    }
}
