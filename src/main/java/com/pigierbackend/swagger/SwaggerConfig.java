package com.pigierbackend.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI krandjApi() {
        return new OpenAPI()
        .info(new Info()
        .title("Krandja API")
        .version("1.0")
        .description("Application de Gestion de PIGIER"));
    }

}
