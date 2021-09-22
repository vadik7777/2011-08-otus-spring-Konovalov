package ru.otus.homework3.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LSIOServiceImpl implements LSIOService {

    private final IOService ioService;
    private final LSService lsService;

    @Override
    public void write(String message, Object... args) {
        message = lsService.getMessage(message, args);
        ioService.write(message);
    }
}
