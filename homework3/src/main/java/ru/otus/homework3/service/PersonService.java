package ru.otus.homework3.service;

import ru.otus.homework3.domain.Person;

public interface PersonService {

    Person getPerson(String firstName, String lastName);
}