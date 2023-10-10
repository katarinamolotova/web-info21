package edu.school21.info21.controllers;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@RestController
public class ApiController {
    private final SessionFactory sessionFactory;

    @Autowired
    public ApiController(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @GetMapping("/api")
    public String api() {
        return "Тут будет что-то связанное с Api"; // (3)
    }

    @GetMapping("/api/test")
    public String test() {
        String url = "jdbc:postgresql://localhost:5432/info21java";
        String username = "root";
        String password = "root";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            return "Connection successful!";
        } catch (SQLException e) {
            return  "Connection failed!";
        }
    }
}