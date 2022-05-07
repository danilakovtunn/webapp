package ru.msu.cmc.webapp.DAO.impl;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.msu.cmc.webapp.DAO.CompanyDAO;
import ru.msu.cmc.webapp.models.Company;
import ru.msu.cmc.webapp.utils.HibernateUtility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Repository
public class CompanyDAOImpl implements CompanyDAO{

    @Override
    public void add(Company company) {
        Session session = HibernateUtility.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(company);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void update(Company company) {
        Session session = HibernateUtility.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(company);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void remove(Company company) {
        Session session = HibernateUtility.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(company);
        session.getTransaction().commit();
        session.close();
    }

//    @Override
//    public List<Company> getAllCompanyByName(String personName) {
//
//        try (Session session = sessionFactory.openSession()) {
//            Query<Company> query = session.createQuery("FROM Company WHERE name LIKE :comName");
//            query.setParameter("comName", likeExpr(personName));
//            return query.getResultList().size() == 0 ? new ArrayList<Company>() : query.getResultList();
//        }
//    }
//
//    @Override
//    public void saveCollection(Collection<Company> entities) {
//
//        try (Session session = HibernateUtility.getSessionFactory().openSession()) {
//            session.beginTransaction();
//            for (Company entity : entities) {
//                this.save(entity);
//            }
//            session.getTransaction().commit();
//        }
//    }
    private String likeExpr(String param) {
        return "%" + param + "%";
    }
}