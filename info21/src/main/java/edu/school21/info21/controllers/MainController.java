package edu.school21.info21.controllers;

import edu.school21.info21.entities.PeerEntity;
import edu.school21.info21.repositories.PeerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class MainController {
    //  тут должны быть сервисы
    //  в сервисе необходимо реализовать возможность получения списка имен столбцов
    private PeerRepository peerRepository;

    @GetMapping("/")
    public String getMainPage(Model model) {
        return "index";
    }

    @GetMapping("/data")
    public String getDataPage(Model model) {
        final List<PeerEntity> peers = (List<PeerEntity>) peerRepository.findAll();
        model.addAttribute("peers", peers);

        final List<String> cols = List.of("Nickname", "Birthday");
        model.addAttribute("cols", cols);
        return "data";
    }

    @GetMapping("/operations")
    public String getOperationsPage(Model model) {
        return "operations";
    }
}
