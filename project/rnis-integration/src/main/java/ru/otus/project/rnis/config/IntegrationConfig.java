package ru.otus.project.rnis.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;

@Configuration
@IntegrationComponentScan("ru.otus.project.rnis.integration")
@EnableIntegration
public class IntegrationConfig {
}