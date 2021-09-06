package ru.otus.homework2.domain;

import lombok.Data;

@Data
public class Person {
    private String firstName;
    private String lastName;
    private Boolean passed = false;
    private Integer correctAnswers = 0;
    private Integer wrongAnswers = 0;
}
