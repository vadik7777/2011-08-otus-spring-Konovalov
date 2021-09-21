package ru.otus.homework3.service;

import java.util.Locale;

public interface LSService {

    String getMessage(String message, Object ... args);

    void setCurrentLocale(Locale currentLocale);
}
