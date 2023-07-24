package com.example.spotifyApp;


import java.io.IOException;
import com.example.spotifyApp.service.ApiRequestService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@RestController
public class SpotifyApp {
    public static void main(String[] args) throws IOException {
        SpringApplication.run(SpotifyApp.class, args);
        System.out.println("Hello world!");

        ApiRequestService apiRequestService = new ApiRequestService();
        apiRequestService.makeApiRequest();
    }
    	@RequestMapping("/") String sayHello() {
		return "Is this the reason?!";
	}
        }

