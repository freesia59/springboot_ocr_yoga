package com.yoga.springboot_ocr_yoga;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.yoga.springboot_ocr_yoga.mapper")
public class SpringbootOcrYogaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootOcrYogaApplication.class, args);
    }

}
