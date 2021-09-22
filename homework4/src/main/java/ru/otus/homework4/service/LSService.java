package ru.otus.homework4.service;

import java.util.Locale;

public interface LSService {

    String getMessage(String message, Object ... args);

    void setCurrentLocale(Locale currentLocale);
}
