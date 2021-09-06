package ru.otus.homework2.service;

import ru.otus.homework2.domain.Answer;

import java.util.List;

public interface AnswerService {

    List<Answer> getAllByIdQuestion(Long questionId);

}
