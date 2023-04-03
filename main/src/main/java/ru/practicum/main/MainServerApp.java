package ru.practicum.main;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.boot.SpringApplication;

/**
 * @author Oleg Khilko
 */

@SpringBootApplication
@ComponentScan(basePackages = "ru.practicum")
public class MainServerApp {
    public static void main(String[] args) {
        SpringApplication.run(MainServerApp.class, args);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
}
