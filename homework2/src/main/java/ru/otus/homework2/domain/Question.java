package ru.otus.homework2.domain;

import lombok.Data;

import java.util.List;

@Data
public class Question {
    private final String name;
    private final List<Answer> answers;
}
