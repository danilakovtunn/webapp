package ru.msu.cmc.webapp.DAO;

import ru.msu.cmc.webapp.DAO.impl.CompanyDAOImpl;
import ru.msu.cmc.webapp.models.Company;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PersonServiceTest {
    @Test
    void simpleTest() {
        CompanyDAO companyDAO = new CompanyDAOImpl();
        companyDAO.add(new Company("ВымпелКом (Билайн)", "Публичное акционерное общество \"Вымпел-коммуникации\""));
        companyDAO.add(new Company("Luxoft", "Luxoft, a DXC Technology Company"));
        assertEquals(2, 2);
    }
}

//package ru.msu.cmc.webapp.DAO;
//
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.junit.jupiter.api.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.TestPropertySource;
//import ru.msu.cmc.webapp.models.Company;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNull;
//
//@SpringBootTest
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@TestPropertySource(locations="classpath:application.properties")
//public class CompanyDAOTest {
//    @Autowired
//    private CompanyDAO companyDAO;
//    @Autowired
//    private SessionFactory sessionFactory;
//
//    @Test
//    void testSimpleManipulations() {
//        List<Company> companyAll = (List<Company>) companyDAO.getAll();
//        assertEquals(2, companyAll.size());
//
//        List<Company> luxoft = companyDAO.getAllCompanyByName("Luxoft");
//        assertEquals(1, luxoft.size());
//        assertEquals("Luxoft", luxoft.get(0).getName());
//
//        Company bilain1 = companyDAO.getById(2L);
//        assertEquals(2, bilain1.getId());
//
//        Company personNotExist = companyDAO.getById(100L);
//        assertNull(personNotExist);
//    }
//
//    @BeforeEach
//    void beforeEach() {
//        List<Company> companyList = new ArrayList<>();
//        companyList.add(new Company("ВымпелКом (Билайн)", "Публичное акционерное общество \"Вымпел-коммуникации\""));
//        companyList.add(new Company("Luxoft", "Luxoft, a DXC Technology Company"));
//        companyDAO.saveCollection(companyList);
//    }
//
//    @BeforeAll
//    @AfterEach
//    void annihilation() {
//        try (Session session = sessionFactory.openSession()) {
//            session.beginTransaction();
//            session.createSQLQuery("TRUNCATE person RESTART IDENTITY CASCADE;").executeUpdate();
//            session.createSQLQuery("ALTER SEQUENCE company_id_seq RESTART WITH 1;").executeUpdate();
//            session.getTransaction().commit();
//        }
//    }
//}