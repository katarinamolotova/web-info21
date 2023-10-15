package edu.school21.info21.controllers;

import edu.school21.info21.enums.CheckState;
import edu.school21.info21.services.ApiService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.EnumSet;

@Controller
@AllArgsConstructor
public class AllDataController {

    private final ApiService apiService;

    @GetMapping("/data/{table}")
    public String getAll(@PathVariable final String table, final Model model) {
        apiService.findAllAsString(table);
        addAttributeForFindAll(model, table);
        return "data";
    }

    @GetMapping("/data/{table}/delete/{id}")
    public String deleteById(@PathVariable final String table, @PathVariable final String id, final Model model) {
        apiService.delete(id, table);
        addAttributeForFindAll(model, table);
        return String.format("redirect:/data/%s", table);
    }

    @GetMapping("/data/{table}/{id}")
    public String editById(@PathVariable final String table, @PathVariable final String id, final Model model) {
        model.addAttribute("object", apiService.findByIdObject(table, id));
        addAttributeForCreate(model);
        addAttributeForFindAll(model, table);
        return "data";
    }

    @GetMapping("/data/{table}/add")
    public String create(@PathVariable final String table, final Model model) {
        model.addAttribute("object", apiService.getEmptyEntity(table));
        addAttributeForCreate(model);
        addAttributeForFindAll(model, table);
        return "data";
    }

    //  cannot be cast to clas
    @PostMapping("/data/{table}")
    public String add(
            @PathVariable final String table,
            @Valid final Object object,
            final BindingResult bindingResult
    ) {
        if (!bindingResult.hasErrors()) {
            apiService.created(object, table);
        }
        return String.format("redirect:/data/%s", table);
    }

    private void addAttributeForFindAll(final Model model, final String table) {
        model.addAttribute("rows", apiService.findAllAsString(table));
        model.addAttribute("cols", apiService.getHeaderForTable(table));
        model.addAttribute("table", table);
    }

    private void addAttributeForCreate(final Model model) {
        model.addAttribute("operations", "create");
        model.addAttribute("peers", apiService.findAllObjects("peers"));
        model.addAttribute("tasks", apiService.findAllObjects("tasks"));
        model.addAttribute("checks", apiService.findAllObjects("checks"));
        model.addAttribute("states", EnumSet.allOf(CheckState.class));
    }
}