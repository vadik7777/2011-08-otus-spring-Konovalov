package ru.otus.homework4.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LocalizationIOServiceImpl implements LocalizationIOService {

    private final IOService ioService;
    private final LocalizationService localizationService;

    @Override
    public void write(String message, Object... args) {
        message = localizationService.getMessage(message, args);
        ioService.write(message);
    }

    @Override
    public String readString() {
        return ioService.readString();
    }

    @Override
    public int readInt() {
        return ioService.readInt();
    }
}