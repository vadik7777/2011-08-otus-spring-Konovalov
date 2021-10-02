package ru.otus.homework4.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.homework4.config.AppConfig;

import java.util.Locale;

@RequiredArgsConstructor
@Service
public class LocalizationServiceImpl implements LocalizationService {

    private final MessageSource messageSource;
    private final AppConfig appConfig;

    @Override
    public String getMessage(String message, Object... args) {
        return messageSource.getMessage(message, args, Locale.forLanguageTag(appConfig.getLocale()));
    }
}
