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
        assertEquals(6, companyDAO.getAll().size());
    }
}