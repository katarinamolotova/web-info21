package edu.school21.info21.controllers;

import edu.school21.info21.repositories.FunctionsRepository;
import edu.school21.info21.services.FunctionsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@Controller
@AllArgsConstructor
public class OperationsController {
    private final FunctionsService service;
    private final FunctionsRepository repository;

//    @GetMapping("/operations/transferred_points_from")
//    public String getAll(final Model model) {
//        model.addAttribute("table", repository.transferredPointsFromPeers());
//        model.addAttribute("functions", service.getMethodsRuNameToEnName());
//
//        return "operations";
//    }


    @GetMapping("/operations/{func}")
    public String get(final Model model, @PathVariable final String func) {
//        model.addAttribute("table", repository.transferredPointsFromPeers()); //  общая функция для всех?
        final Map<String, String> methodsName = service.getMethodsEnNameToRuName();
        model.addAttribute("functions", methodsName);
        model.addAttribute("description", service.getDescriptionForMethod(func));
        model.addAttribute("active", methodsName.get(func));
        model.addAttribute("tab", "operations");
        return "operations";
    }
}
