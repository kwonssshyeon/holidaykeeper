package com.planitsquare.holidaykeeper.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("StarLog API")
                        .version("v1")
                        .description("StarLog 서비스 API 문서"))
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Local server")
                ));
    }
}

