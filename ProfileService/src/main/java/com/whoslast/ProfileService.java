package com.whoslast;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@SpringBootApplication(scanBasePackages = {"com"})
@ComponentScan(basePackages = {"com"})
@EntityScan(basePackages = {"com"})
@EnableJpaAuditing
@EnableAutoConfiguration
@EnableEurekaClient
public class ProfileService {
    public static void main(String[] args) {
        SpringApplication.run(ProfileService.class, args);
    }
}
