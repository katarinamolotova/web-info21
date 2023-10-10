package edu.school21.info21.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

    @GetMapping("/api")
    public String api() {
        return "Тут будет что-то связанное с Api"; // (3)
    }
}