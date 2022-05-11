package ru.msu.cmc.webapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.msu.cmc.webapp.DAO.*;
import ru.msu.cmc.webapp.DAO.impl.*;
import ru.msu.cmc.webapp.models.Company;

import java.util.List;

@Controller
public class CompanyController {
    @Autowired
    private final CompanyDAO companyDAO = new CompanyDAOImpl();

    @Autowired
    private final VacancyDAO vacancyDAO = new VacancyDAOImpl();

    @GetMapping("/companies")
    public String companyList(
            @RequestParam(name = "nameFilter", required = false) String name,
            Model model) {
        List<Company> company;
        if (name != null) {
            company = companyDAO.getAllCompanyByName(name);
        }
        else {
            company = (List<Company>) companyDAO.getAll();
        }
        model.addAttribute("companies", company);
        model.addAttribute("companyService", companyDAO);
        return "companies";
    }

    @GetMapping("/viewCompany")
    public String viewCompany(
            @RequestParam(name = "id") Long id,
            Model model) {
        model.addAttribute("company", companyDAO.getById(id));
        return "viewCompany";
    }

    @GetMapping("/addCompany")
    public String addCompany(Model model) {
        return "addCompany";
    }

    @PostMapping("/saveCompany")
    public String saveCompany(
            @RequestParam(name = "name") String name,
            @RequestParam(name = "fullName") String fullName,
            Model model
    ) {
        Company company = new Company(name, fullName);

        companyDAO.add(company);
        return viewCompany(company.getId(), model);
    }

    @PostMapping("/deleteCompany")
    public String deleteCompany(
            @RequestParam(name = "id") Long id,
            Model model
    ) {
        companyDAO.delete(companyDAO.getById(id));
        return companyList(null, model);
    }

    @GetMapping("/updateCompany")
    public String updateCompany(
            @RequestParam(name = "id") Long id,
            Model model
    ) {
        model.addAttribute("company", companyDAO.getById(id));
        return "updateCompany";
    }

    @PostMapping("/saveUpdateCompany")
    public String saveUpdatePerson(
            @RequestParam(name = "id") Long id,
            @RequestParam(name = "name") String name,
            @RequestParam(name = "fullName") String fullName,
            Model model
    ) {
        Company company = companyDAO.getById(id);
        company.setName(name);
        company.setFull_name(fullName);
        companyDAO.update(company);
        return viewCompany(company.getId(), model);
    }

}