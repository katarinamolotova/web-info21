package edu.school21.info21.controllers;

import edu.school21.info21.services.ApiService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@RestController
@AllArgsConstructor
public class ApiController {

    private final ApiService service;

    @GetMapping("/api/{base}/{id}")
    public String apiOperations(@PathVariable String base,
                                @PathVariable String id) {
        return service.findById(base, id);
    }

    @GetMapping("/api/path")
    public String getPath() {
        File file = new File("");
        String path = file.getAbsolutePath() + "/import";
        return path;
    }

    @GetMapping("/api/{base}")
    public String apiFindAll(@PathVariable String base) {
        return service.findAll(base);
    }

    @GetMapping("/api/health-check")
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
}