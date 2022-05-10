package ru.msu.cmc.webapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.msu.cmc.webapp.DAO.EducationDAO;
import ru.msu.cmc.webapp.DAO.PersonDAO;
import ru.msu.cmc.webapp.DAO.PlacesDAO;
import ru.msu.cmc.webapp.DAO.impl.EducationDAOImpl;
import ru.msu.cmc.webapp.DAO.impl.PersonDAOImpl;
import ru.msu.cmc.webapp.DAO.impl.PlacesDAOImpl;
import ru.msu.cmc.webapp.models.Person;
import ru.msu.cmc.webapp.models.Places;

import java.util.List;

@Controller
public class PersonController {
    @Autowired
    private final PersonDAO personDAO = new PersonDAOImpl();

    @Autowired
    private final PlacesDAO placesDAO = new PlacesDAOImpl();

    @Autowired
    private final EducationDAO educationDAO = new EducationDAOImpl();

    @GetMapping("/people")
    public String peopleListPage(Model model) {
        List<Person> people = (List<Person>) personDAO.getAll();
        model.addAttribute("people", people);
        model.addAttribute("personService", personDAO);
        return "people";
    }

    @GetMapping("/viewPerson")
    public String personPage(@RequestParam(name = "id") Long id, Model model) {
        Person person = personDAO.getById(id);
        List<Places> places = placesDAO.getAllPlacesByPersonId(id);
        String educName = personDAO.getPersonEducationById(id);
        model.addAttribute("person", person);
        model.addAttribute("places", places);
        model.addAttribute("educName", educName);
        return "viewPerson";
    }
}