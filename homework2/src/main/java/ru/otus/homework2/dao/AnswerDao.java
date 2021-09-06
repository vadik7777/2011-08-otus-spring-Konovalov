package ru.otus.homework2.dao;

import ru.otus.homework2.domain.Answer;

import java.util.List;

public interface AnswerDao {

    List<Answer> getAllByIdQuestion(Long questionId);

}
