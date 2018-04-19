package ru.sberbank.vkr.microblog.webuiservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@EnableEurekaClient
@SpringBootApplication
public class WebUiServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebUiServiceApplication.class, args);
	}


}
