package ru.msu.cmc.webapp.DAO.impl;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.msu.cmc.webapp.DAO.EducationDAO;
import ru.msu.cmc.webapp.models.Company;
import ru.msu.cmc.webapp.models.Education;
import ru.msu.cmc.webapp.utils.HibernateUtility;

import java.util.List;

@Repository
public class EducationDAOImpl implements EducationDAO {
    @Override
    public void add(Education entity) {
        Session session = HibernateUtility.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(entity);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void update(Education entity) {
        Session session = HibernateUtility.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(entity);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void delete(Education entity) {
        Session session = HibernateUtility.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(entity);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<Education> getAll() {
        Session session = HibernateUtility.getSessionFactory().openSession();
        Query<Education> query = session.createQuery
                ("FROM Education", Education.class);
        List<Education> result = query.getResultList();
        session.close();
        return result;
    }

    @Override
    public Education getById(Long id) {
        Session session = HibernateUtility.getSessionFactory().openSession();
        Query<Education> query = session.createQuery
                ("FROM Education AS education " +
                        "WHERE education.id = :id");
        query.setParameter("id", id);
        Education result;
        result = query.getSingleResult();
        session.close();
        return result;
    }

}