package com.itheima;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@MapperScan("com.itheima.mapper") //自动扫描这个包下Mapper接口
@ServletComponentScan
@SpringBootApplication
public class TliasWebManagementApplication {
    public static void main(String[] args) {
        SpringApplication.run(TliasWebManagementApplication.class, args);
    }
}
