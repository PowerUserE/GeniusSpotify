package com.example.spotifyApp;


import java.io.IOException;
import com.example.spotifyApp.service.ApiRequestService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class SpotifyApp {
    public static void main(String[] args) throws IOException {
        SpringApplication.run(SpotifyApp.class, args);
        System.out.println("Hello world!");

        ApiRequestService apiRequestService = new ApiRequestService();
        apiRequestService.makeApiRequest();
    }
        }

