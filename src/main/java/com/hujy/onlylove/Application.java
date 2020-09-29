package com.hujy.onlylove;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Description
 *
 * @author hujy
 * @version 1.0
 * @date 2020-09-07 16:42
 */
@SpringBootApplication(scanBasePackages = "com.hujy.onlylove")
@MapperScan(value = {"com.hujy.onlylove.mapper"})
@EnableTransactionManagement
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
