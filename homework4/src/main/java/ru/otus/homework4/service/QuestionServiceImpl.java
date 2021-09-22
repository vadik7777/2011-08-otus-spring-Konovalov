package ru.otus.homework4.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework4.dao.QuestionDao;
import ru.otus.homework4.domain.Question;

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
