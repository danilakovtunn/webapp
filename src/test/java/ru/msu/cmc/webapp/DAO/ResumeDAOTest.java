package ru.msu.cmc.webapp.DAO;

import org.hibernate.Session;
import ru.msu.cmc.webapp.DAO.impl.*;
import ru.msu.cmc.webapp.models.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;
import ru.msu.cmc.webapp.utils.HibernateUtility;

import java.nio.charset.CoderMalfunctionError;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ResumeDAOTest {

    @Test
    void testById() {
        ResumeDAO resumeDAO = new ResumeDAOImpl();
        assertEquals(3L, resumeDAO.getById(4L).getPerson_id().getId());
        assertEquals("Программист 1С", resumeDAO.getById(4L).getPosition());
    }

    @Test
    void testGetAll() {
        ResumeDAO resumeDAO = new ResumeDAOImpl();
        assertEquals(7, resumeDAO.getAll().size());
    }

    @Test
    void testAdd() {
        ResumeDAO resumeDAO = new ResumeDAOImpl();
        PersonDAO personDAO = new PersonDAOImpl();
        assertEquals(7, resumeDAO.getAll().size());
        resumeDAO.add(new Resume(personDAO.getById(1L), "Tester", 1L, 1f));
        assertEquals(8, resumeDAO.getAll().size());
    }

    @Test
    void testUpdate() {
        ResumeDAO resumeDAO = new ResumeDAOImpl();
        Resume test = resumeDAO.getById(3L);
        assertEquals(3L, test.getPerson_id().getId());
        assertEquals("DevOps", test.getPosition());
        test.setPosition("TEST");
        resumeDAO.update(test);
        assertEquals("TEST", resumeDAO.getById(3L).getPosition());
    }

    @Test
    void testDelete() {
        ResumeDAO resumeDAO = new ResumeDAOImpl();
        Resume test = resumeDAO.getById(3L);
        assertEquals(7, resumeDAO.getAll().size());
        resumeDAO.delete(test);
        assertEquals(6, resumeDAO.getAll().size());
    }

    @Test
    void testGetAllResumeBySearchStatus() {
        ResumeDAO resumeDAO = new ResumeDAOImpl();
        List<Resume> test = resumeDAO.getAllResumeBySearchStatus();
        assertEquals(5, test.size());
        resumeDAO.delete(resumeDAO.getById(3L));
        resumeDAO.delete(resumeDAO.getById(4L));
        resumeDAO.delete(resumeDAO.getById(5L));
        resumeDAO.delete(resumeDAO.getById(6L));
        resumeDAO.delete(resumeDAO.getById(7L));
       test = resumeDAO.getAllResumeBySearchStatus();
        assertEquals(0, test.size());
    }

    @Test
    void testGetAllResumeByExperience() {
        ResumeDAO resumeDAO = new ResumeDAOImpl();
        assertEquals(5, resumeDAO.getAllResumeByExperience(5f).size());
    }
    @Test
    void testGetAllResumeByPerson() {
        ResumeDAO resumeDAO = new ResumeDAOImpl();
        List<Resume> test = resumeDAO.getAllResumeByPerson("Киселев");
        assertEquals(2, test.size());
        test = resumeDAO.getAllResumeByPerson("TestTestTest");
        assertEquals(0, test.size());
    }

    @Test
    void testGetAllResumeByPosition() {
        ResumeDAO resumeDAO = new ResumeDAOImpl();
        List<Resume> test = resumeDAO.getAllResumeByPosition("Программист");
        assertEquals(3, test.size());
    }

    @Test
    void testGetAllResumeBySalary() {
        ResumeDAO resumeDAO = new ResumeDAOImpl();
        List<Resume> test = resumeDAO.getAllResumeBySalary(50000L, 100000L);
        assertEquals(2, test.size());
    }

    @Test
    void testGetAllResumeByEducation() {
        ResumeDAO resumeDAO = new ResumeDAOImpl();
        List<Resume> test = resumeDAO.getAllResumeByEducation("Высшее");
        assertEquals(5, test.size());
        test = resumeDAO.getAllResumeByEducation("ВысочайшееТест");
        assertEquals(0, test.size());
    }

    @Test
    void testGetAllResumeByVacancy() {
        ResumeDAO resumeDAO = new ResumeDAOImpl();
        CompanyDAO companyDAO = new CompanyDAOImpl();
        VacancyDAO vacancyDAO = new VacancyDAOImpl();
        EducationDAO educationDAO = new EducationDAOImpl();
        Vacancy vacancy1 = new Vacancy(companyDAO.getById(1L), "DevOps", 150000L, 1f, educationDAO.getById(6L));
        Vacancy vacancy2 = new Vacancy(companyDAO.getById(1L), "DevOps", 200000L, 5f, educationDAO.getById(6L));
        Vacancy vacancy3 = new Vacancy(companyDAO.getById(1L), "DevOps", 200000L, 1f, educationDAO.getById(6L));
        Vacancy vacancy4 = new Vacancy(companyDAO.getById(1L), "DevOps", 200000L, 1f, educationDAO.getById(7L));
        vacancyDAO.add(vacancy1);
        vacancyDAO.add(vacancy2);
        vacancyDAO.add(vacancy3);
        vacancyDAO.add(vacancy4);
        assertEquals(1, resumeDAO.getAllResumeByVacancy(vacancy1).size());
        assertEquals(1, resumeDAO.getAllResumeByVacancy(vacancy2).size());
        assertEquals(2, resumeDAO.getAllResumeByVacancy(vacancy3).size());
        assertEquals(1, resumeDAO.getAllResumeByVacancy(vacancy4).size());
    }




    @BeforeEach
    void beforeEach() {
        annihilation();
        ResumeDAO resumeDAO = new ResumeDAOImpl();
        PersonDAO personDAO = new PersonDAOImpl();
        resumeDAO.add(new Resume(personDAO.getById(1L), "Специалист по ИБ", 126000L, 9.9f));
        resumeDAO.add(new Resume(personDAO.getById(2L), "Специалист по ИБ", 204000L, 8.7f));
        resumeDAO.add(new Resume(personDAO.getById(3L), "DevOps", 196000L, 7.7f));
        resumeDAO.add(new Resume(personDAO.getById(3L), "Программист 1С", 79000L, 7.7f));
        resumeDAO.add(new Resume(personDAO.getById(3L), "Программист Android (java)", 80000L, 7.7f));
        resumeDAO.add(new Resume(personDAO.getById(4L), "DevOps", 149000L, 2.6f));
        resumeDAO.add(new Resume(personDAO.getById(4L), "Программист Python", 292000L, 2.6f));

    }

    private void annihilation() {
        Session session = HibernateUtility.getSessionFactory().openSession();
        session.beginTransaction();
        session.createSQLQuery("TRUNCATE website.public.resume RESTART IDENTITY CASCADE;").executeUpdate();
        session.createSQLQuery("ALTER SEQUENCE resume_id_seq RESTART WITH 1;").executeUpdate();
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
        personDAO.add(new Person("Илья", "Орлов", "Артёмович", educationDAO.getById(1L), "743923, Амурская область, город Люберцы, пр. Ленина, 12", "не ищет"));
        personDAO.add(new Person("Ксения", "Михайлова", "Фёдоровна", educationDAO.getById(7L), "777892, Архангельская область, город Талдом, бульвар Косиора, 60", "ищет работу"));
        personDAO.add(new Person("Степан", "Киселев", "Демидович", educationDAO.getById(6L), "650879, Челябинская область, город Балашиха, въезд Будапештсткая, 20", "ищет работу"));

        session = HibernateUtility.getSessionFactory().openSession();
        session.beginTransaction();
        session.createSQLQuery("TRUNCATE website.public.company RESTART IDENTITY CASCADE;").executeUpdate();
        session.createSQLQuery("ALTER SEQUENCE company_id_seq RESTART WITH 1;").executeUpdate();
        session.getTransaction().commit();
        session.close();
        CompanyDAO companyDAO = new CompanyDAOImpl();
        companyDAO.add(new Company("ВымпелКом (Билайн)", "Публичное акционерное общество \"Вымпел-коммуникации\""));
        companyDAO.add(new Company("Luxoft", "Luxoft, a DXC Technology Company"));
        companyDAO.add(new Company("EPAM", "EPAM Systems, Inc"));
        companyDAO.add(new Company("Яндекс", "ОБЩЕСТВО С ОГРАНИЧЕННОЙ ОТВЕТСТВЕННОСТЬЮ \"ЯНДЕКС\""));
        companyDAO.add(new Company("Tinkoff.ru", "Акционерное общество \"Тинькофф Банк\""));
        companyDAO.add(new Company("Deutsche Bank", "\"Дойче Банк\" Общество с ограниченной ответственностью"));
    }
}