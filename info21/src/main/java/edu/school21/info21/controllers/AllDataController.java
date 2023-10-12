package edu.school21.info21.controllers;

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
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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

    @GetMapping("/data/peers/edit/{id}")
    public String getPeerEdit(@PathVariable String id, final Model model) {
        return "edit";
    }

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
}

