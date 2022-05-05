package ru.msu.cmc.webapp.DAO.impl;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.msu.cmc.webapp.DAO.CompanyDAO;
import ru.msu.cmc.webapp.models.Company;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CompanyDAOImpl extends CommonDAOImpl<Company, Long> implements CompanyDAO{

    public CompanyDAOImpl() {
        super(Company.class);
    }

    @Override
    public List<Company> getAllCompanyByName(String personName) {
        try (Session session = sessionFactory.openSession()) {
            Query<Company> query = session.createQuery("FROM Company WHERE name LIKE :comName");
            query.setParameter("comName", likeExpr(personName));
            return query.getResultList().size() == 0 ? new ArrayList<Company>() : query.getResultList();
        }
    }

    private String likeExpr(String param) {
        return "%" + param + "%";
    }
}