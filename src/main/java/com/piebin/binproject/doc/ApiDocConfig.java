package com.piebin.binproject.doc;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(servers = {@Server(url = "/")})
public class ApiDocConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("Bin project Backend")
                        .description("Spring Boot API")
                        .version("v0.0.1"));
    }

    // API
    @Bean
    public GroupedOpenApi defaultAPI() {
        return GroupedOpenApi.builder()
                .group("Default API")
                .pathsToMatch("/**")
                .build();
    }

    @Bean
    public GroupedOpenApi accountAPI() {
        return GroupedOpenApi.builder()
                .group("Account API")
                .pathsToMatch("/api/account/**")
                .build();
    }
}
