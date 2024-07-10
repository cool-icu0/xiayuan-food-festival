package com.cool.take_out_file;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class TakeOutFileApplication {

    public static void main(String[] args) {
        SpringApplication.run(TakeOutFileApplication.class, args);
    }

}
