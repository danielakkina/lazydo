package com.lazydo.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LazyDoBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(LazyDoBackendApplication.class, args);
	}

}
