package edu.school21.info21.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AllDataController {

    @GetMapping("/1")
    public String getCheckTable(Model model) {
        return "index";
    }

    @GetMapping("/2")
    public String getFriendsTable(Model model) {
        return "index";
    }

    @GetMapping("/3")
    public String getP2pTable(Model model) {
        return "index";
    }

    @GetMapping("/4")
    public String getPeerTable(Model model) {
        return "index";
    }

    @GetMapping("/5")
    public String getRecommendationsTable(Model model) {
        return "index";
    }

    @GetMapping("/6")
    public String getTaskTable(Model model) {
        return "index";
    }

    @GetMapping("/7")
    public String getTimeTrackingTable(Model model) {
        return "index";
    }

    @GetMapping("/8")
    public String getTransferredTable(Model model) {
        return "index";
    }

    @GetMapping("/9")
    public String getVerterTable(Model model) {
        return "index";
    }

    @GetMapping("/10")
    public String getXpTable(Model model) {
        return "index";
    }

}

