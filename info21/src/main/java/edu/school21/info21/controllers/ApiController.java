package edu.school21.info21.controllers;

import edu.school21.info21.repositories.PeerRepository;
import lombok.AllArgsConstructor;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@RestController
@AllArgsConstructor
public class ApiController {
    private final SessionFactory sessionFactory;
    private final PeerRepository peerRepository;


    @GetMapping("/api")
    public String api() {
        return "Тут будет что-то связанное с Api"; // (3)
    }

    @GetMapping("/api/test")
    public String test() {
        String url = "jdbc:postgresql://service-db/info21java";
        String username = "root";
        String password = "root";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            return "Connection successful!";
        } catch (SQLException e) {
            return e.getMessage();
        }
    }

    @GetMapping("/api/test/hibernate")
    public String hibernate() {
        return peerRepository.loadByNickname("chastity").toString();
    }


}