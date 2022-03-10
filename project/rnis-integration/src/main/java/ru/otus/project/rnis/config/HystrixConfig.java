package ru.otus.project.rnis.config;

import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Configuration;

@EnableHystrixDashboard
@EnableHystrix
@Configuration
public class HystrixConfig {
}