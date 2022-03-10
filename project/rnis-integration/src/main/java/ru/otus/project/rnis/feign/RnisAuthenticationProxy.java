package ru.otus.project.rnis.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.project.rnis.dto.rnis.AuthenticationParamsDto;
import ru.otus.project.rnis.constants.RnisConstants;

@FeignClient(name = "rnis-authentication", url = "${rnis.url}")
public interface RnisAuthenticationProxy {

    @GetMapping(value = "/connect")
    ResponseEntity<String> connect(@SpringQueryMap AuthenticationParamsDto authenticationParamsDto);

    @GetMapping(value = "/ping")
    ResponseEntity<String> ping(@CookieValue(RnisConstants.RNIS_SECURE_COOKIE) String cookie);

}