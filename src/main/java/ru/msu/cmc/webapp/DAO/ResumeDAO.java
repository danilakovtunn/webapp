package ru.msu.cmc.webapp.DAO;

import ru.msu.cmc.webapp.models.Vacancy;
import ru.msu.cmc.webapp.models.Education;
import ru.msu.cmc.webapp.models.Resume;
import ru.msu.cmc.webapp.models.Company;
import java.util.List;

public interface ResumeDAO extends CommonDAO<Resume, Long> {
    List<Resume> getAllResumeBySearchStatus();
    List<Resume> getAllResumeByPerson(String companyName);
    List<Resume> getAllResumeByPosition(String positionName);
    List<Resume> getAllResumeBySalary(Long salaryFrom, Long salaryTo);
    List<Resume> getAllResumeByEducation(String educationName);
    List<Resume> getAllResumeByVacancy(Vacancy vacancy);
}