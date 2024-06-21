package com.example.demo;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {
			String[] beanNames = ctx.getBeanDefinitionNames();
			Arrays.sort(beanNames);

			for (String beanName : beanNames){
				System.out.println(beanName);
			}
		};
	}


}
