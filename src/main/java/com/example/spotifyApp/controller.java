package com.example.spotifyApp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

// @RestController
@Controller
public class controller {

    @GetMapping("/")
    public String index() {
        // return "Greetings from Spring Boot!";
        return "index";
    }

}