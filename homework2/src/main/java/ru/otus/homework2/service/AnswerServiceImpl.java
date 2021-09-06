package ru.otus.homework2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework2.dao.AnswerDao;
import ru.otus.homework2.domain.Answer;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AnswerServiceImpl implements AnswerService {

    private final AnswerDao answerDao;

    @Override
    public List<Answer> getAllByIdQuestion(Long questionId) {
        return answerDao.getAllByIdQuestion(questionId);
    }
}
