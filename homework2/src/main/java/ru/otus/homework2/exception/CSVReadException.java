package ru.otus.homework2.exception;

public class CSVReadException extends RuntimeException {

    public CSVReadException(String message) {
        super(message);
    }
}