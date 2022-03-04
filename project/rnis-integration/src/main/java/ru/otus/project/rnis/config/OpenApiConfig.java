package ru.otus.project.rnis.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@Configuration
public class OpenApiConfig {

    private final String version;
    private final String description;
    private final String name;

    public OpenApiConfig(@Value("${rnis-service.name}") String name,
                         @Value("${rnis-service.description}") String description,
                         @Value("${rnis-service.build.version}") String version,
                         @Value("${rnis-service.build.timestamp}") String buildTime) throws ParseException {
        this.name = name;
        this.description = description;
        this.version = new StringBuilder(version).append(", ")
                                                 .append(new SimpleDateFormat("yyyyMMdd-HHmm").parse(buildTime))
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