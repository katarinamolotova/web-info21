package edu.school21.info21.controllers;

import edu.school21.info21.entities.CheckEntity;
import edu.school21.info21.entities.FriendsEntity;
import edu.school21.info21.entities.P2pEntity;
import edu.school21.info21.entities.PeerEntity;
import edu.school21.info21.entities.RecommendationsEntity;
import edu.school21.info21.entities.TaskEntity;
import edu.school21.info21.entities.TimeTrackingEntity;
import edu.school21.info21.entities.TransferredPointsEntity;
import edu.school21.info21.entities.VerterEntity;
import edu.school21.info21.entities.XpEntity;
import edu.school21.info21.enums.CheckState;
import edu.school21.info21.handlers.EntityHandler;
import edu.school21.info21.services.ApiService;
import edu.school21.info21.services.CheckServices;
import edu.school21.info21.services.EduService;
import edu.school21.info21.services.FriendsServices;
import edu.school21.info21.services.P2pServices;
import edu.school21.info21.services.PeerServices;
import edu.school21.info21.services.RecommendationsServices;
import edu.school21.info21.services.TaskServices;
import edu.school21.info21.services.TimeTrackingServices;
import edu.school21.info21.services.TransferredPointsServices;
import edu.school21.info21.services.VerterServices;
import edu.school21.info21.services.XpServices;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.EnumSet;
import java.util.List;

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
        model.addAttribute("object", apiService.findById(table, id));
        addAttributeForCreate(model);
        addAttributeForFindAll(model, table);
        return "data";
    }

    @GetMapping("/data/{table}/add")
    public String create(@PathVariable final String table, final Model model) {
        // TODO
//        model.addAttribute("object", new PeerEntity());

        addAttributeForCreate(model);
        addAttributeForFindAll(model, table);
        return "data";
    }

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
        model.addAttribute("peers", apiService.findAll("peers"));
        model.addAttribute("tasks", apiService.findAll("tasks"));
        model.addAttribute("checks", apiService.findAll("checks"));
        model.addAttribute("states", EnumSet.allOf(CheckState.class));
    }
}