package com.zenika.skyjo;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition
public class SkyjoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SkyjoApplication.class, args);
	}

}
