package com.example.acgdata;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.acgdata.mapper")
public class AcgDataApplication {

    public static void main(String[] args) {
        SpringApplication.run(AcgDataApplication.class, args);
    }

}
