package ru.msu.cmc.webapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.msu.cmc.webapp.DAO.EducationDAO;
import ru.msu.cmc.webapp.DAO.PersonDAO;
import ru.msu.cmc.webapp.DAO.PlacesDAO;
import ru.msu.cmc.webapp.DAO.impl.EducationDAOImpl;
import ru.msu.cmc.webapp.DAO.impl.PersonDAOImpl;
import ru.msu.cmc.webapp.DAO.impl.PlacesDAOImpl;
import ru.msu.cmc.webapp.models.Education;
import ru.msu.cmc.webapp.models.Person;
import ru.msu.cmc.webapp.models.Places;

import java.sql.Date;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

@Controller
public class PersonController {
    @Autowired
    private final PersonDAO personDAO = new PersonDAOImpl();

    @Autowired
    private final PlacesDAO placesDAO = new PlacesDAOImpl();

    @Autowired
    private final EducationDAO educationDAO = new EducationDAOImpl();

    @GetMapping("/people")
    public String peopleList(
            @RequestParam(name = "nameFilter", required = false) String name,
            Model model) {
        List<Person> people;
        if (name != null) {
            people = personDAO.getAllPersonByName(name);
        }
        else {
            people = (List<Person>) personDAO.getAll();
        }
        model.addAttribute("people", people);
        model.addAttribute("personService", personDAO);
        return "people";
    }

    @GetMapping("/viewPerson")
    public String viewPerson(
            @RequestParam(name = "id") Long id,
            Model model) {
        Person person = personDAO.getById(id);
        List<Places> places = placesDAO.getAllPlacesByPersonId(id);
        String educName = personDAO.getPersonEducationById(id);
        model.addAttribute("person", person);
        model.addAttribute("places", places);
        model.addAttribute("educName", educName);
        return "viewPerson";
    }

    @GetMapping("/addPerson")
    public String addPerson(Model model) {
        List<Education> education = (List<Education>) educationDAO.getAll();
        String[] statuses = new String[]{"ищет работу" , "открыт к предложениям" , "не ищет"};
        model.addAttribute("education", education);
        model.addAttribute("statuses", statuses);
        return "addPerson";
    }

    @PostMapping("/savePerson")
    public String savePerson(
            @RequestParam(name = "lastName") String lastName,
            @RequestParam(name = "firstName") String firstName,
            @RequestParam(name = "surName") String surName,
            @RequestParam(name = "inputEducation") Long inputEducation,
            @RequestParam(name = "address") String address,
            @RequestParam(name = "inputStatus") String inputStatus,
            Model model
    ) {
        Person person = new Person(firstName, lastName, surName, educationDAO.getById(inputEducation), address, inputStatus);
        personDAO.add(person);
        return viewPerson(person.getId(), model);
    }

    @PostMapping("/deletePerson")
    public String deletePerson(
            @RequestParam(name = "id") Long id,
            Model model
    ) {
        personDAO.delete(personDAO.getById(id));
        return peopleList(null, model);
    }

    @GetMapping("/updatePerson")
    public String updatePerson(
            @RequestParam(name = "id") Long id,
            Model model
    ) {
        List<Education> education = (List<Education>) educationDAO.getAll();
        String[] statuses = new String[]{"ищет работу" , "открыт к предложениям" , "не ищет"};
        model.addAttribute("education", education);
        model.addAttribute("statuses", statuses);
        model.addAttribute("person", personDAO.getById(id));
        model.addAttribute("personEduc", personDAO.getById(id).getEducation_id().getEduc_name());
        return "updatePerson";
    }

    @PostMapping("/saveUpdatePerson")
    public String saveUpdatePerson(
            @RequestParam(name = "id") Long id,
            @RequestParam(name = "lastName") String lastName,
            @RequestParam(name = "firstName") String firstName,
            @RequestParam(name = "surName") String surName,
            @RequestParam(name = "inputEducation") Long inputEducation,
            @RequestParam(name = "address") String address,
            @RequestParam(name = "inputStatus") String inputStatus,
            Model model
    ) {
        Person person = personDAO.getById(id);
        person.setLast_name(lastName);
        person.setFirst_name(firstName);
        person.setSur_name(surName);
        person.setEducation_id(educationDAO.getById(inputEducation));
        person.setHome_address(address);
        person.setSearch_status(inputStatus);
        personDAO.update(person);
        return viewPerson(person.getId(), model);
    }

    @GetMapping("/addPlace")
    public String addPlace(
            @RequestParam(name = "id") Long id,
            Model model) {
        model.addAttribute("personId", id);
        return "addPlace";
    }

    @PostMapping("/savePlace")
    public String savePlace(
            @RequestParam(name = "personId") Long personId,
            @RequestParam(name = "companyName") String companyName,
            @RequestParam(name = "position") String position,
            @RequestParam(name = "salary") Long salary,
            @RequestParam(name = "dateFrom") Date dateFrom,
            @RequestParam(name = "dateTo") Date dateTo,
            Model model
    ) {
        Places place = new Places(personDAO.getById(personId), companyName, position, salary, dateFrom, dateTo);
        placesDAO.add(place);
        return viewPerson(personId, model);
    }

    @PostMapping("/deletePlace")
    public String deletePlace(
            @RequestParam(name = "personId") Long personId,
            @RequestParam(name = "id") Long id,
            Model model
    ) {
        placesDAO.delete(placesDAO.getById(id));
        return viewPerson(personId, model);
    }

    @GetMapping("/updatePlace")
    public String updatePlace(
            @RequestParam(name = "personId") Long personId,
            @RequestParam(name = "id") Long id,
            Model model
    ) {
        model.addAttribute("place", placesDAO.getById(id));
        model.addAttribute("personId", personId);
        return "updatePlace";
    }

    @PostMapping("/saveUpdatePlace")
    public String saveUpdatePerson(
            @RequestParam(name = "personId") Long personId,
            @RequestParam(name = "id") Long id,
            @RequestParam(name = "companyName") String companyName,
            @RequestParam(name = "position") String position,
            @RequestParam(name = "salary") Long salary,
            @RequestParam(name = "dateFrom") Date dateFrom,
            @RequestParam(name = "dateTo") Date dateTo,
            Model model
    ) {
        Places place = placesDAO.getById(id);
        place.setCompany(companyName);
        place.setPosition(position);
        place.setSalary(salary);
        place.setStart_date(dateFrom);
        place.setFinish_date(dateTo);
        placesDAO.update(place);
        return viewPerson(personId, model);
    }
}