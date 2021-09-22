package ru.otus.homework3.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.homework3.domain.Person;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("PersonDaoImpl test")
class PersonDaoImplTest {

    private PersonDao personDao = new PersonDaoImpl();

    @DisplayName("shouldCorrectGetPerson test")
    @Test
    void shouldCorrectGetPerson() {
        Person person = personDao.getPerson("Ivan", "Ivanov");
        assertThat(person).extracting("firstName", "lastName").containsExactly("Ivan", "Ivanov");
    }
}