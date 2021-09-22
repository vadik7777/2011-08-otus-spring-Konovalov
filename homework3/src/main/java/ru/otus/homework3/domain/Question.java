package ru.otus.homework3.domain;

import lombok.Data;

import java.util.List;

@Data
public class Question {
    private final String name;
    private final List<Answer> answers;
}
