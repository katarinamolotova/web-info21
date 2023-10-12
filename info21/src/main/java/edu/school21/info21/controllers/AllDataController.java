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

    @GetMapping("/data/time-tracking")
    public String getTimeTrackingTable(final Model model) {
        addAttributeForFindAll(model, timeTrackingServices);
        return "data";
    }

    @GetMapping("/data/transferred-points")
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
    public String getPeerDelete(@PathVariable final String id, final Model model) {
        peerServices.delete(id);
        addAttributeForFindAll(model, peerServices);
        return "redirect:/data/peers";
    }
}

