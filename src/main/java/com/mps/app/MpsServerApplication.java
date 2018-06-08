package com.mps.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.mps.app")
public class MpsServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MpsServerApplication.class, args);
	}
}
