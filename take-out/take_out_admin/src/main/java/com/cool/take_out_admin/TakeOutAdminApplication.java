package com.cool.take_out_admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@SpringBootApplication
@ComponentScan(basePackages = {"com.cool"})
public class TakeOutAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(TakeOutAdminApplication.class, args);
    }

}
