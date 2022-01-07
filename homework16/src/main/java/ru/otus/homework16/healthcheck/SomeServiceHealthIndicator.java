package ru.otus.homework16.healthcheck;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

@Component
public class SomeServiceHealthIndicator implements HealthIndicator {

    private final boolean active;

    public SomeServiceHealthIndicator(@Value("${app.someservice.active}") boolean active) {
        this.active = active;
    }

    @Override
    public Health health() {
        if (active) {
            return Health.up()
                    .withDetail("message", "Some service is active")
                    .build();
        } else {
            return Health.down()
                    .status(Status.OUT_OF_SERVICE)
                    .withDetail("message", "Some service is not active")
                    .build();
        }
    }
}