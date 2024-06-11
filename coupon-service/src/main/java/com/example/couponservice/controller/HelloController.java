package com.example.couponservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/health_check")
    public String health_check() {
        return "coupon-service";
    }
}
