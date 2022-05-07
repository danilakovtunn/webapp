package ru.msu.cmc.webapp.DAO;

import ru.msu.cmc.webapp.models.Vacancy;
import ru.msu.cmc.webapp.models.Education;
import ru.msu.cmc.webapp.models.Resume;
import ru.msu.cmc.webapp.models.Company;

import java.util.Collection;
import java.util.List;

public interface VacancyDAO {
    Vacancy getById(Long id);
    Collection<Vacancy> getAll();
    void add(Vacancy entity);
    void delete(Vacancy entity);
    void update(Vacancy entity);
    List<Vacancy> getAllVacancyByCompany(String companyName);
    List<Vacancy> getAllVacancyByPosition(String positionName);
    List<Vacancy> getAllVacancyBySalary(Long salaryFrom, Long salaryTo);
    List<Vacancy> getAllVacancyByEducation(String educationName);
    List<Vacancy> getAllVacancyByResume(Resume resume);
}