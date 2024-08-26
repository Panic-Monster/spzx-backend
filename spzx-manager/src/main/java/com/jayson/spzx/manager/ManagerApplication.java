package com.jayson.spzx.manager;

import com.jayson.spzx.manager.properties.UserAuthProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author: Jayson_Y
 * @date: 2024/8/24
 * @project: spzx-parent
 */
@SpringBootApplication()
@MapperScan("com.jayson.spzx.manager.mapper")
@ComponentScan("com.jayson.spzx")
@EnableConfigurationProperties(value = {UserAuthProperties.class})
public class ManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManagerApplication.class, args);
    }
}
