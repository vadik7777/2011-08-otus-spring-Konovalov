package ru.otus.homework4.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework4.dao.QuestionDao;
import ru.otus.homework4.domain.Answer;
import ru.otus.homework4.domain.Question;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@DisplayName("QuestionServiceImpl test")
@ExtendWith(MockitoExtension.class)
class QuestionServiceImplTest {

    @Mock
    private QuestionDao questionDao;

    @InjectMocks
    private QuestionServiceImpl questionServiceImpl;

    @DisplayName("shouldCorrectGetAll test")
    @Test
    void shouldCorrectGetAll() {
        Question question = new Question("Question", List.of(new Answer("Answer", true)));
        given(questionDao.getAll()).willReturn(List.of(question));
        assertEquals(questionServiceImpl.getAll().get(0), question);
    }
}