package project.postmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@ComponentScan(basePackages = {"project"})
@EnableEurekaClient
@EnableJpaAuditing
@SpringBootApplication(scanBasePackages = {"project"})
@EnableAutoConfiguration
public class PostService {

    public static void main(String[] args) {
        SpringApplication.run(PostService.class, args);
    }
}
