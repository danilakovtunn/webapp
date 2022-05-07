package ru.msu.cmc.webapp.DAO.impl;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.msu.cmc.webapp.DAO.CompanyDAO;
import ru.msu.cmc.webapp.models.Company;
import ru.msu.cmc.webapp.utils.HibernateUtility;

import javax.persistence.NoResultException;
import java.util.List;

@Repository
public class CompanyDAOImpl implements CompanyDAO{

    @Override
    public void add(Company entity) {
        Session session = HibernateUtility.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(entity);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void update(Company entity) {
        Session session = HibernateUtility.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(entity);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void delete(Company entity) {
        Session session = HibernateUtility.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(entity);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<Company> getAll() {
        Session session = HibernateUtility.getSessionFactory().openSession();
        Query<Company> query = session.createQuery
                ("FROM Company", Company.class);
        List<Company> result = query.getResultList();
        session.close();
        return result;
    }

    @Override
    public Company getById(Long id) {
        Session session = HibernateUtility.getSessionFactory().openSession();
        Query<Company> query = session.createQuery
                ("FROM Company AS company " +
                        "WHERE company.id = :id");
        query.setParameter("id", id);
        Company result;
        try {
            result = query.getSingleResult();
        } catch(NoResultException e) {
            result = null;
        }
        session.close();
        return result;
    }
    @Override
    public List<Company> getAllCompanyByName(String personName) {
        Session session = HibernateUtility.getSessionFactory().openSession();
        Query<Company> query = session.createQuery
                ("FROM Company " +
                "WHERE name LIKE :comName");
        query.setParameter("comName", likeExpr(personName));
        List<Company> result = query.getResultList();
        session.close();
        return result;
    }


    private String likeExpr(String param) {
        return "%" + param + "%";
    }
}