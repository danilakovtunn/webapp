package ru.msu.cmc.webapp.DAO.impl;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.msu.cmc.webapp.DAO.ResumeDAO;
import ru.msu.cmc.webapp.models.Vacancy;
import ru.msu.cmc.webapp.models.Resume;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ResumeDAOImpl extends CommonDAOImpl<Resume, Long> implements ResumeDAO {

    public ResumeDAOImpl() {
        super(Resume.class);
    }

    @Override
    public List<Resume> getAllResumeBySearchStatus() {
        try (Session session = sessionFactory.openSession()) {
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
            return resumes;
        }
    }

    @Override
    public List<Resume> getAllResumeByPerson(String companyName) {
        try (Session session = sessionFactory.openSession()) {
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
            return resumes;
        }
    }

    @Override
    public List<Resume> getAllResumeByPosition(String positionName) {
        try (Session session = sessionFactory.openSession()) {
            Query<Resume> query = session.createQuery
                    ("FROM Resume " +
                            "WHERE position LIKE :comName");
            query.setParameter("comName", likeExpr(positionName));
            return query.getResultList().size() == 0 ? new ArrayList<Resume>() : query.getResultList();
        }
    }

    @Override
    public List<Resume> getAllResumeBySalary(Long salaryFrom, Long salaryTo) {
        try (Session session = sessionFactory.openSession()) {
            Query<Resume> query = session.createQuery
                    ("FROM Resume " +
                            "WHERE desired_salary >= :from AND " +
                            "desired_salary <= :to");
            query.setParameter("from", salaryFrom);
            query.setParameter("to", salaryTo);
            return query.getResultList();
        }
    }

    @Override
    public List<Resume> getAllResumeByEducation(String educationName) {
        try (Session session = sessionFactory.openSession()) {
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
            return resumes;
        }
    }

    @Override
    public List<Resume> getAllResumeByVacancy(Vacancy vacancy) {
        try (Session session = sessionFactory.openSession()) {
            Query query = session.createQuery
                    ("FROM Resume as resume " +
                            "INNER JOIN resume.person_id as person " +
                            "INNER JOIN person.education_id as education " +
                            "WHERE resume.position = :pos AND " +
                            "resume.desired_salary <= :salary AND " +
                            "resume.experience >= :exp AND " +
                            "education.id >= (SELECT education.id " +
                                    "FROM Vacancy as vacancy " +
                                    "INNER JOIN vacancy.required_education as education " +
                                    "WHERE vacancy = :vacancy)");
            query.setParameter("pos", vacancy.getPosition());
            query.setParameter("salary", vacancy.getOffered_salary());
            query.setParameter("exp", vacancy.getExperience());
            query.setParameter("vacancy", vacancy);
            List<Object[]> result = query.list();
            List<Resume> resumes = new ArrayList<Resume>();
            if (result.size() == 0) {
                return resumes;
            }
            for(Object[] row : result) {
                resumes.add((Resume) row[0]);
            }
            return resumes;
        }
    }

    private String likeExpr(String param) {
        return "%" + param + "%";
    }
}