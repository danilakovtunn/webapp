package ru.msu.cmc.webapp.DAO;

import org.hibernate.Session;
import ru.msu.cmc.webapp.DAO.impl.EducationDAOImpl;
import ru.msu.cmc.webapp.DAO.impl.PersonDAOImpl;
import ru.msu.cmc.webapp.DAO.impl.PlacesDAOImpl;
import ru.msu.cmc.webapp.models.Education;
import ru.msu.cmc.webapp.models.Person;
import ru.msu.cmc.webapp.models.Places;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;
import ru.msu.cmc.webapp.utils.HibernateUtility;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlacesDAOTest {

    @Test
    void testById() {
        PlacesDAO placesDAO = new PlacesDAOImpl();
        assertEquals(1L, placesDAO.getById(1L).getPerson_id().getId());
        assertEquals("Яндекс", placesDAO.getById(1L).getCompany());
    }

    @Test
    void testGetAll() {
        PlacesDAO placesDAO = new PlacesDAOImpl();
        assertEquals(7, placesDAO.getAll().size());
    }

    @Test
    void testAdd() {
        PlacesDAO placesDAO = new PlacesDAOImpl();
        PersonDAO personDAO = new PersonDAOImpl();
        assertEquals(7, placesDAO.getAll().size());
        placesDAO.add(new Places(personDAO.getById(1L), "Test", "tester", 1L, java.sql.Date.valueOf("2000-01-01"), java.sql.Date.valueOf("2000-01-02")));
        assertEquals(8, placesDAO.getAll().size());
    }

    @Test
    void testUpdate() {
        PlacesDAO placesDAO = new PlacesDAOImpl();
        Places test = placesDAO.getById(3L);
        assertEquals(2L, test.getPerson_id().getId());
        assertEquals("ВымпелКом (Билайн)", test.getCompany());
        test.setCompany("Test");
        placesDAO.update(test);
        assertEquals("Test", placesDAO.getById(3L).getCompany());
    }

    @Test
    void testDelete() {
        PlacesDAO placesDAO = new PlacesDAOImpl();
        Places test = placesDAO.getById(3L);
        assertEquals(7, placesDAO.getAll().size());
        placesDAO.delete(test);
        assertEquals(6, placesDAO.getAll().size());
    }

    @Test
    void testGetAllPlacesByPersonId() {
        PlacesDAO placesDAO = new PlacesDAOImpl();
        List<Places> test = placesDAO.getAllPlacesByPersonId(3L);
        assertEquals(3, test.size());
    }

    @BeforeEach
    void beforeEach() {
        annihilation();
        PlacesDAO placesDAO = new PlacesDAOImpl();
        PersonDAO personDAO = new PersonDAOImpl();
        placesDAO.add(new Places(personDAO.getById(1L), "Яндекс", "Программист Android (java)", 225000L, java.sql.Date.valueOf("2020-7-1"), java.sql.Date.valueOf("2021-2-25")));
        placesDAO.add(new Places(personDAO.getById(2L), "Luxoft", "Администратор 1С", 169000L, java.sql.Date.valueOf("2019-11-1"), java.sql.Date.valueOf("2020-7-22")));
        placesDAO.add(new Places(personDAO.getById(2L), "ВымпелКом (Билайн)", "Сетевой инженер", 43000L, java.sql.Date.valueOf("2020-7-22"), java.sql.Date.valueOf("2021-2-26")));
        placesDAO.add(new Places(personDAO.getById(3L), "Deutsche Bank", "Программист Swift (ObC)", 241000L, java.sql.Date.valueOf("2018-1-4"), java.sql.Date.valueOf("2019-5-11")));
        placesDAO.add(new Places(personDAO.getById(3L), "ВымпелКом (Билайн)", "Программист JavaScript", 196000L, java.sql.Date.valueOf("2019-5-11"), java.sql.Date.valueOf("2020-6-24")));
        placesDAO.add(new Places(personDAO.getById(3L), "EPAM", "Программист Android (java)", 106000L, java.sql.Date.valueOf("2020-6-24"), java.sql.Date.valueOf("2021-11-23")));
        placesDAO.add(new Places(personDAO.getById(4L), "ВымпелКом (Билайн)", "3D-дизайнер", 156000L, java.sql.Date.valueOf("2019-12-26"), java.sql.Date.valueOf("2020-11-15")));
    }

    private void annihilation() {
        Session session = HibernateUtility.getSessionFactory().openSession();
        session.beginTransaction();
        session.createSQLQuery("TRUNCATE website.public.places_of_work RESTART IDENTITY CASCADE;").executeUpdate();
        session.createSQLQuery("ALTER SEQUENCE places_of_work_id_seq RESTART WITH 1;").executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @BeforeAll
    static void beforeAll() {
        Session session = HibernateUtility.getSessionFactory().openSession();
        session.beginTransaction();
        session.createSQLQuery("TRUNCATE website.public.education RESTART IDENTITY CASCADE;").executeUpdate();
        session.createSQLQuery("ALTER SEQUENCE education_id_seq RESTART WITH 1;").executeUpdate();
        session.getTransaction().commit();
        session.close();
        EducationDAO educationDAO = new EducationDAOImpl();
        educationDAO.add(new Education("без образования"));
        educationDAO.add(new Education("начальное"));
        educationDAO.add(new Education("основное общее (9 кл)"));
        educationDAO.add(new Education("среднее общее (11 кл)"));
        educationDAO.add(new Education("среднее профессиональное (колледж)"));
        educationDAO.add(new Education("Высшее (бакалавриат)"));
        educationDAO.add(new Education("Высшее (специалитет)"));
        educationDAO.add(new Education("Высшее (магистратура)"));

        session = HibernateUtility.getSessionFactory().openSession();
        session.beginTransaction();
        session.createSQLQuery("TRUNCATE website.public.person RESTART IDENTITY CASCADE;").executeUpdate();
        session.createSQLQuery("ALTER SEQUENCE person_id_seq RESTART WITH 1;").executeUpdate();
        session.getTransaction().commit();
        session.close();
        PersonDAO personDAO = new PersonDAOImpl();
        personDAO.add(new Person("Артём", "Зайцев", "Михайлович", educationDAO.getById(4L), "534660, Новосибирская область, город Дорохово, наб. Ленина, 81", "не ищет"));
        personDAO.add(new Person("Илья", "Орлов", "Артёмович",  educationDAO.getById(1L), "743923, Амурская область, город Люберцы, пр. Ленина, 12", "не ищет"));
        personDAO.add(new Person("Ксения", "Михайлова", "Фёдоровна",  educationDAO.getById(7L), "777892, Архангельская область, город Талдом, бульвар Косиора, 60", "ищет работу"));
        personDAO.add(new Person("Степан", "Киселев", "Демидович",  educationDAO.getById(6L), "650879, Челябинская область, город Балашиха, въезд Будапештсткая, 20", "ищет работу"));

    }
}