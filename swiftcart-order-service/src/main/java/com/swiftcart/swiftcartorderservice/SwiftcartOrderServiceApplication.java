package com.swiftcart.swiftcartorderservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SwiftcartOrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SwiftcartOrderServiceApplication.class, args);
    }

}
