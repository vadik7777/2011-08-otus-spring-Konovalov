package ru.otus.project.rnis.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    private final String version;
    private final String description;
    private final String name;

    public OpenApiConfig(@Value("${open-api-info.name}") String name,
                         @Value("${open-api-info.description}") String description,
                         @Value("${open-api-info.build.version}") String version,
                         @Value("${open-api-info.build.timestamp}") String buildTime) {
        this.name = name;
        this.description = description;
        this.version = new StringBuilder(version).append(", ")
                                                 .append(buildTime)
                                                 .toString();
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(new Info().title(name)
                                            .description(description)
                                            .version(version)
        );
    }
}