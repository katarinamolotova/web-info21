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
    private final PeerServices peerServices;
    private final CheckServices checkServices;
    private final FriendsServices friendsServices;
    private final P2pServices p2pServices;
    private final RecommendationsServices recommendationsServices;
    private final TaskServices taskServices;
    private final TimeTrackingServices timeTrackingServices;
    private final TransferredPointsServices transferredPointsServices;
    private final VerterServices verterServices;
    private final XpServices xpServices;

    //  GET ALL

    @GetMapping("/data/checks")
    public String getCheckTable(final Model model) {
        addAttributeForFindAll(model, checkServices);
        return "data";
    }

    @GetMapping("/data/friends")
    public String getFriendsTable(final Model model) {
        addAttributeForFindAll(model, friendsServices);
        return "data";
    }

    @GetMapping("/data/p2p")
    public String getP2pTable(final Model model) {
        addAttributeForFindAll(model, p2pServices);
        return "data";
    }

    @GetMapping("/data/peers")
    public String getPeerTable(final Model model) {
        addAttributeForFindAll(model, peerServices);
        return "data";
    }

    @GetMapping("/data/recommendations")
    public String getRecommendationsTable(final Model model) {
        addAttributeForFindAll(model, recommendationsServices);
        return "data";
    }

    @GetMapping("/data/tasks")
    public String getTaskTable(final Model model) {
        addAttributeForFindAll(model, taskServices);
        return "data";
    }

    @GetMapping("/data/time_tracking")
    public String getTimeTrackingTable(final Model model) {
        addAttributeForFindAll(model, timeTrackingServices);
        return "data";
    }

    @GetMapping("/data/transferred_points")
    public String getTransferredTable(final Model model) {
        addAttributeForFindAll(model, transferredPointsServices);
        return "data";
    }

    @GetMapping("/data/verter")
    public String getVerterTable(final Model model) {
        addAttributeForFindAll(model, verterServices);
        return "data";
    }

    @GetMapping("/data/xp")
    public String getXpTable(final Model model) {
        addAttributeForFindAll(model, xpServices);
        return "data";
    }

    private void addAttributeForFindAll(final Model model, final EduService service) {
        final List list = service.findAllAsString();
        model.addAttribute("rows", list);

        final List cols = service.getHeaderForTable();
        model.addAttribute("cols", cols);

        model.addAttribute("table", service.getTableName());
    }

    //  DELETE

    @GetMapping("/data/peers/delete/{id}")
    public String peerDelete(@PathVariable final String id, final Model model) {
        peerServices.delete(id);
        addAttributeForFindAll(model, peerServices);
        return "redirect:/data/peers";
    }

    @GetMapping("/data/checks/delete/{id}")
    public String checksDelete(@PathVariable final String id, final Model model) {
        checkServices.delete(Long.parseLong(id));
        addAttributeForFindAll(model, checkServices);
        return "redirect:/data/checks";
    }

    @GetMapping("/data/friends/delete/{id}")
    public String friendsDelete(@PathVariable final String id, final Model model) {
        friendsServices.delete(Long.parseLong(id));
        addAttributeForFindAll(model, friendsServices);
        return "redirect:/data/friends";
    }

    @GetMapping("/data/p2p/delete/{id}")
    public String p2pDelete(@PathVariable final String id, final Model model) {
        p2pServices.delete(Long.parseLong(id));
        addAttributeForFindAll(model, p2pServices);
        return "redirect:/data/p2p";
    }

    @GetMapping("/data/recommendations/delete/{id}")
    public String recommendationsDelete(@PathVariable final String id, final Model model) {
        recommendationsServices.delete(Long.parseLong(id));
        addAttributeForFindAll(model, recommendationsServices);
        return "redirect:/data/recommendations";
    }

    @GetMapping("/data/tasks/delete/{id}")
    public String tasksDelete(@PathVariable final String id, final Model model) {
        taskServices.delete(id);
        addAttributeForFindAll(model, taskServices);
        return "redirect:/data/tasks";
    }

    @GetMapping("/data/time_tracking/delete/{id}")
    public String timeTrackingDelete(@PathVariable final String id, final Model model) {
        timeTrackingServices.delete(Long.parseLong(id));
        addAttributeForFindAll(model, timeTrackingServices);
        return "redirect:/data/time_tracking";
    }

    @GetMapping("/data/transferred_points/delete/{id}")
    public String transferredPointsDelete(@PathVariable final String id, final Model model) {
        transferredPointsServices.delete(Long.parseLong(id));
        addAttributeForFindAll(model, transferredPointsServices);
        return "redirect:/data/transferred_points";
    }

    @GetMapping("/data/verter/delete/{id}")
    public String verterDelete(@PathVariable final String id, final Model model) {
        verterServices.delete(Long.parseLong(id));
        addAttributeForFindAll(model, verterServices);
        return "redirect:/data/verter";
    }

    @GetMapping("/data/xp/delete/{id}")
    public String xpDelete(@PathVariable final String id, final Model model) {
        xpServices.delete(Long.parseLong(id));
        addAttributeForFindAll(model, xpServices);
        return "redirect:/data/xp";
    }

    //  ADD AND UPDATE

    @GetMapping("/data/peers/{id}")
    public String getPeerEdit(@PathVariable final String id, final Model model) {
        model.addAttribute("operations", "create");
        model.addAttribute("object", peerServices.findById(id));
        addAttributeForFindAll(model, peerServices);
        return "data";
    }

    @GetMapping("/data/peers/add")
    public String peerAdd(final Model model) {
        model.addAttribute("operations", "create");
        model.addAttribute("object", new PeerEntity());
        addAttributeForFindAll(model, peerServices);
        return "data";
    }

    @PostMapping("/data/peers")
    public String peerAddPost(@Valid final PeerEntity peer, final BindingResult bindingResult, final Model model) {
        if (!bindingResult.hasErrors()) {
            peerServices.created(peer);
        }
        return "redirect:/data/peers";
    }

