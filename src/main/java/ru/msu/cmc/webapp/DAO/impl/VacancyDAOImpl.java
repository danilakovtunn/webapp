package ru.msu.cmc.webapp.DAO.impl;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.msu.cmc.webapp.DAO.VacancyDAO;
import ru.msu.cmc.webapp.models.Vacancy;
import ru.msu.cmc.webapp.models.Education;
import ru.msu.cmc.webapp.models.Resume;
import ru.msu.cmc.webapp.models.Company;
import ru.msu.cmc.webapp.utils.HibernateUtility;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
public class VacancyDAOImpl implements VacancyDAO{
    @Override
    public void add(Vacancy entity) {
        Session session = HibernateUtility.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(entity);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void update(Vacancy entity) {
        Session session = HibernateUtility.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(entity);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void delete(Vacancy entity) {
        Session session = HibernateUtility.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(entity);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<Vacancy> getAll() {
        Session session = HibernateUtility.getSessionFactory().openSession();
        Query<Vacancy> query = session.createQuery
                ("FROM Vacancy", Vacancy.class);
        List<Vacancy> result = query.getResultList();
        session.close();
        return result;
    }

    @Override
    public Vacancy getById(Long id) {
        Session session = HibernateUtility.getSessionFactory().openSession();
        Query<Vacancy> query = session.createQuery
                ("FROM Vacancy AS vacancy " +
                        "WHERE vacancy.id = :id");
        query.setParameter("id", id);
        Vacancy result;
        result = query.getSingleResult();
        session.close();
        return result;
    }

    @Override
    public List<Vacancy> getAllVacancyByCompany(String companyName) {
        Session session = HibernateUtility.getSessionFactory().openSession();
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
        session.close();
        return vacancies;
    }

    @Override
    public List<Vacancy> getAllVacancyByPosition(String positionName) {
        Session session = HibernateUtility.getSessionFactory().openSession();
        Query<Vacancy> query = session.createQuery
                ("FROM Vacancy " +
                        "WHERE position LIKE :comName");
        query.setParameter("comName", likeExpr(positionName));
        List<Vacancy> vacancies = query.getResultList();
        session.close();
        return vacancies;
    }

    @Override
    public List<Vacancy> getAllVacancyBySalary(Long salaryFrom, Long salaryTo) {
        Session session = HibernateUtility.getSessionFactory().openSession();
        Query<Vacancy> query = session.createQuery
                        ("FROM Vacancy " +
                                "WHERE offered_salary >= :from AND " +
                                "offered_salary <= :to");
        query.setParameter("from", salaryFrom);
        query.setParameter("to", salaryTo);
        List<Vacancy> vacancies = query.getResultList();
        session.close();
        return vacancies;
    }

    @Override
    public List<Vacancy> getAllVacancyByEducation(String educationName) {
        Session session = HibernateUtility.getSessionFactory().openSession();
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
        session.close();
        return vacancies;
    }

    @Override
    public List<Vacancy> getAllVacancyByResume(Resume resume) {
        Session session = HibernateUtility.getSessionFactory().openSession();
        Query helpquery = session.createQuery
                ("FROM Resume as resume " +
                        "INNER JOIN resume.person_id as person " +
                        "INNER JOIN person.education_id as education " +
                        "WHERE resume.id = :resume");
        helpquery.setParameter("resume", resume.getId());
        List<Object[]> help = helpquery.list();
        Education helpresult = (Education) (help.get(0))[2];

        Query<Vacancy> query = session.createQuery
                ("SELECT vacancy.id, vacancy.company_id, vacancy.position, vacancy.offered_salary, vacancy.experience, vacancy.required_education " +
                        "FROM Vacancy as vacancy " +
                        "INNER JOIN vacancy.required_education as education " +
                        "WHERE vacancy.position = :pos AND " +
                        "vacancy.offered_salary >= :salary AND " +
                        "vacancy.experience <= :exp AND " +
                        "education.id <= :educId");
        query.setParameter("pos", resume.getPosition());
        query.setParameter("salary", resume.getDesired_salary());
        query.setParameter("exp", resume.getExperience());
        query.setParameter("educId", helpresult.getId());
        List<Vacancy> result = query.getResultList();
        session.close();
        return result;
    }

    private String likeExpr(String param) {
        return "%" + param + "%";
    }
}