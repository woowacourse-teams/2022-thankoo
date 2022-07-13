package com.woowacourse.thankoo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class ThankooApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThankooApplication.class, args);
    }

}
