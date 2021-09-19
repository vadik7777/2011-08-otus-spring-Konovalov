package ru.otus.homework3.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework3.dao.QuestionDao;
import ru.otus.homework3.domain.Question;

import java.util.List;

@RequiredArgsConstructor
@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionDao questionDao;

    @Override
    public List<Question> getAll() {
        return questionDao.getAll();
    }
}
