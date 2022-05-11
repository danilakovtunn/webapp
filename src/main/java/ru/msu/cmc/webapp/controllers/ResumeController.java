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
public class ResumeController {
    @Autowired
    private final PersonDAO personDAO = new PersonDAOImpl();
    @Autowired
    private final VacancyDAO vacancyDAO = new VacancyDAOImpl();
    @Autowired
    private final EducationDAO educationDAO = new EducationDAOImpl();
    @Autowired
    private final ResumeDAO resumeDAO = new ResumeDAOImpl();

    @GetMapping("/resumes")
    public String resumeList(
            @RequestParam(name = "nameFilter", required = false) String name,
            @RequestParam(name = "positionFilter", required = false) String position,
            @RequestParam(name = "educationFilter", required = false) String educationFilter,
            @RequestParam(name = "experienceFilter", required = false) Float experienceFilter,
            @RequestParam(name = "fromFilter", required = false) Long fromFilter,
            @RequestParam(name = "toFilter", required = false) Long toFilter,
            @RequestParam(name = "vacancyId", required = false) Long vacancyId,
            @RequestParam(name = "personId", required = false) Long personId,
            Model model) {
        List<Resume> resumes;
        if (name != null && !name.equals("")) {
            resumes = resumeDAO.getAllResumeByPerson(name);
        } else if (position != null && !position.equals("")){
            resumes = resumeDAO.getAllResumeByPosition(position);
        } else if (educationFilter != null && !educationFilter.equals("")){
            resumes = resumeDAO.getAllResumeByEducation(educationFilter);
        } else if (experienceFilter != null){
            resumes = resumeDAO.getAllResumeByExperience(experienceFilter);
        } else if (fromFilter != null && toFilter != null){
            resumes = resumeDAO.getAllResumeBySalary(fromFilter, toFilter);
        } else if (vacancyId != null){
            resumes = resumeDAO.getAllResumeByVacancy(vacancyDAO.getById(vacancyId));
        } else if (personId != null){
            Person person = personDAO.getById(personId);
            resumes = resumeDAO.getAllResumeByPerson(person.getFirst_name() + ' ' + person.getLast_name() + ' ' + person.getSur_name());
        } else {
            resumes = (List<Resume>) resumeDAO.getAll();
        }
        model.addAttribute("resumes", resumes);
        model.addAttribute("education", educationDAO.getAll());
        model.addAttribute("resumeService", resumeDAO);
        return "resumes";
    }

    @GetMapping("/addResume")
    public String addResume(Model model) {
        List<Person> people = (List<Person>) personDAO.getAll();
        model.addAttribute("people", people);
        return "addResume";
    }

    @PostMapping("/saveResume")
    public String saveResume(
            @RequestParam(name = "inputPerson") Long inputPerson,
            @RequestParam(name = "position") String position,
            @RequestParam(name = "salary") Long salary,
            @RequestParam(name = "experience") Float experience,
            Model model
    ) {
        Resume resume = new Resume(personDAO.getById(inputPerson), position, salary, experience);
        resumeDAO.add(resume);
        return resumeList(null, null, null, null, null, null, null, null, model);
    }

    @PostMapping("/deleteResume")
    public String deleteResume(
            @RequestParam(name = "resumeId") Long id,
            Model model
    ) {
        resumeDAO.delete(resumeDAO.getById(id));
        return resumeList(null, null, null, null, null, null, null, null, model);
    }

    @GetMapping("/updateResume")
    public String updateResume(
            @RequestParam(name = "resumeId") Long id,
            Model model
    ) {
        model.addAttribute("resume", resumeDAO.getById(id));
        model.addAttribute("people", personDAO.getAll());
        model.addAttribute("searcher", resumeDAO.getById(id).getPerson_id());
        return "updateResume";
    }

    @PostMapping("/saveUpdateResume")
    public String saveUpdateResume(
            @RequestParam(name = "id") Long id,
            @RequestParam(name = "inputPerson") Long inputPerson,
            @RequestParam(name = "position") String position,
            @RequestParam(name = "salary") Long salary,
            @RequestParam(name = "experience") Float experience,
            Model model
    ) {
        Resume resume = resumeDAO.getById(id);
        resume.setPerson_id(personDAO.getById(inputPerson));
        resume.setPosition(position);
        resume.setDesired_salary(salary);
        resume.setExperience(experience);
        resumeDAO.update(resume);
        return resumeList(null, null, null, null, null, null, null, null, model);
    }
}
