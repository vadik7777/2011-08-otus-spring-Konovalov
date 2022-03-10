package ru.otus.project.rnis.config;

import feign.RequestInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import ru.otus.project.rnis.service.RnisAuthenticationService;
import ru.otus.project.rnis.constants.RnisConstants;

@RequiredArgsConstructor
@Component
public class RnisSecureRequestInterceptorConfig {

    private final RnisAuthenticationService rnisAuthenticationService;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            var cookie = rnisAuthenticationService.checkConnectionAndGetCookie();
            requestTemplate.header(RnisConstants.HEADER_COOKIE, cookie);
        };
    }
}