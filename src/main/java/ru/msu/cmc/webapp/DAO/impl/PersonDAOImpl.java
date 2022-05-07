package ru.msu.cmc.webapp.DAO.impl;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.msu.cmc.webapp.DAO.PersonDAO;
import ru.msu.cmc.webapp.models.Person;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PersonDAOImpl implements PersonDAO{
//
//    public PersonDAOImpl() {
//        super(Person.class);
//    }
//
//    @Override
//    public List<Person> getAllPersonByName(String personName) {
//        try (Session session = sessionFactory.openSession()) {
//            Query<Person> query = session.createQuery("FROM Person " +
//                    "WHERE CONCAT(first_name, ' ', last_name, ' ', sur_name) LIKE :comName");
//            query.setParameter("comName", likeExpr(personName));
//            return query.getResultList().size() == 0 ? new ArrayList<Person>() : query.getResultList();
//        }
//    }
//
//    private String likeExpr(String param) {
//        return "%" + param + "%";
//    }
}