package ru.otus.junit.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.junit.dao.PersonDao;
import ru.otus.junit.dao.PersonNotFoundException;
import ru.otus.junit.domain.Person;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.eq;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class PersonServiceImplTest {

    @Mock
    private PersonDao personDao;
    @InjectMocks
    private PersonServiceImpl personService;

    @Test
    void getByName() {
        Person person = new Person(10, "Ivan");

        given(personDao.getByName(eq("Ivan")))
                .willReturn(person);

        assertThat(personService.getByName("Ivan"))
                .isEqualTo(person); // TODO: сравните с помощью equals
    }

    @Test
    void getAll() {
        given(personDao.getAll()).willReturn(Arrays.asList(new Person(44, "Ivan")));
        assertThat(personService.getAll().size()).isEqualTo(1);
    }

    @Test
    void existsWithName() {
        given(personDao.getByName(eq("Ivan"))).willReturn(new Person(10, "Ivan"));
        assertThat(personService.existsWithName("Ivan")).isTrue();
        given(personDao.getByName(eq("Petr"))).willThrow(PersonNotFoundException.class);
        assertThatExceptionOfType(PersonNotFoundException.class)
                .isThrownBy(() -> {personService.existsWithName("Petr");});
    }

    @Test
    void save() {
    }
}
