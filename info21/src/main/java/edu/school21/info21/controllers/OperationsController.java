package edu.school21.info21.controllers;

import edu.school21.info21.repositories.FunctionsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class OperationsController {
    private final FunctionsRepository repository;

    @GetMapping("/operations/transferred_points_from")
    public String getAll(final Model model) {
        model.addAttribute("table", repository.transferredPointsFromPeers());
        return "operations";
    }
}
