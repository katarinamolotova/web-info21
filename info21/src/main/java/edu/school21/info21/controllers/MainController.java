package edu.school21.info21.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class MainController {

    @GetMapping("/")
    public String getMainPage(final Model model) {
        model.addAttribute("tab", "main");
        return "index";
    }
}
