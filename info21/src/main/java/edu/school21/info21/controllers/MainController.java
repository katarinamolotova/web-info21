package edu.school21.info21.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class MainController {

    @GetMapping("/")
    public String getMainPage(Model model) {
        return "index";
    }

    @GetMapping("/operations")
    public String getOperationsPage(Model model) {
        return "operations";
    }
}
