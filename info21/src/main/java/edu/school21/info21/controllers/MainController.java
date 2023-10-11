package edu.school21.info21.controllers;

import edu.school21.info21.entities.PeerEntity;
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

import java.util.List;

@Controller
@AllArgsConstructor
public class MainController {
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


    @GetMapping("/")
    public String getMainPage(Model model) {
        return "index";
    }

    @GetMapping("/data/peers")
    public String getDataPage(Model model) {
        final List<PeerEntity> peers = peerServices.findAll();
        model.addAttribute("peers", peers);

        final List<String> cols = peerServices.getHeaderForTable();
        model.addAttribute("cols", cols);
        return "data";
    }

    @GetMapping("/operations")
    public String getOperationsPage(Model model) {
        return "operations";
    }
}
