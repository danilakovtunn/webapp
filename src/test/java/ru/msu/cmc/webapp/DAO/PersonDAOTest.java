package ru.msu.cmc.webapp.DAO;

import org.hibernate.Session;
import ru.msu.cmc.webapp.DAO.impl.CompanyDAOImpl;
import ru.msu.cmc.webapp.DAO.impl.EducationDAOImpl;
import ru.msu.cmc.webapp.DAO.impl.PersonDAOImpl;
import ru.msu.cmc.webapp.models.Company;
import ru.msu.cmc.webapp.models.Education;
import ru.msu.cmc.webapp.models.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;
import ru.msu.cmc.webapp.utils.HibernateUtility;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PersonDAOTest {

    @Test
    void testById() {
        PersonDAO personDAO = new PersonDAOImpl();
        assertEquals("Киселев", personDAO.getById(4L).getLast_name());
        assertEquals("Степан", personDAO.getById(4L).getFirst_name());
        assertEquals("Демидович", personDAO.getById(4L).getSur_name());
    }

    @Test
    void testGetAll() {
        PersonDAO personDAO = new PersonDAOImpl();
        assertEquals(6, personDAO.getAll().size());
    }

    @Test
    void testAdd() {
        PersonDAO personDAO = new PersonDAOImpl();
        EducationDAO educationDAO = new EducationDAOImpl();
        assertEquals(6, personDAO.getAll().size());
        personDAO.add(new Person("TestLast", "TestFirst", "TestSur", educationDAO.getById(1L), "TestLocation", "не ищет"));
        assertEquals(7, personDAO.getAll().size());
    }

    @Test
    void testUpdate() {
        PersonDAO personDAO = new PersonDAOImpl();
        Person test = personDAO.getById(3L);
        assertEquals("Михайлова", test.getLast_name());
        assertEquals("Ксения", test.getFirst_name());
        assertEquals("Фёдоровна", test.getSur_name());
        test.setFirst_name("TEST");
        personDAO.update(test);
        assertEquals("TEST", personDAO.getById(3L).getFirst_name());
    }

    @Test
    void testDelete() {
        PersonDAO personDAO = new PersonDAOImpl();
        Person test = personDAO.getById(3L);
        assertEquals(6, personDAO.getAll().size());
        personDAO.delete(test);
        assertEquals(5, personDAO.getAll().size());
    }

    @Test
    void testGetAllPersonByName() {
        PersonDAO personDAO = new PersonDAOImpl();
        List<Person> test = personDAO.getAllPersonByName("Илья Орлов");
        assertEquals(1, test.size());
        assertEquals("Илья", test.get(0).getFirst_name());
        assertEquals("Орлов", test.get(0).getLast_name());
        test = personDAO.getAllPersonByName("ев");
        assertEquals(3, test.size());
    }

    private void annihilation() {
        Session session = HibernateUtility.getSessionFactory().openSession();
        session.beginTransaction();
        session.createSQLQuery("TRUNCATE website.public.person RESTART IDENTITY CASCADE;").executeUpdate();
        session.createSQLQuery("ALTER SEQUENCE person_id_seq RESTART WITH 1;").executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @BeforeEach
    void beforeEach() {
        annihilation();
        PersonDAO personDAO = new PersonDAOImpl();
        EducationDAO educationDAO = new EducationDAOImpl();
        personDAO.add(new Person("Артём", "Зайцев", "Михайлович", educationDAO.getById(4L), "534660, Новосибирская область, город Дорохово, наб. Ленина, 81", "не ищет"));
        personDAO.add(new Person("Илья", "Орлов", "Артёмович",  educationDAO.getById(1L), "743923, Амурская область, город Люберцы, пр. Ленина, 12", "не ищет"));
        personDAO.add(new Person("Ксения", "Михайлова", "Фёдоровна",  educationDAO.getById(7L), "777892, Архангельская область, город Талдом, бульвар Косиора, 60", "ищет работу"));
        personDAO.add(new Person("Степан", "Киселев", "Демидович",  educationDAO.getById(6L), "650879, Челябинская область, город Балашиха, въезд Будапештсткая, 20", "ищет работу"));
        personDAO.add(new Person("Матвей", "Медведев", "Максимович",  educationDAO.getById(7L), "993270, Калужская область, город Чехов, бульвар Будапештсткая, 73", "открыт к предложениям"));
        personDAO.add(new Person("Денис", "Галкин", "Михайлович",  educationDAO.getById(1L), "754668, Липецкая область, город Подольск, бульвар Космонавтов, 20", "не ищет"));
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
    }
}