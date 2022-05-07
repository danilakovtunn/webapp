package ru.msu.cmc.webapp.DAO.impl;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.msu.cmc.webapp.DAO.PersonDAO;
import ru.msu.cmc.webapp.models.Company;
import ru.msu.cmc.webapp.models.Education;
import ru.msu.cmc.webapp.models.Person;
import ru.msu.cmc.webapp.utils.HibernateUtility;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PersonDAOImpl implements PersonDAO{
    @Override
    public void add(Person entity) {
        Session session = HibernateUtility.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(entity);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void update(Person entity) {
        Session session = HibernateUtility.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(entity);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void delete(Person entity) {
        Session session = HibernateUtility.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(entity);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<Person> getAll() {
        Session session = HibernateUtility.getSessionFactory().openSession();
        Query<Person> query = session.createQuery
                ("FROM Person", Person.class);
        List<Person> result = query.getResultList();
        session.close();
        return result;
    }

    @Override
    public Person getById(Long id) {
        Session session = HibernateUtility.getSessionFactory().openSession();
        Query<Person> query = session.createQuery
                ("FROM Person AS person " +
                        "WHERE person.id = :id");
        query.setParameter("id", id);
        Person result;
        result = query.getSingleResult();
        session.close();
        return result;
    }
    @Override
    public List<Person> getAllPersonByName(String personName) {
        Session session = HibernateUtility.getSessionFactory().openSession();
        Query<Person> query = session.createQuery("FROM Person " +
                "WHERE CONCAT(first_name, ' ', last_name, ' ', sur_name) LIKE :comName");
        query.setParameter("comName", likeExpr(personName));
        List<Person> result;
        result = query.getResultList();
        session.close();
        return result;
    }

    private String likeExpr(String param) {
        return "%" + param + "%";
    }
}