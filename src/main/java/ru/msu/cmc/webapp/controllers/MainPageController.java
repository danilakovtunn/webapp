package ru.msu.cmc.webapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainPageController {

    @GetMapping("/")
    public String mainpage(Model model) {
        return "mainpage";
    }

    @GetMapping("/error")
    public String error(
            @RequestParam(name = "message", required = false) String message,
            Model model) {
        if (message == null) {
            message = "Неправильный формат данных. Увы.";
        }
        model.addAttribute("message", message);
        return "error";
    }
}