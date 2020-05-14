package com.perficient.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * Use this class to hold application wide configurations and the application entry point.
 */
@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan
public class SpringbootApplication {

    /**
     * Application entry point for a Spring Boot application.
     * @param args Command line args are ignored by this application.
     */
    public static void main(final String ...args) {
        SpringApplication.run(SpringbootApplication.class, args);
    }

    /**
     *
     * @return An instance of a password encoder used by the application.
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
