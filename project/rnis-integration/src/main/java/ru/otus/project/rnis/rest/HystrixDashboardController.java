package ru.otus.project.rnis.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class HystrixDashboardController {

    private final String hystrixDashboardUrl;

    public HystrixDashboardController(@Value("${rnis-service.hystrix-dashboard-url}") String hystrixDashboardUrl) {
        this.hystrixDashboardUrl = hystrixDashboardUrl;
    }

    @GetMapping("/")
    public ResponseEntity redirectToHystrixDashBoardUrl() {
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(hystrixDashboardUrl));
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }
}