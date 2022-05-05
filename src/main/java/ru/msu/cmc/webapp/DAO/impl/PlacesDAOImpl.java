package ru.msu.cmc.webapp.DAO.impl;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.msu.cmc.webapp.DAO.PlacesDAO;
import ru.msu.cmc.webapp.models.Places;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PlacesDAOImpl extends CommonDAOImpl<Places, Long> implements PlacesDAO {

    public PlacesDAOImpl() {
        super(Places.class);
    }

    public List<Places> getAllPlacesByPersonId(Long personId) {
        try (Session session = sessionFactory.openSession()) {
            Query<Places> query = session.createQuery("FROM Places as places " +
                    "WHERE places.person_id = :id");
            query.setParameter("id", personId);
            return query.getResultList().size() == 0 ? new ArrayList<Places>() : query.getResultList();
        }
    }
}