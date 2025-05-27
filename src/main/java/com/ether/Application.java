package com.ether;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.ether.config.SdeProperties;
import com.ether.config.ServerProperties;

@SpringBootApplication
@EnableConfigurationProperties({
    ServerProperties.class,
    SdeProperties.class
})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
