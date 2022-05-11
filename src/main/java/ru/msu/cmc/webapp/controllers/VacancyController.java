package ru.msu.cmc.webapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.msu.cmc.webapp.DAO.*;
import ru.msu.cmc.webapp.DAO.impl.*;
import ru.msu.cmc.webapp.models.*;

import java.sql.Date;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

@Controller
public class VacancyController {
    @Autowired
    private final CompanyDAO companyDAO = new CompanyDAOImpl();
    @Autowired
    private final VacancyDAO vacancyDAO = new VacancyDAOImpl();
    @Autowired
    private final EducationDAO educationDAO = new EducationDAOImpl();
    @Autowired
    private final ResumeDAO resumeDAO = new ResumeDAOImpl();

    @GetMapping("/vacancies")
    public String vacancyList(
            @RequestParam(name = "nameFilter", required = false) String name,
            @RequestParam(name = "positionFilter", required = false) String position,
            @RequestParam(name = "educationFilter", required = false) String educationFilter,
            @RequestParam(name = "experienceFilter", required = false) Float experienceFilter,
            @RequestParam(name = "fromFilter", required = false) Long fromFilter,
            @RequestParam(name = "toFilter", required = false) Long toFilter,
            @RequestParam(name = "resumeId", required = false) Long resumeId,
            @RequestParam(name = "companyId", required = false) Long companyId,
            Model model) {
        List<Vacancy> vacancies;
        if (name != null && !name.equals("")) {
            vacancies = vacancyDAO.getAllVacancyByCompany(name);
        } else if (position != null && !position.equals("")){
            vacancies = vacancyDAO.getAllVacancyByPosition(position);
        } else if (educationFilter != null && !educationFilter.equals("")){
            vacancies = vacancyDAO.getAllVacancyByEducation(educationFilter);
        } else if (experienceFilter != null){
            vacancies = vacancyDAO.getAllVacancyByExperience(experienceFilter);
        } else if (fromFilter != null && toFilter != null) {
            vacancies = vacancyDAO.getAllVacancyBySalary(fromFilter, toFilter);
        } else if (resumeId != null){
            vacancies = vacancyDAO.getAllVacancyByResume(resumeDAO.getById(resumeId));
        } else if (companyId != null){
            vacancies = vacancyDAO.getAllVacancyByCompany(companyDAO.getById(companyId).getName());
        } else {
            vacancies = (List<Vacancy>) vacancyDAO.getAll();
        }
        model.addAttribute("vacancies", vacancies);
        model.addAttribute("education", educationDAO.getAll());
        model.addAttribute("vacancyService", vacancyDAO);
        return "vacancies";
    }

    @GetMapping("/addVacancy")
    public String addVacancy(Model model) {
        model.addAttribute("companies", companyDAO.getAll());
        model.addAttribute("education", educationDAO.getAll());
        return "addVacancy";
    }

    @PostMapping("/saveVacancy")
    public String saveVacancy(
            @RequestParam(name = "companyId") Long companyId,
            @RequestParam(name = "position") String position,
            @RequestParam(name = "salary") Long salary,
            @RequestParam(name = "experience") Float experience,
            @RequestParam(name = "inputEducation") Long inputEducation,
            Model model
    ) {
        Vacancy vacancy = new Vacancy(companyDAO.getById(companyId), position, salary, experience, educationDAO.getById(inputEducation));
        vacancyDAO.add(vacancy);
        return vacancyList(null, null, null, null, null, null, null, null, model);
    }

    @PostMapping("/deleteVacancy")
    public String deleteVacancy(
            @RequestParam(name = "vacancyId") Long id,
            Model model
    ) {
        vacancyDAO.delete(vacancyDAO.getById(id));
        return vacancyList(null, null, null, null, null, null, null, null, model);
    }

    @GetMapping("/updateVacancy")
    public String updateResume(
            @RequestParam(name = "vacancyId") Long id,
            Model model
    ) {
        model.addAttribute("vacancy", vacancyDAO.getById(id));
        model.addAttribute("companies", companyDAO.getAll());
        model.addAttribute("education", educationDAO.getAll());
        return "updateVacancy";
    }

    @PostMapping("/saveUpdateVacancy")
    public String saveUpdateResume(
            @RequestParam(name = "id") Long id,
            @RequestParam(name = "companyId") Long companyId,
            @RequestParam(name = "position") String position,
            @RequestParam(name = "salary") Long salary,
            @RequestParam(name = "experience") Float experience,
            @RequestParam(name = "inputEducation") Long inputEducation,
            Model model
    ) {
        Vacancy vacancy = vacancyDAO.getById(id);
        vacancy.setCompany_id(companyDAO.getById(companyId));
        vacancy.setPosition(position);
        vacancy.setOffered_salary(salary);
        vacancy.setExperience(experience);
        vacancy.setRequired_education(educationDAO.getById(inputEducation));
        vacancyDAO.update(vacancy);
        return vacancyList(null, null, null, null, null, null, null, null, model);
    }
}
