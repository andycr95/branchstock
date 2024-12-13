package com.andycaicedo.branchstock.branchstock.configuration;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.lang.NonNull;

@Configuration
@EnableWebFlux
public class WebFlux implements WebFluxConfigurer {
    @Override
    public void addCorsMappings(@NonNull CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedHeaders("*")
                .allowCredentials(false);
    }
}