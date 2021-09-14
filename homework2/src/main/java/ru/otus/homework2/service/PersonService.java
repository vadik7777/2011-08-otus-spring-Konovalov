package ru.otus.homework2.service;

import ru.otus.homework2.domain.Person;

public interface PersonService {

    Person getPerson();

    void writeFirstName();

    void writeLastName();
}