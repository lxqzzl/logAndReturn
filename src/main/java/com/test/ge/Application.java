package com.test.ge;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 启动类
 *
 * @author lxq
 */

@SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class)
@MapperScan({"com.test.ge.dao.*"} )
@EnableAsync
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}