package ru.msu.cmc.webapp.DAO;

import ru.msu.cmc.webapp.models.Company;
import java.util.List;
import java.util.Collection;

public interface CompanyDAO {
    Company getById(Long id);
    Collection<Company> getAll();
    void add(Company entity);
    void delete(Company entity);
    void update(Company entity);
    List<Company> getAllCompanyByName(String companyName);
}