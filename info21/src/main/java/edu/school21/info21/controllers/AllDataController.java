package edu.school21.info21.controllers;

import edu.school21.info21.entities.PeerEntity;
import edu.school21.info21.entities.XpEntity;
import edu.school21.info21.handlers.EntityHandler;
import edu.school21.info21.services.CheckServices;
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

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    private final EntityHandler<PeerEntity> entityHandler;

    @GetMapping("/data/checks")
    public String getCheckTable(Model model) {
        return "index";
    }

    @GetMapping("/data/friends")
    public String getFriendsTable(Model model) {
        return "index";
    }

    @GetMapping("/data/p2p")
    public String getP2pTable(Model model) {
        return "index";
    }

    @GetMapping("/data/peers")
    public String getPeerTable(Model model) {
        final List<PeerEntity> peers = peerServices.findAll();

        List<List<String>> list = entityHandler.mapEntitiesToListString(peers, PeerEntity.class);

        model.addAttribute("rows", list);

        final List<String> cols = peerServices.getHeaderForTable();
        model.addAttribute("cols", cols);

        model.addAttribute("page", "Peers");
        return "data";
    }

    @GetMapping("/data/recommendations")
    public String getRecommendationsTable(Model model) {
        return "index";
    }

    @GetMapping("/data/tasks")
    public String getTaskTable(Model model) {
        return "index";
    }

    @GetMapping("/data/time-tracking")
    public String getTimeTrackingTable(Model model) {
        return "index";
    }

    @GetMapping("/data/transferred-points")
    public String getTransferredTable(Model model) {
        return "index";
    }

    @GetMapping("/data/verter")
    public String getVerterTable(Model model) {
        return "index";
    }

    @GetMapping("/data/xp")
    public String getXpTable(Model model) {
        final List<XpEntity> peers = xpServices.findAll();

        Field[] fields = XpEntity.class.getDeclaredFields();

        List<List<String>> list = peers
                .stream()
                .map(entity -> Arrays.stream(fields)
                                     .map(field -> {
                                         if (!field.isAccessible()) {
                                             field.setAccessible(true);
                                         }
                                         try {
                                             return String.valueOf(field.get(entity));
                                         } catch (IllegalAccessException e) {
                                             return "error";
                                         }
                                     }).collect(Collectors.toList())
                ).toList();

        model.addAttribute("rows", list);

        final List<String> cols = xpServices.getHeaderForTable();
        model.addAttribute("cols", cols);

        model.addAttribute("page", "Xp");
        return "data";
    }

}

