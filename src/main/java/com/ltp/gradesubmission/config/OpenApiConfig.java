package com.ltp.gradesubmission.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {
    @Bean
    OpenAPI openAPI() {
        return new OpenAPI()
        .info(new Info()
            .title("Grades API")
            .description("An API that can manage grades")
            .version("v1.0.0"));
    }
}
