package edu.school21.info21.controllers;

import edu.school21.info21.enums.InfoMessages;
import edu.school21.info21.exceptions.NotFoundFunction;
import edu.school21.info21.services.FunctionsService;
import edu.school21.info21.services.context.FunctionContext;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Slf4j
@Controller
@AllArgsConstructor
public class OperationsController {
    private final FunctionsService service;

    @GetMapping("/operations/{func}")
    public String get(final Model model, @PathVariable final String func) {
        return getBasePage(model, func);
    }

    @GetMapping("/operations/{func}/{message}")
    public String error(final Model model, @PathVariable final String func, @PathVariable final String message) {
        try {
            model.addAttribute("error", InfoMessages.valueOf(message).getName());
        } catch (final IllegalArgumentException e) {
            return "redirect:/operations/00_native_query";
        }

        return getBasePage(model, func);
    }

    private String getBasePage(final Model model, final String func) {
        final String path = setColumnsNameAndCheckExistenceFunction(model, func);
        if (path != null) {
            return path;
        }

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
            model.addAttribute("error", "Ошибка в данных. Проверьте заполненные поля.");
            log.warn("Error when executing the function {}", func);
        }

        final String path = setColumnsNameAndCheckExistenceFunction(model, func);
        if (path != null) {
            return path;
        }

        model.addAttribute("context", context);
        setCommonAttributeToModel(model, func);
        return "operations";
    }

    private void setCommonAttributeToModel(final Model model, final String func) {
        model.addAttribute("functions", service.getMethodsEnNameToRuName());
        model.addAttribute("description", service.getDescriptionForMethod(func));
        model.addAttribute("active", func);
        model.addAttribute("tab", "operations");
    }

    private String setColumnsNameAndCheckExistenceFunction(final Model model, final String func) {
        try {
            model.addAttribute("cols", service.getColumnsNameByFuncName(func));
        } catch (final NotFoundFunction e) {
            log.warn("Attempt to call a non-existent function {}", func);
            return "redirect:/operations/00_native_query";
        }
        return null;
    }
}
