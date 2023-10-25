package edu.school21.info21.controllers;

import edu.school21.info21.repositories.FunctionsRepository;
import edu.school21.info21.services.FunctionsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
@AllArgsConstructor
public class OperationsController {
    private final FunctionsService service;

//    @GetMapping("/operations/transferred_points_from")
//    public String getAll(final Model model) {
//        model.addAttribute("table", repository.transferredPointsFromPeers());
//        model.addAttribute("functions", service.getMethodsRuNameToEnName());
//
//        return "operations";
//    }


    @GetMapping("/operations/{func}")
    public String get(final Model model, @PathVariable final String func) {
        setCommonAttributeToModel(model, func);
        return "operations";
    }

    //  without parameters
    @GetMapping("/operations/{func}/execute")
    public String execute(final Model model, @PathVariable final String func) {
        model.addAttribute("table", service.executeFunctionWithoutParameters(func));
        setCommonAttributeToModel(model, func);
        return "operations";
    }

    private void setCommonAttributeToModel(final Model model, final String func) {
        model.addAttribute("functions", service.getMethodsEnNameToRuName());
        model.addAttribute("description", service.getDescriptionForMethod(func));
        model.addAttribute("active", func);
        model.addAttribute("tab", "operations");
    }
}
