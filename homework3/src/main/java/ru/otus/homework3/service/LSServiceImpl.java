package ru.otus.homework3.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@RequiredArgsConstructor
@Service
public class LSServiceImpl implements LSService {

    private final Locale currentLocale = Locale.getDefault();
    private final MessageSource messageSource;

    @Override
    public String getMessage(String message, Object... args) {
        return messageSource.getMessage(message, args, currentLocale);
    }
}
