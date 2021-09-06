package ru.otus.homework2.domain;

import lombok.Data;

@Data
public class Answer {
    private Long id;
    private Long questionId;
    private String name;
    private Boolean right;
}
