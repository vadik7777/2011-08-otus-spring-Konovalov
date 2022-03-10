package ru.otus.project.rnis.model;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class CookieData {

    private volatile String cookie;
    private volatile String cookieValue;

    public synchronized void setData(String cookie, String cookieValue) {
        this.cookie = cookie;
        this.cookieValue = cookieValue;
    }
}