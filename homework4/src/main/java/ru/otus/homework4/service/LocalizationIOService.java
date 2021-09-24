package ru.otus.homework4.service;

public interface LocalizationIOService {

    void write(String message, Object... args);

    String readString();

    int readInt();

}