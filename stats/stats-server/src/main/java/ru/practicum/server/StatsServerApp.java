package ru.practicum.server;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.boot.SpringApplication;

/**
 * @author Oleg Khilko
 */

@SpringBootApplication
public class StatsServerApp {
    public static void main(String[] args) {
        SpringApplication.run(StatsServerApp.class, args);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
}
