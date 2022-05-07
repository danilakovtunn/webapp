package ru.msu.cmc.webapp.DAO.impl;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.msu.cmc.webapp.DAO.PlacesDAO;
import ru.msu.cmc.webapp.models.Company;
import ru.msu.cmc.webapp.models.Education;
import ru.msu.cmc.webapp.models.Places;
import ru.msu.cmc.webapp.utils.HibernateUtility;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PlacesDAOImpl implements PlacesDAO {
    @Override
    public void add(Places entity) {
        Session session = HibernateUtility.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(entity);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void update(Places entity) {
        Session session = HibernateUtility.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(entity);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void delete(Places entity) {
        Session session = HibernateUtility.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(entity);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<Places> getAll() {
        Session session = HibernateUtility.getSessionFactory().openSession();
        Query<Places> query = session.createQuery
                ("FROM Places", Places.class);
        List<Places> result = query.getResultList();
        session.close();
        return result;
    }

    @Override
    public Places getById(Long id) {
        Session session = HibernateUtility.getSessionFactory().openSession();
        Query<Places> query = session.createQuery
                ("FROM Places AS places " +
                        "WHERE places.id = :id");
        query.setParameter("id", id);
        Places result;
        result = query.getSingleResult();
        session.close();
        return result;
    }

    public List<Places> getAllPlacesByPersonId(Long personId) {
        Session session = HibernateUtility.getSessionFactory().openSession();
        Query<Places> query = session.createQuery("FROM Places as places " +
                "WHERE places.person_id = :id");
        query.setParameter("id", personId);
        List<Places> result = query.getResultList();
        session.close();
        return result;
    }
}