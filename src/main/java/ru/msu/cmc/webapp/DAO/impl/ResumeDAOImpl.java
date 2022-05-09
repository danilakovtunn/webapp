package ru.msu.cmc.webapp.DAO.impl;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.msu.cmc.webapp.DAO.ResumeDAO;
import ru.msu.cmc.webapp.models.Company;
import ru.msu.cmc.webapp.models.Education;
import ru.msu.cmc.webapp.models.Vacancy;
import ru.msu.cmc.webapp.models.Resume;
import ru.msu.cmc.webapp.utils.HibernateUtility;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ResumeDAOImpl implements ResumeDAO {

    @Override
    public void add(Resume entity) {
        Session session = HibernateUtility.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(entity);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void update(Resume entity) {
        Session session = HibernateUtility.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(entity);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void delete(Resume entity) {
        Session session = HibernateUtility.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(entity);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<Resume> getAll() {
        Session session = HibernateUtility.getSessionFactory().openSession();
        Query<Resume> query = session.createQuery
                ("FROM Resume", Resume.class);
        List<Resume> result = query.getResultList();
        session.close();
        return result;
    }

    @Override
    public Resume getById(Long id) {
        Session session = HibernateUtility.getSessionFactory().openSession();
        Query<Resume> query = session.createQuery
                ("FROM Resume AS resume " +
                        "WHERE resume.id = :id");
        query.setParameter("id", id);
        Resume result;
        result = query.getSingleResult();
        session.close();
        return result;
    }

    @Override
    public List<Resume> getAllResumeBySearchStatus() {
        Session session = HibernateUtility.getSessionFactory().openSession();
        Query query = session.createQuery
                ("FROM Resume as resume " +
                        "INNER JOIN resume.person_id as person " +
                        "WHERE person.search_status IN ('ищет работу', 'открыт к предложениям')");
        List<Object[]> result = query.list();
        List<Resume> resumes = new ArrayList<Resume>();
        if (result.size() == 0) {
            return resumes;
        }
        for(Object[] row : result) {
            resumes.add((Resume) row[0]);
        }
        session.close();
        return resumes;
    }

    @Override
    public List<Resume> getAllResumeByPerson(String companyName) {
        Session session = HibernateUtility.getSessionFactory().openSession();
        Query query = session.createQuery
                ("FROM Resume as resume " +
                        "INNER JOIN resume.person_id as person " +
                        "WHERE CONCAT(person.first_name, ' ', person.last_name, ' ', person.sur_name) LIKE :comName");
        query.setParameter("comName", likeExpr(companyName));
        List<Object[]> result = query.list();
        List<Resume> resumes = new ArrayList<Resume>();
        if (result.size() == 0) {
            return resumes;
        }
        for(Object[] row : result) {
            resumes.add((Resume) row[0]);
        }
        session.close();
        return resumes;
    }

    @Override
    public List<Resume> getAllResumeByPosition(String positionName) {
        Session session = HibernateUtility.getSessionFactory().openSession();
        Query<Resume> query = session.createQuery
                ("FROM Resume " +
                        "WHERE position LIKE :comName");
        query.setParameter("comName", likeExpr(positionName));
        List<Resume> resumes = query.getResultList();
        session.close();
        return resumes;
    }

    @Override
    public List<Resume> getAllResumeBySalary(Long salaryFrom, Long salaryTo) {
        Session session = HibernateUtility.getSessionFactory().openSession();
        Query<Resume> query = session.createQuery
                ("FROM Resume " +
                        "WHERE desired_salary >= :from AND " +
                        "desired_salary <= :to");
        query.setParameter("from", salaryFrom);
        query.setParameter("to", salaryTo);
        List<Resume> resumes = query.getResultList();
        session.close();
        return resumes;
    }

    @Override
    public List<Resume> getAllResumeByEducation(String educationName) {
        Session session = HibernateUtility.getSessionFactory().openSession();
        Query query = session.createQuery
                ("FROM Resume as Resume " +
                        "INNER JOIN Resume.person_id as person " +
                        "INNER JOIN person.education_id as education " +
                        "WHERE education.educ_name LIKE :comName");
        query.setParameter("comName", likeExpr(educationName));
        List<Object[]> result = query.list();
        List<Resume> resumes = new ArrayList<Resume>();
        if (result.size() == 0) {
            return resumes;
        }
        for(Object[] row : result) {
            resumes.add((Resume) row[0]);
        }
        session.close();
        return resumes;
    }

    @Override
    public List<Resume> getAllResumeByVacancy(Vacancy vacancy) {
        Session session = HibernateUtility.getSessionFactory().openSession();
        Query helpquery = session.createQuery
                ("FROM Vacancy as vacancy " +
                        "INNER JOIN vacancy.required_education as education " +
                        "WHERE vacancy.id = :vacancy");
        helpquery.setParameter("vacancy", vacancy.getId());
        List<Object[]> help = helpquery.list();
        Education helpresult = (Education) (help.get(0))[1];

        Query<Resume> query = session.createQuery
                ("SELECT resume.id, resume.person_id, resume.position, resume.desired_salary, resume.experience " +
                        "FROM Resume as resume " +
                        "INNER JOIN resume.person_id as person " +
                        "INNER JOIN person.education_id as education " +
                        "WHERE resume.position = :pos AND " +
                        "resume.desired_salary <= :salary AND " +
                        "resume.experience >= :exp AND " +
                        "education.id >= :educId");
        query.setParameter("pos", vacancy.getPosition());
        query.setParameter("salary", vacancy.getOffered_salary());
        query.setParameter("exp", vacancy.getExperience());
        query.setParameter("educId", helpresult.getId());
        List<Resume> result = query.getResultList();
        session.close();
        return result;
    }

    private String likeExpr(String param) {
        return "%" + param + "%";
    }
}