package ru.otus.project.rnis.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@ConditionalOnProperty(prefix = "rnis-service", name = "scheduler-enable", havingValue = "true")
@Configuration
@EnableScheduling
public class SchedulerConfig {
}