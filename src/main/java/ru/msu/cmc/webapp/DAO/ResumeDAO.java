package ru.msu.cmc.webapp.DAO;

import ru.msu.cmc.webapp.models.*;

import java.util.Collection;
import java.util.List;

public interface ResumeDAO {
    Resume getById(Long id);
    Collection<Resume> getAll();
    void add(Resume entity);
    void delete(Resume entity);
    void update(Resume entity);
    List<Resume> getAllResumeBySearchStatus();
    List<Resume> getAllResumeByPerson(String personName);
    List<Resume> getAllResumeByPosition(String positionName);
    List<Resume> getAllResumeBySalary(Long salaryFrom, Long salaryTo);
    List<Resume> getAllResumeByEducation(String educationName);
    List<Resume> getAllResumeByVacancy(Vacancy vacancy);
}