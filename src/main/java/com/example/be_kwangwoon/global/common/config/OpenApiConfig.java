package com.example.be_kwangwoon.global.common.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(servers = {@Server(url = "http://together-env.eba-idjepbda.ap-northeast-2.elasticbeanstalk.com/", description = "production server url"),
        @Server(url = "http://localhost:8080", description = "dev server url")
})
@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("투게더 API")
                .version("v1.0.0")
                .description("투게더 API");

        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("Bearer 토큰", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                        )
                )
                .addSecurityItem(new SecurityRequirement().addList("Bearer 토큰"))
                .info(info);
    }


}