package edu.school21.info21.controllers;

import edu.school21.info21.services.FunctionsService;
import edu.school21.info21.services.context.FunctionContext;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class OperationsController {
    private final FunctionsService service;

    @GetMapping("/operations/{func}")
    public String get(final Model model, @PathVariable final String func) {
        model.addAttribute("context", new FunctionContext());
        setCommonAttributeToModel(model, func);
        return "operations";
    }

    @PostMapping("/operations/{func}")
    public String execute(final Model model, @PathVariable final String func, @Valid final FunctionContext context) {
        try {
            final List table = service.executeFunction(func, context);
            model.addAttribute("table", table);
        } catch (final Exception e) {
            model.addAttribute("error", true);
        }
        model.addAttribute("context", context);
        setCommonAttributeToModel(model, func);
        return "operations";
    }

    private void setCommonAttributeToModel(final Model model, final String func) {
        model.addAttribute("cols", service.getColumnsNameByFuncName(func));
        model.addAttribute("functions", service.getMethodsEnNameToRuName());
        model.addAttribute("description", service.getDescriptionForMethod(func));
        model.addAttribute("active", func);
        model.addAttribute("tab", "operations");
    }
}
