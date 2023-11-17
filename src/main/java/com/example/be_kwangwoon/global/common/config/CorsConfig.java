package com.example.be_kwangwoon.global.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
public class CorsConfig {
    @Bean
     CorsConfigurationSource  configurationSource() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowCredentials(true);
        corsConfig.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://localhost:5500", "http://127.0.0.1:5500", "https://kw-teamhub.netlify.app"));
        corsConfig.setAllowedMethods(Arrays.asList("POST", "GET", "DELETE", "PUT"));
        corsConfig.setAllowedHeaders(List.of("Authorization"));
        corsConfig.addExposedHeader("Authorization");
//        corsConfig.addAllowedOriginPattern("*");
//        corsConfig.addAllowedHeader("*");
//        corsConfig.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);
        return source;
    }
}

