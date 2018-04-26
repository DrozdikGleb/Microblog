package ru.sberbank.vkr.microblog.serviceregistration;

import com.github.vanroy.cloud.dashboard.config.EnableCloudDashboard;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;


@SpringBootApplication
@EnableEurekaServer
@EnableCloudDashboard
@EnableDiscoveryClient
public class ServiceRegistrationServer extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(ServiceRegistrationServer.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ServiceRegistrationServer.class);
    }

}
