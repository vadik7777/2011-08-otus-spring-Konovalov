package ru.otus.homework3.dao;

import ru.otus.homework3.domain.Person;

public interface PersonDao {

    Person getPerson(String firstName, String lastName);
}