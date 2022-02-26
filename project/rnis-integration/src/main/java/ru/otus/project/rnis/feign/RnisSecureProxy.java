package ru.otus.project.rnis.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.project.rnis.config.RnisSecureRequestInterceptorConfig;
import ru.otus.project.rnis.dto.rnis.ObjectInfoDto;
import ru.otus.project.rnis.dto.rnis.TreeDto;

@FeignClient(name = "rnis-secure", url = "${rnis.url}", configuration = {RnisSecureRequestInterceptorConfig.class})
public interface RnisSecureProxy {

    @GetMapping(value = "/ping")
    ResponseEntity<String> ping();

    @GetMapping(value = "/fullobjinfo")
    ObjectInfoDto fullObjInfo(@RequestParam Long oid);

    @GetMapping(value = "/gettree")
    TreeDto getTree(@RequestParam(defaultValue = "true") boolean all);
}