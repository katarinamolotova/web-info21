package edu.school21.info21.controllers;

import edu.school21.info21.entities.P2pEntity;
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
    public String getCheckTable(final Model model) {
//        final List<P2pEntity> p2p = p2pServices.findAll();
//        final List<List<String>> list = entityHandler.mapEntitiesToListString(p2p, P2pEntity.class);
//        model.addAttribute("rows", list);

        final List<String> cols = checkServices.getHeaderForTable();
        model.addAttribute("cols", cols);

        model.addAttribute("table", "checks");
        return "data";
    }

    @GetMapping("/data/friends")
    public String getFriendsTable(final Model model) {
//        final List<P2pEntity> p2p = p2pServices.findAll();
//        final List<List<String>> list = entityHandler.mapEntitiesToListString(p2p, P2pEntity.class);
//        model.addAttribute("rows", list);

        final List<String> cols = friendsServices.getHeaderForTable();
        model.addAttribute("cols", cols);

        model.addAttribute("table", "friends");
        return "data";
    }

    @GetMapping("/data/p2p")
    public String getP2pTable(final Model model) {
//        final List<P2pEntity> p2p = p2pServices.findAll();
//        final List<List<String>> list = entityHandler.mapEntitiesToListString(p2p, P2pEntity.class);
//        model.addAttribute("rows", list);

        final List<String> cols = p2pServices.getHeaderForTable();
        model.addAttribute("cols", cols);

        model.addAttribute("table", "p2p");
        return "data";
    }

    @GetMapping("/data/peers")
    public String getPeerTable(final Model model) {
        final List<PeerEntity> peers = peerServices.findAll();
        final List<List<String>> list = entityHandler.mapEntitiesToListString(peers, PeerEntity.class);
        model.addAttribute("rows", list);

        final List<String> cols = peerServices.getHeaderForTable();
        model.addAttribute("cols", cols);

        model.addAttribute("table", "peers");
        return "data";
    }

    @GetMapping("/data/recommendations")
    public String getRecommendationsTable(final Model model) {
//        final List<P2pEntity> p2p = p2pServices.findAll();
//        final List<List<String>> list = entityHandler.mapEntitiesToListString(p2p, P2pEntity.class);
//        model.addAttribute("rows", list);

        final List<String> cols = recommendationsServices.getHeaderForTable();
        model.addAttribute("cols", cols);

        model.addAttribute("table", "recommendations");
        return "data";
    }

    @GetMapping("/data/tasks")
    public String getTaskTable(final Model model) {
//        final List<P2pEntity> p2p = p2pServices.findAll();
//        final List<List<String>> list = entityHandler.mapEntitiesToListString(p2p, P2pEntity.class);
//        model.addAttribute("rows", list);

        final List<String> cols = taskServices.getHeaderForTable();
        model.addAttribute("cols", cols);

        model.addAttribute("table", "tasks");
        return "data";
    }

    @GetMapping("/data/time-tracking")
    public String getTimeTrackingTable(final Model model) {
//        final List<P2pEntity> p2p = p2pServices.findAll();
//        final List<List<String>> list = entityHandler.mapEntitiesToListString(p2p, P2pEntity.class);
//        model.addAttribute("rows", list);

        final List<String> cols = timeTrackingServices.getHeaderForTable();
        model.addAttribute("cols", cols);

        model.addAttribute("table", "time_tracking");
        return "data";
    }

    @GetMapping("/data/transferred-points")
    public String getTransferredTable(final Model model) {
//        final List<P2pEntity> p2p = p2pServices.findAll();
//        final List<List<String>> list = entityHandler.mapEntitiesToListString(p2p, P2pEntity.class);
//        model.addAttribute("rows", list);

        final List<String> cols = transferredPointsServices.getHeaderForTable();
        model.addAttribute("cols", cols);

        model.addAttribute("table", "transferred_points");
        return "data";
    }

    @GetMapping("/data/verter")
    public String getVerterTable(Model model) {
//        final List<P2pEntity> p2p = p2pServices.findAll();
//        final List<List<String>> list = entityHandler.mapEntitiesToListString(p2p, P2pEntity.class);
//        model.addAttribute("rows", list);

        final List<String> cols = verterServices.getHeaderForTable();
        model.addAttribute("cols", cols);

        model.addAttribute("table", "verter");
        return "data";
    }

    @GetMapping("/data/xp")
    public String getXpTable(final Model model) {
//        final List<P2pEntity> p2p = p2pServices.findAll();
//        final List<List<String>> list = entityHandler.mapEntitiesToListString(p2p, P2pEntity.class);
//        model.addAttribute("rows", list);

        final List<String> cols = xpServices.getHeaderForTable();
        model.addAttribute("cols", cols);

        model.addAttribute("table", "xp");
        return "data";
    }

}

