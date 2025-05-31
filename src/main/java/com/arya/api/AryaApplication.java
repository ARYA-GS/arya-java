package com.arya.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AryaApplication {

	public static void main(String[] args) {
		SpringApplication.run(AryaApplication.class, args);
	}

}
