package ru.msu.cmc.webapp.DAO;

import ru.msu.cmc.webapp.models.Company;
import ru.msu.cmc.webapp.models.Education;
import ru.msu.cmc.webapp.models.Person;

import java.util.Collection;
import java.util.List;

public interface PersonDAO {
    Person getById(Long id);
    Collection<Person> getAll();
    void add(Person entity);
    void delete(Person entity);
    void update(Person entity);
    List<Person> getAllPersonByName(String personName);
    String getPersonEducationById(Long id);
}