package com.finance.dashboard.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "Welcome to the Finance Dashboard API! The system is running successfully.\n\n" +
               "--- INTERACTIVE WEBSITE EXPLORER ---\n" +
               "Please visit: http://localhost:8080/swagger-ui/index.html to explore and test the APIs visually!\n\n" +
               "Available secured endpoints via API Client:\n" +
               "- POST /api/auth/signup\n" +
               "- POST /api/auth/signin\n" +
               "- GET/POST /api/records\n" +
               "- GET /api/dashboard/summary";
    }
}
