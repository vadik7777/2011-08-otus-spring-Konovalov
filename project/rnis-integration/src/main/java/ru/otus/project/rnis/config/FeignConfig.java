package ru.otus.project.rnis.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;


@EnableFeignClients(basePackages = "ru.otus.project.rnis.feign")
@Configuration
public class FeignConfig {
}