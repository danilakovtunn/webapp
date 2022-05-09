package ru.msu.cmc.webapp.DAO;

import org.hibernate.Session;
import ru.msu.cmc.webapp.DAO.impl.EducationDAOImpl;
import ru.msu.cmc.webapp.models.Education;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;
import ru.msu.cmc.webapp.utils.HibernateUtility;

import static org.junit.jupiter.api.Assertions.*;

class EducationDAOTest {

    @Test
    void testById() {
        EducationDAO educationDAO = new EducationDAOImpl();
        assertEquals("среднее общее (11 кл)", educationDAO.getById(4L).getEduc_name());
    }

    @Test
    void testGetAll() {
        EducationDAO educationDAO = new EducationDAOImpl();
        assertEquals(8, educationDAO.getAll().size());
    }

    @Test
    void testAdd() {
        EducationDAO educationDAO = new EducationDAOImpl();
        assertEquals(8, educationDAO.getAll().size());
        educationDAO.add(new Education("без образования"));
        assertEquals(9, educationDAO.getAll().size());
    }

    @Test
    void testUpdate() {
        EducationDAO educationDAO = new EducationDAOImpl();
        Education test = educationDAO.getById(3L);
        assertEquals("основное общее (9 кл)", test.getEduc_name());
        test.setEduc_name("без образования");
        educationDAO.update(test);
        assertEquals("без образования", test.getEduc_name());
    }

    @Test
    void testDelete() {
        EducationDAO educationDAO = new EducationDAOImpl();
        Education test = educationDAO.getById(3L);
        assertEquals(8, educationDAO.getAll().size());
        educationDAO.delete(test);
        assertEquals(7, educationDAO.getAll().size());
    }

    @BeforeEach
    void beforeEach() {
        annihilation();
        EducationDAO educationDAO = new EducationDAOImpl();
        educationDAO.add(new Education("без образования"));
        educationDAO.add(new Education("начальное"));
        educationDAO.add(new Education("основное общее (9 кл)"));
        educationDAO.add(new Education("среднее общее (11 кл)"));
        educationDAO.add(new Education("среднее профессиональное (колледж)"));
        educationDAO.add(new Education("Высшее (бакалавриат)"));
        educationDAO.add(new Education("Высшее (специалитет)"));
        educationDAO.add(new Education("Высшее (магистратура)"));
    }

    private void annihilation() {
        Session session = HibernateUtility.getSessionFactory().openSession();
        session.beginTransaction();
        session.createSQLQuery("TRUNCATE website.public.education RESTART IDENTITY CASCADE;").executeUpdate();
        session.createSQLQuery("ALTER SEQUENCE education_id_seq RESTART WITH 1;").executeUpdate();
        session.getTransaction().commit();
        session.close();
    }
}