//    @GetMapping("/data/checks/{id}")
//    public String checksEdit(@PathVariable final String id, final Model model) {
//        model.addAttribute("operations", "create");
//        model.addAttribute("object", checkServices.findById(id));
//        addAttributeForFindAll(model, checkServices);
//        return "data";
//    }

    @GetMapping("/data/checks/add")
    public String checkAdd(final Model model) {
        model.addAttribute("operations", "create");
        model.addAttribute("peers", peerServices.findAll());
        model.addAttribute("tasks", taskServices.findAll());
        model.addAttribute("object", new CheckEntity());
        addAttributeForFindAll(model, checkServices);
        return "data";
    }

    //  key error
    @PostMapping("/data/checks")
    public String checkAddPost(@Valid final CheckEntity check, final BindingResult bindingResult, final Model model) {
        if (!bindingResult.hasErrors()) {
            checkServices.created(check);
        }
        return "redirect:/data/checks";
    }

    @GetMapping("/data/friends/add")
    public String friendsAdd(final Model model) {
        model.addAttribute("operations", "create");
        model.addAttribute("peers", peerServices.findAll());
        model.addAttribute("object", new FriendsEntity());
        addAttributeForFindAll(model, friendsServices);
        return "data";
    }

    //  key error
    @PostMapping("/data/friends")
    public String friendAddPost(@Valid final FriendsEntity friends, final BindingResult bindingResult, final Model model) {
        if (!bindingResult.hasErrors()) {
            friendsServices.created(friends);
        }
        return "redirect:/data/friends";
    }

    @GetMapping("/data/p2p/add")
    public String p2pAdd(final Model model) {
        model.addAttribute("operations", "create");
        model.addAttribute("peers", peerServices.findAll());
        model.addAttribute("checks", checkServices.findAll());
        model.addAttribute("states", EnumSet.allOf(CheckState.class));
        model.addAttribute("object", new P2pEntity());
        addAttributeForFindAll(model, p2pServices);
        return "data";
    }

    //  key error
    //  time null???
    @PostMapping("/data/p2p")
    public String p2pAddPost(@Valid final P2pEntity p2p, final BindingResult bindingResult, final Model model) {
        if (!bindingResult.hasErrors()) {
            p2pServices.created(p2p);
        }
        return "redirect:/data/p2p";
    }

    @GetMapping("/data/recommendations/add")
    public String recommendationsAdd(final Model model) {
        model.addAttribute("operations", "create");
        model.addAttribute("peers", peerServices.findAll());
        model.addAttribute("object", new RecommendationsEntity());
        addAttributeForFindAll(model, recommendationsServices);
        return "data";
    }

    //  key error
    //  time null???
    @PostMapping("/data/recommendations")
    public String recommendationsAddPost(
            @Valid final RecommendationsEntity recommendations,
            final BindingResult bindingResult,
            final Model model
    ) {
        if (!bindingResult.hasErrors()) {
            recommendationsServices.created(recommendations);
        }
        return "redirect:/data/recommendations";
    }

    @GetMapping("/data/tasks/add")
    public String tasksAdd(final Model model) {
        model.addAttribute("operations", "create");
        model.addAttribute("tasks", taskServices.findAll());
        model.addAttribute("object", new TaskEntity());
        addAttributeForFindAll(model, taskServices);
        return "data";
    }

    @PostMapping("/data/tasks")
    public String tasksAddPost(
            @Valid final TaskEntity task,
            final BindingResult bindingResult,
            final Model model
    ) {
        if (!bindingResult.hasErrors()) {
            taskServices.created(task);
        }
        return "redirect:/data/tasks";
    }

    @GetMapping("/data/time_tracking/add")
    public String timeTrackingAdd(final Model model) {
        model.addAttribute("operations", "create");
        model.addAttribute("peers", peerServices.findAll());
        model.addAttribute("object", new TimeTrackingEntity());
        addAttributeForFindAll(model, timeTrackingServices);
        return "data";
    }

    @PostMapping("/data/time_tracking")
    public String timeTrackingAddPost(
            @Valid final TimeTrackingEntity timeTracking,
            final BindingResult bindingResult,
            final Model model
    ) {
        if (!bindingResult.hasErrors()) {
            timeTrackingServices.created(timeTracking);
        }
        return "redirect:/data/time_tracking";
    }

    @GetMapping("/data/transferred_points/add")
    public String transferredPointsAdd(final Model model) {
        model.addAttribute("operations", "create");
        model.addAttribute("peers", peerServices.findAll());
        model.addAttribute("object", new TransferredPointsEntity());
        addAttributeForFindAll(model, transferredPointsServices);
        return "data";
    }

    @PostMapping("/data/transferred_points")
    public String transferredPointsAddPost(
            @Valid final TransferredPointsEntity transferredPoints,
            final BindingResult bindingResult,
            final Model model
    ) {
        if (!bindingResult.hasErrors()) {
            transferredPointsServices.created(transferredPoints);
        }
        return "redirect:/data/transferred_points";
    }

    @GetMapping("/data/verter/add")
    public String verterAdd(final Model model) {
        model.addAttribute("operations", "create");
        model.addAttribute("checks", checkServices.findAll());
        model.addAttribute("states", EnumSet.allOf(CheckState.class));
        model.addAttribute("object", new VerterEntity());
        addAttributeForFindAll(model, verterServices);
        return "data";
    }

    @PostMapping("/data/verter")
    public String verterAddPost(@Valid final VerterEntity verter, final BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            verterServices.created(verter);
        }
        return "redirect:/data/verter";
    }

    @GetMapping("/data/xp/add")
    public String xpAdd(final Model model) {
        model.addAttribute("operations", "create");
        model.addAttribute("checks", checkServices.findAll());
        model.addAttribute("object", new XpEntity());
        addAttributeForFindAll(model, xpServices);
        return "data";
    }

    @PostMapping("/data/xp")
    public String xpAddPost(@Valid final XpEntity xp, final BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            xpServices.created(xp);
        }
        return "redirect:/data/xp";
    }
}

