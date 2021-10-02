package ru.otus.homework4.service;

import ru.otus.homework4.domain.Person;

public interface PersonService {

    Person getPerson(String firstName, String lastName);
}