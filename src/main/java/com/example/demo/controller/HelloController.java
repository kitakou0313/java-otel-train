package com.example.demo.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * HelloController
 */
@RestController
public class HelloController {
    @GetMapping("/")
    public String index() {
        return "Hello, from Springboot";
        
    }
}
