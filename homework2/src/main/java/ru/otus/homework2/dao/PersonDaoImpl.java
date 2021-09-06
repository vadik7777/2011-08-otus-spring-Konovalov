package ru.otus.homework2.dao;

import org.springframework.stereotype.Service;
import ru.otus.homework2.domain.Person;

@Service
public class PersonDaoImpl implements PersonDao {

    private Person person = new Person();

    @Override
    public Person getPerson() {
        return person;
    }
}
