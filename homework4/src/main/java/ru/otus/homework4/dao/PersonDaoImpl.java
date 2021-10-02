package ru.otus.homework4.dao;

import org.springframework.stereotype.Component;
import ru.otus.homework4.domain.Person;

@Component
public class PersonDaoImpl implements PersonDao {
    @Override
    public Person getPerson(String firstName, String lastName) {
        return new Person(firstName, lastName);
    }
}