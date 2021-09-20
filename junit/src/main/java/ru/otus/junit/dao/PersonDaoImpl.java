package ru.otus.junit.dao;

import ru.otus.junit.domain.Person;

import java.util.ArrayList;
import java.util.List;

public class PersonDaoImpl implements PersonDao {

    List<Person> personList = new ArrayList<>(){{
        add(new Person(25, "Ivan"));
        add(new Person(26, "Sergey"));
    }};
    @Override
    public Person getByName(String name) throws PersonNotFoundException {

        return personList.stream()
                .filter(person -> person.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new PersonNotFoundException("not found"));
    }

    @Override
    public List<Person> getAll() {
        return personList;
    }

    @Override
    public void deleteByName(String name) throws PersonNotFoundException {
        Person person = getByName(name);
        personList.remove(person);
    }

    @Override
    public void save(Person person) {
        try {
            Person personByName = getByName(person.getName());
            personByName.setAge(person.getAge());
            personByName.setName(person.getName());
        } catch (PersonNotFoundException e) {
            personList.add(person);
        }
    }
}
