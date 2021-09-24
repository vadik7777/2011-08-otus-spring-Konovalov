package ru.otus.homework4.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.otus.homework4.annotation.SpringBootTestWithTestProfile;

import static org.junit.jupiter.api.Assertions.assertFalse;

@DisplayName("QuestionDaoImpl test")
@SpringBootTestWithTestProfile
class QuestionDaoImplTest {

    @Autowired
    private QuestionDao questionDao;

    @DisplayName("shouldCorrectGetAll test")
    @Test
    void shouldCorrectGetAll() {
        assertFalse(questionDao.getAll().isEmpty());
    }
}