package com.example.demo;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;

public class DiceApplication {
 public static void main(String[] args) {
    
    SpringApplication app = new SpringApplication(
        DiceApplication.class
    );
    app.setBannerMode(Banner.Mode.OFF);
    app.run(args);
 }   
}
