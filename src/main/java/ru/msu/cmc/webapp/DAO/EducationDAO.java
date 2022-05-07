package ru.msu.cmc.webapp.DAO;

import ru.msu.cmc.webapp.models.Company;
import ru.msu.cmc.webapp.models.Education;

import java.util.Collection;

public interface EducationDAO {
    Education getById(Long id);
    Collection<Education> getAll();
    void add(Education entity);
    void delete(Education entity);
    void update(Education entity);
}