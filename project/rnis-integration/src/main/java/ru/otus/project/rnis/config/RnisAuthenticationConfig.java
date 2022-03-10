package ru.otus.project.rnis.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("rnis.authentication")
public class RnisAuthenticationConfig {
    private String login;
    private String password;
    private String lang;
    private String timezone;
}