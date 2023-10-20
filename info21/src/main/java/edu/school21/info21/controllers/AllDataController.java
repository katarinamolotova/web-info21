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
import java.util.Objects;

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

    @GetMapping("/data/{table}/{id}/error")
    public String editError(@PathVariable final String table, @PathVariable final String id, final Model model) {
        model.addAttribute("object", apiService.findByIdObject(table, id));
        model.addAttribute("error", true);
        addAttributeForCreate(model);
        addAttributeForFindAll(model, table);
        return "data";
    }

    @GetMapping("/data/{table}/add/error")
    public String createError(@PathVariable final String table, final Model model) {
        model.addAttribute("object", apiService.getEmptyEntity(table));
        model.addAttribute("error", true);
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
            apiService.created(entity, table);
        } catch (final Exception ex) {
            return redirectToAddOrEditWithError(id, table);
        }

        return String.format("redirect:/data/%s", table);
    }

    private String redirectToAddOrEditWithError(final String id, final String table) {
        return Objects.nonNull(id) && !id.isEmpty() && !id.equals("0")?
                String.format("redirect:/data/%s/%s/error", table, id) :
                String.format("redirect:/data/%s/add/error", table);
    }

    private void addAttributeForFindAll(final Model model, final String table) {
        model.addAttribute("tab", "data");
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