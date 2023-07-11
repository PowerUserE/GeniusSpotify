package com.example.spotifyApp;
//import org.example.config.accessTokenManager;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.net.HttpURLConnection;
import java.net.URL;

import com.example.spotifyApp.service.ApiRequestService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SpotifyApp {
    public static void main(String[] args) throws IOException {
        SpringApplication.run(SpotifyApp.class, args);
        System.out.println("Hello world!");

        ApiRequestService apiRequestService = new ApiRequestService();
        apiRequestService.makeApiRequest();
    }



        }

