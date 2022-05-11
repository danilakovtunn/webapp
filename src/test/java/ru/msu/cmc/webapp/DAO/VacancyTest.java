package ru.msu.cmc.webapp.DAO;

import org.hibernate.Session;
import ru.msu.cmc.webapp.DAO.impl.*;
import ru.msu.cmc.webapp.models.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;
import ru.msu.cmc.webapp.utils.HibernateUtility;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VacancyTest {

    @Test
    void testById() {
        VacancyDAO vacancyDAO = new VacancyDAOImpl();
        assertEquals(1L, vacancyDAO.getById(1L).getCompany_id().getId());
        assertEquals("Программист Java", vacancyDAO.getById(1L).getPosition());
    }

    @Test
    void testGetAll() {
        VacancyDAO vacancyDAO = new VacancyDAOImpl();
        assertEquals(7, vacancyDAO.getAll().size());
    }

    @Test
    void testAdd() {
        VacancyDAO vacancyDAO = new VacancyDAOImpl();
        CompanyDAO companyDAO = new CompanyDAOImpl();
        EducationDAO educationDAO = new EducationDAOImpl();
        assertEquals(7, vacancyDAO.getAll().size());
        vacancyDAO.add(new Vacancy(companyDAO.getById(1L), "Tester", 1L, 1f, educationDAO.getById(1L)));
        assertEquals(8, vacancyDAO.getAll().size());
    }

    @Test
    void testUpdate() {
        VacancyDAO vacancyDAO = new VacancyDAOImpl();
        Vacancy test = vacancyDAO.getById(3L);
        assertEquals(1L, vacancyDAO.getById(3L).getCompany_id().getId());
        assertEquals("Программист С++ / C", vacancyDAO.getById(3L).getPosition());
        test.setPosition("Tester");
        vacancyDAO.update(test);
        assertEquals("Tester", vacancyDAO.getById(3L).getPosition());
    }

    @Test
    void testDelete() {
        VacancyDAO vacancyDAO = new VacancyDAOImpl();
        Vacancy test = vacancyDAO.getById(3L);
        assertEquals(7, vacancyDAO.getAll().size());
        vacancyDAO.delete(test);
        assertEquals(6, vacancyDAO.getAll().size());
    }

    @Test
    void testGetAllVacancyByCompany() {
        VacancyDAO vacancyDAO = new VacancyDAOImpl();
        List<Vacancy> test = vacancyDAO.getAllVacancyByCompany("ВымпелКом");
        assertEquals(4, test.size());
        test = vacancyDAO.getAllVacancyByCompany("12345678901234567890");
        assertEquals(0, test.size());
    }

    @Test
    void testGetAllVacancyByExperience() {
        VacancyDAO vacancyDAO = new VacancyDAOImpl();
        assertEquals(3, vacancyDAO.getAllVacancyByExperience(5f).size());
    }

    @Test
    void testGetAllVacancyByPosition() {
        VacancyDAO vacancyDAO = new VacancyDAOImpl();
        List<Vacancy> test = vacancyDAO.getAllVacancyByPosition("Программист");
        assertEquals(5, test.size());
    }

    @Test
    void testGetAllVacancyBySalary() {
        VacancyDAO vacancyDAO = new VacancyDAOImpl();
        List<Vacancy> test = vacancyDAO.getAllVacancyBySalary(181000L, 183000L);
        assertEquals(2, test.size());
        test = vacancyDAO.getAllVacancyBySalary(500000L, 700000L);
        assertEquals(0, test.size());
    }

    @Test
    void testGetAllVacancyByEducation() {
        VacancyDAO vacancyDAO = new VacancyDAOImpl();
        List<Vacancy> test = vacancyDAO.getAllVacancyByEducation("Высшее");
        assertEquals(5, test.size());
        test = vacancyDAO.getAllVacancyByEducation("высочайшее тест");
        assertEquals(0, test.size());
    }

    @Test
    void testGetAllVacancyByResume() {
        VacancyDAO vacancyDAO = new VacancyDAOImpl();
        PersonDAO personDAO = new PersonDAOImpl();
        ResumeDAO resumeDAO = new ResumeDAOImpl();
        Resume resume1 = new Resume(personDAO.getById(1L), "Программист Java", 200000L, 8f);
        Resume resume2 = new Resume(personDAO.getById(1L), "Программист Java", 100000L, 8f);
        resumeDAO.add(resume1);
        resumeDAO.add(resume2);
        assertEquals(1, vacancyDAO.getAllVacancyByResume(resume1).size());
        assertEquals(2, vacancyDAO.getAllVacancyByResume(resume2).size());
    }

    @BeforeEach
    void beforeEach() {
        annihilation();
        VacancyDAO vacancyDAO = new VacancyDAOImpl();
        CompanyDAO companyDAO = new CompanyDAOImpl();
        EducationDAO educationDAO = new EducationDAOImpl();
        vacancyDAO.add(new Vacancy(companyDAO.getById(1L), "Программист Java", 142000L, 4.9f, educationDAO.getById(8L)));
        vacancyDAO.add(new Vacancy(companyDAO.getById(1L), "Программист Ruby", 268000L, 8.7f, educationDAO.getById(5L)));
        vacancyDAO.add(new Vacancy(companyDAO.getById(1L), "Программист С++ / C", 182000L, 4.2f, educationDAO.getById(8L)));
        vacancyDAO.add(new Vacancy(companyDAO.getById(2L), "Руководитель отдела IT (поддержка)", 133000L, 5.3f, educationDAO.getById(7L)));
        vacancyDAO.add(new Vacancy(companyDAO.getById(2L), "Системный администратор", 201000L, 1.8f, educationDAO.getById(8L)));
        vacancyDAO.add(new Vacancy(companyDAO.getById(2L), "Программист JavaScript", 182000L, 4.7f, educationDAO.getById(5L)));
        vacancyDAO.add(new Vacancy(companyDAO.getById(1L), "Программист Java", 300000L, 7f, educationDAO.getById(8L)));
    }

    private void annihilation() {
        Session session = HibernateUtility.getSessionFactory().openSession();
        session.beginTransaction();
        session.createSQLQuery("TRUNCATE website.public.vacancies RESTART IDENTITY CASCADE;").executeUpdate();
        session.createSQLQuery("ALTER SEQUENCE vacancies_id_seq RESTART WITH 1;").executeUpdate();
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
        personDAO.add(new Person("Артём", "Зайцев", "Михайлович", educationDAO.getById(8L), "534660, Новосибирская область, город Дорохово, наб. Ленина, 81", "не ищет"));
        personDAO.add(new Person("Илья", "Орлов", "Артёмович",  educationDAO.getById(1L), "743923, Амурская область, город Люберцы, пр. Ленина, 12", "не ищет"));
        personDAO.add(new Person("Ксения", "Михайлова", "Фёдоровна",  educationDAO.getById(7L), "777892, Архангельская область, город Талдом, бульвар Косиора, 60", "ищет работу"));
        personDAO.add(new Person("Степан", "Киселев", "Демидович",  educationDAO.getById(6L), "650879, Челябинская область, город Балашиха, въезд Будапештсткая, 20", "ищет работу"));

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