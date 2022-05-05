package ru.msu.cmc.webapp.DAO;

import ru.msu.cmc.webapp.models.Person;
import java.util.List;

public interface PersonDAO extends CommonDAO<Person, Long> {
    List<Person> getAllPersonByName(String companyName);
}