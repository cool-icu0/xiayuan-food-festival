package com.cool.take_out_notify;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.stereotype.Component;

@SpringBootApplication
@ComponentScans({@ComponentScan("com.cool.*")
})
@MapperScan(basePackages = {"com.cool.mapper"})
public class TakeOutNotifyApplication {

    public static void main(String[] args) {
        SpringApplication.run(TakeOutNotifyApplication.class, args);
    }

}
