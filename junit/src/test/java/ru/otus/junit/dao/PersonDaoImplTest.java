package ru.otus.junit.dao;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.junit.domain.Person;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

class PersonDaoImplTest {

    private PersonDao personDao = new PersonDaoImpl();

    @Test
    void shouldHaveCorrectGetByName() {
        assertEquals(personDao.getByName("Ivan").getName(), "Ivan");
    }

    @Test
    void getAll() {
        assertEquals(personDao.getAll().size(), 2);
    }

    @Test
    void deleteByName() {
        personDao.deleteByName("Ivan");
        assertThatExceptionOfType(PersonNotFoundException.class)
                .isThrownBy(() -> {personDao.deleteByName("Ivan");})
                .withMessage("not found");
    }

    @Test
    void save() {
        Person person = new Person(45, "Alex");
        personDao.save(person);
        assertThat(personDao.getByName("Alex")).isEqualTo(person);

    }
}