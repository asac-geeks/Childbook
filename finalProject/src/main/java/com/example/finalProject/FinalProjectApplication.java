package com.example.finalProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class FinalProjectApplication {
	@Bean
	public WebClient.Builder getWebClient(){
		return WebClient.builder();
	}

	public static void main(String[] args) {
		SpringApplication.run(FinalProjectApplication.class, args);
	}

}
