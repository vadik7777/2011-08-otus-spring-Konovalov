package ru.otus.project.rnis.simulator.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("simulator.secure")
public class SimulatorSecureConfig {
    private String login;
    private String password;
    private String cookieValue;
}