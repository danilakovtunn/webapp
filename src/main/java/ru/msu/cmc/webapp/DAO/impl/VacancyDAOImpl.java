package ru.msu.cmc.webapp.DAO.impl;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.msu.cmc.webapp.DAO.VacancyDAO;
import ru.msu.cmc.webapp.models.Vacancy;
import ru.msu.cmc.webapp.models.Education;
import ru.msu.cmc.webapp.models.Resume;
import ru.msu.cmc.webapp.models.Company;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
public class VacancyDAOImpl extends CommonDAOImpl<Vacancy, Long> implements VacancyDAO{

    public VacancyDAOImpl() {
        super(Vacancy.class);
    }

    @Override
    public List<Vacancy> getAllVacancyByCompany(String companyName) {
        try (Session session = sessionFactory.openSession()) {
            Query query = session.createQuery
                            ("FROM Vacancy as vacancy " +
                                    "INNER JOIN vacancy.company_id as company " +
                                    "WHERE company.name LIKE :comName");
            query.setParameter("comName", likeExpr(companyName));
            List<Object[]> result = query.list();
            List<Vacancy> vacancies = new ArrayList<Vacancy>();
            if (result.size() == 0) {
                return vacancies;
            }
            for(Object[] row : result) {
                vacancies.add((Vacancy) row[0]);
            }
            return vacancies;
        }
    }

    @Override
    public List<Vacancy> getAllVacancyByPosition(String positionName) {
        try (Session session = sessionFactory.openSession()) {
            Query<Vacancy> query = session.createQuery
                    ("FROM Vacancy " +
                            "WHERE position LIKE :comName");
            query.setParameter("comName", likeExpr(positionName));
            return query.getResultList().size() == 0 ? new ArrayList<Vacancy>() : query.getResultList();
        }
    }

    @Override
    public List<Vacancy> getAllVacancyBySalary(Long salaryFrom, Long salaryTo) {
        try (Session session = sessionFactory.openSession()) {
            Query<Vacancy> query = session.createQuery
                            ("FROM Vacancy " +
                                    "WHERE offered_salary >= :from AND " +
                                    "offered_salary <= :to");
            query.setParameter("from", salaryFrom);
            query.setParameter("to", salaryTo);
            return query.getResultList();
        }
    }

    @Override
    public List<Vacancy> getAllVacancyByEducation(String educationName) {
        try (Session session = sessionFactory.openSession()) {
            Query query = session.createQuery
                    ("FROM Vacancy as vacancy " +
                            "INNER JOIN vacancy.required_education as education " +
                            "WHERE education.educ_name LIKE :comName");
            query.setParameter("comName", likeExpr(educationName));
            List<Object[]> result = query.list();
            List<Vacancy> vacancies = new ArrayList<Vacancy>();
            if (result.size() == 0) {
                return vacancies;
            }
            for(Object[] row : result) {
                vacancies.add((Vacancy) row[0]);
            }
            return vacancies;
        }
    }

    @Override
    public List<Vacancy> getAllVacancyByResume(Resume resume) {
        try (Session session = sessionFactory.openSession()) {
            Query query = session.createQuery
                    ("FROM Vacancy as vacancy " +
                            "INNER JOIN vacancy.required_education as education " +
                            "WHERE vacancy.position = :pos AND " +
                            "vacancy.offered_salary >= :salary AND " +
                            "vacancy.experience <= :exp AND " +
                            "education.id <= (SELECT education.id " +
                                    "FROM Resume as resume " +
                                    "INNER JOIN resume.person_id as person " +
                                    "INNER JOIN person.education_id as education " +
                                    "WHERE resume = :resume)");
            query.setParameter("pos", resume.getPosition());
            query.setParameter("salary", resume.getDesired_salary());
            query.setParameter("exp", resume.getExperience());
            query.setParameter("resume", resume);
            List<Object[]> result = query.list();
            List<Vacancy> vacancies = new ArrayList<Vacancy>();
            if (result.size() == 0) {
                return vacancies;
            }
            for(Object[] row : result) {
                vacancies.add((Vacancy) row[0]);
            }
            return vacancies;
        }
    }

    private String likeExpr(String param) {
        return "%" + param + "%";
    }
}