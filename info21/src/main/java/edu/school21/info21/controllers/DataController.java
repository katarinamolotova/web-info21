package edu.school21.info21.controllers;

import edu.school21.info21.entities.CheckEntity;
import edu.school21.info21.entities.EntityInfo;
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
import edu.school21.info21.enums.InfoMessages;
import edu.school21.info21.enums.TableNames;
import edu.school21.info21.services.DataService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.EnumSet;
import java.util.Objects;

@Slf4j
@Controller
@AllArgsConstructor
public class DataController {

    private final DataService dataService;

    @GetMapping("/data/{table}")
    public String getAll(@PathVariable final String table, final Model model) {
        dataService.findAllAsString(table);
        addAttributeForFindAll(model, table);
        return "data";
    }

    @GetMapping("/data/{table}/error/{message}")
    public String error(@PathVariable final String table, @PathVariable final String message, final Model model) {
        try {
            model.addAttribute("error", InfoMessages.valueOf(message).getName());
            model.addAttribute("active", table);
        } catch (final IllegalArgumentException e) {
            return "redirect:/data/peers";
        }

        dataService.findAllAsString(table);
        addAttributeForFindAll(model, table);
        return "data";
    }

    @GetMapping("/data/{table}/delete/{id}")
    public String deleteById(@PathVariable final String table, @PathVariable final String id, final Model model) {
        dataService.delete(id, table);
        return String.format("redirect:/data/%s", table);
    }

    @GetMapping("/data/{table}/{id}")
    public String editById(@PathVariable final String table, @PathVariable final String id, final Model model) {
        try {
            model.addAttribute("object", dataService.findByIdObject(table, id));
        } catch (final Exception e) {
            log.warn("Not found entity {} by id = {}", table, id);
            return String.format("redirect:/data/%s", table);
        }

        addAttributeForCreate(model);
        addAttributeForFindAll(model, table);
        return "data";
    }

    @GetMapping("/data/{table}/add")
    public String create(@PathVariable final String table, final Model model) {
        model.addAttribute("object", dataService.getEmptyEntity(table));
        addAttributeForCreate(model);
        addAttributeForFindAll(model, table);
        return "data";
    }

    @GetMapping("/data/{table}/{id}/error")
    public String editError(@PathVariable final String table, @PathVariable final String id, final Model model) {
        log.warn("Error when editing entity with id = {} from {}", id, table);
        try {
            model.addAttribute("object", dataService.findByIdObject(table, id));
        } catch (final Exception e) {
            log.warn("Not found entity {} by id = {}", table, id);
            return String.format("redirect:/data/%s", table);
        }

        model.addAttribute("error", "Ошибка в данных. Проверьте заполненные поля.");
        addAttributeForCreate(model);
        addAttributeForFindAll(model, table);
        return "data";
    }

    @GetMapping("/data/{table}/add/error")
    public String createError(@PathVariable final String table, final Model model) {
        log.warn("Error when adding to {} table", table);
        model.addAttribute("object", dataService.getEmptyEntity(table));
        model.addAttribute("error", "Ошибка в данных. Проверьте заполненные поля.");
        addAttributeForCreate(model);
        addAttributeForFindAll(model, table);
        return "data";
    }

    @PostMapping("/data/peers")
    public String createPeer(
            @Valid final PeerEntity entity,
            final BindingResult bindingResult
    ) {
        return create(entity, entity.getNickname(), "peers", bindingResult);
    }

    @PostMapping("/data/checks")
    public String createCheck(
            @Valid final CheckEntity entity,
            final BindingResult bindingResult
    ) {
        return create(entity, String.valueOf(entity.getId()), "checks", bindingResult);
    }

    @PostMapping("/data/friends")
    public String createFriends(
            @Valid final FriendsEntity entity,
            final BindingResult bindingResult
    ) {
        return create(entity, String.valueOf(entity.getId()), "friends", bindingResult);
    }

    @PostMapping("/data/p2p")
    public String createP2p(
            @Valid final P2pEntity entity,
            final BindingResult bindingResult
    ) {
        return create(entity, String.valueOf(entity.getId()), "p2p", bindingResult);
    }

    @PostMapping("/data/recommendations")
    public String createRecommendations(
            @Valid final RecommendationsEntity entity,
            final BindingResult bindingResult
    ) {
        return create(entity, String.valueOf(entity.getId()), "recommendations", bindingResult);
    }

    @PostMapping("/data/tasks")
    public String createTask(
            @Valid final TaskEntity entity,
            final BindingResult bindingResult
    ) {
        return create(entity, entity.getTitle(), "tasks", bindingResult);
    }

    @PostMapping("/data/time_tracking")
    public String createTimeTracking(
            @Valid final TimeTrackingEntity entity,
            final BindingResult bindingResult
    ) {
        return create(entity, String.valueOf(entity.getId()), "time_tracking", bindingResult);
    }

    @PostMapping("/data/transferred_points")
    public String createTransferredPoints(
            @Valid final TransferredPointsEntity entity,
            final BindingResult bindingResult
    ) {
        return create(entity, String.valueOf(entity.getId()), "transferred_points", bindingResult);
    }

    @PostMapping("/data/verter")
    public String createVerter(
            @Valid final VerterEntity entity,
            final BindingResult bindingResult
    ) {
        return create(entity, String.valueOf(entity.getId()), "verter", bindingResult);
    }

    @PostMapping("/data/xp")
    public String createXp(
            @Valid final XpEntity entity,
            final BindingResult bindingResult
    ) {
        return create(entity, String.valueOf(entity.getId()), "xp", bindingResult);
    }

    private String create(
            final EntityInfo entity,
            final String id,
            final String table,
            final BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return redirectToAddOrEditWithError(id, table);
        }

        try {
            dataService.created(entity, table);
        } catch (final Exception ex) {
            return redirectToAddOrEditWithError(id, table);
        }

        return String.format("redirect:/data/%s", table);
    }

    private String redirectToAddOrEditWithError(final String id, final String table) {
        return  Objects.nonNull(id) && dataService.existsById(table, id) ?
                String.format("redirect:/data/%s/%s/error", table, id) :
                String.format("redirect:/data/%s/add/error", table);
    }

    private void addAttributeForFindAll(final Model model, final String table) {
        model.addAttribute("tab", "data");
        model.addAttribute("rows", dataService.findAllAsString(table));
        model.addAttribute("cols", dataService.getHeaderForTable(table));
        model.addAttribute("table", table);
        model.addAttribute("tables", TableNames.getAllNames());
    }

    private void addAttributeForCreate(final Model model) {
        model.addAttribute("operations", "create");
        model.addAttribute("peers", dataService.findAllObjects("peers"));
        model.addAttribute("tasks", dataService.findAllObjects("tasks"));
        model.addAttribute("checks", dataService.findAllObjects("checks"));
        model.addAttribute("states", EnumSet.allOf(CheckState.class));
    }
}
