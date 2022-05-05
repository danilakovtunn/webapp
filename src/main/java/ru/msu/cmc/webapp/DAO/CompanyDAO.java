package ru.msu.cmc.webapp.DAO;

import ru.msu.cmc.webapp.models.Company;
import java.util.List;

public interface CompanyDAO extends CommonDAO<Company, Long> {
    List<Company> getAllCompanyByName(String companyName);
}