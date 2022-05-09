package ru.msu.cmc.webapp.DAO;

import org.hibernate.Session;
import ru.msu.cmc.webapp.DAO.impl.CompanyDAOImpl;
import ru.msu.cmc.webapp.models.Company;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;
import ru.msu.cmc.webapp.utils.HibernateUtility;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CompanyDAOTest {

    @Test
    void testById() {
        CompanyDAO companyDAO = new CompanyDAOImpl();
        assertEquals("Яндекс", companyDAO.getById(4L).getName());
        assertNull(companyDAO.getById(54L));
    }

    @Test
    void testGetAll() {
        CompanyDAO companyDAO = new CompanyDAOImpl();
        assertEquals(6, companyDAO.getAll().size());
    }

    @Test
    void testAdd() {
        CompanyDAO companyDAO = new CompanyDAOImpl();
        assertEquals(6, companyDAO.getAll().size());
        companyDAO.add(new Company("test", "testing test"));
        assertEquals(7, companyDAO.getAll().size());
    }

    @Test
    void testUpdate() {
        CompanyDAO companyDAO = new CompanyDAOImpl();
        Company test = companyDAO.getById(3L);
        assertEquals("EPAM", test.getName());
        test.setName("TEST");
        companyDAO.update(test);
        assertEquals("TEST", companyDAO.getById(3L).getName());
    }

    @Test
    void testDelete() {
        CompanyDAO companyDAO = new CompanyDAOImpl();
        Company test = companyDAO.getById(3L);
        assertEquals(6, companyDAO.getAll().size());
        companyDAO.delete(test);
        assertEquals(5, companyDAO.getAll().size());
    }

    @Test
    void testGetAllCompanyByName() {
        CompanyDAO companyDAO = new CompanyDAOImpl();
        List<Company> companies = companyDAO.getAllCompanyByName("Яндекс");
        assertEquals(1, companies.size());
        assertEquals("Яндекс", companies.get(0).getName());
        companies = companyDAO.getAllCompanyByName("f");
        assertEquals(2, companies.size());
    }

    @BeforeEach
    void beforeEach() {
        annihilation();
        CompanyDAO companyDAO = new CompanyDAOImpl();
        companyDAO.add(new Company("ВымпелКом (Билайн)", "Публичное акционерное общество \"Вымпел-коммуникации\""));
        companyDAO.add(new Company("Luxoft", "Luxoft, a DXC Technology Company"));
        companyDAO.add(new Company("EPAM", "EPAM Systems, Inc"));
        companyDAO.add(new Company("Яндекс", "ОБЩЕСТВО С ОГРАНИЧЕННОЙ ОТВЕТСТВЕННОСТЬЮ \"ЯНДЕКС\""));
        companyDAO.add(new Company("Tinkoff.ru", "Акционерное общество \"Тинькофф Банк\""));
        companyDAO.add(new Company("Deutsche Bank", "\"Дойче Банк\" Общество с ограниченной ответственностью"));
    }

    private void annihilation() {
        Session session = HibernateUtility.getSessionFactory().openSession();
        session.beginTransaction();
        session.createSQLQuery("TRUNCATE website.public.company RESTART IDENTITY CASCADE;").executeUpdate();
        session.createSQLQuery("ALTER SEQUENCE company_id_seq RESTART WITH 1;").executeUpdate();
        session.getTransaction().commit();
        session.close();
    }
}