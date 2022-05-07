package ru.msu.cmc.webapp.DAO;

import ru.msu.cmc.webapp.models.Company;
import java.util.List;

public interface CompanyDAO {
    //List<Company> getAllCompanyByName(String companyName);
    void add(Company company);
    void update(Company company);
    void remove(Company company);
}