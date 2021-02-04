package com.maintenance.web.login;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@Component(value = "com.maintenance.web")
public class MaintenanceWebLoginApplication {
    public static void main(String[] args) {
        SpringApplication.run(MaintenanceWebLoginApplication.class, args);
    }
}
