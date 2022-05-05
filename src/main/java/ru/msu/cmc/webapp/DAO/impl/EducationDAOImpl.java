package ru.msu.cmc.webapp.DAO.impl;

import org.springframework.stereotype.Repository;
import ru.msu.cmc.webapp.DAO.EducationDAO;
import ru.msu.cmc.webapp.models.Education;

@Repository
public class EducationDAOImpl extends CommonDAOImpl<Education, Long> implements EducationDAO {

    public EducationDAOImpl() {
        super(Education.class);
    }
}