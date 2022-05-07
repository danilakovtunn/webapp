package ru.msu.cmc.webapp.DAO;

import ru.msu.cmc.webapp.models.Company;
import ru.msu.cmc.webapp.models.Person;
import ru.msu.cmc.webapp.models.Places;

import java.util.Collection;
import java.util.List;

public interface PlacesDAO {
    Places getById(Long id);
    Collection<Places> getAll();
    void add(Places entity);
    void delete(Places entity);
    void update(Places entity);
    List<Places> getAllPlacesByPersonId(Long personId);
}