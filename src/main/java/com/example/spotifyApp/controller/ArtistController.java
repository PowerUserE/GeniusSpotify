package com.example.spotifyApp.controller;

import com.example.spotifyApp.model.ArtistDTO;
import com.example.spotifyApp.service.ArtistService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping("/searchArtistQuery")
public class ArtistController {

    private final ArtistService artistService;

    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

//    @GetMapping()
//    public String searchArtist(@RequestParam("query") String query, Model model) throws IOException {
//        ArtistDTO artist = artistService.getArtist(query);
//        if (artist == null) {
//            System.out.println("Error 404");
//            return "errorView"; // Return the name of the view for displaying the error
//        } else {
//            model.addAttribute("artist", artist);
//            System.out.println("Works!");
////            return "searchResultsView"; // Return the name of the view for displaying search results
//            return "searchResultsView"; // Return the name of the view for displaying search results
//        }
//    }

    @GetMapping()
    public ModelAndView searchArtist(@RequestParam("query") String query) throws IOException {
        List<Map<String, Object>> artistList = artistService.getArtist(query);
        if (artistList.isEmpty()) {
            System.out.println("Error 404");
            ModelAndView modelAndView = new ModelAndView("errorView"); // Specify the name of the HTML file for displaying not found response
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("searchResultsView"); // Specify the name of the HTML file for displaying search results
            modelAndView.addObject("artist", artistList); // Add the artist object to the model
            System.out.println("Model and View shows!");
            return modelAndView;
        }
    }


}
