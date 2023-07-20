package com.example.spotifyApp.controller;

import com.example.spotifyApp.service.ArtistService;
import com.example.spotifyApp.service.SearchService;
import com.example.spotifyApp.service.TrackService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/searchArtistQuery")
public class ArtistController {

    private final ArtistService artistService;
    private final TrackService trackService;

    private final SearchService searchService;

    public ArtistController(ArtistService artistService, TrackService trackService, SearchService searchService) throws IOException {
        this.artistService = artistService;
        this.trackService = trackService;
        this.searchService = searchService;
    }


    @GetMapping()
    public ModelAndView searchArtist(@RequestParam(value = "query", required = false) String query,
                                     @RequestParam(value = "queryTrackArtist", required = false) String queryTrackArtist,
                                     @RequestParam(value = "specificArtistQuery", required = false) String queryArtistDetails) throws IOException {
        List<Map<String, Object>> artistList = artistService.getArtist(query);
        List<Map<String, Object>> trackList = null;
        List<String> artistDetails = null;

        if (queryTrackArtist != null) {
            artistDetails = searchService.searchArtistDetails(queryArtistDetails);
            trackList = trackService.getArtistTracks(queryTrackArtist); // Retrieve track profiles
        }

        if (artistList.isEmpty() && (trackList == null || trackList.isEmpty())) {
            System.out.println("Error 404");
            ModelAndView modelAndView = new ModelAndView("errorView"); // Specify the name of the HTML file for displaying not found response
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("index"); // Specify the name of the HTML file for displaying search results
            modelAndView.addObject("artist", artistList); // Add the artist object to the model
            modelAndView.addObject("track", trackList); // Add the track object to the model
            modelAndView.addObject("specificArtist", artistDetails);
            System.out.println("Model and View shows!");
            return modelAndView;
        }
    }


}
