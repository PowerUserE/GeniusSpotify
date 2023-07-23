package com.example.spotifyApp.controller;

import com.example.spotifyApp.service.ArtistService;
import com.example.spotifyApp.service.LyricsService;
import com.example.spotifyApp.service.SearchService;
import com.example.spotifyApp.service.TrackService;
import org.jmusixmatch.MusixMatchException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.example.spotifyApp.service.LyricsService.chosenArtist;
import static com.example.spotifyApp.service.LyricsService.chosenTrack;

@Controller
@RequestMapping("/searchArtistQuery")
public class ArtistController {

    private final ArtistService artistService;
    private final TrackService trackService;

    private final SearchService searchService;
    private final LyricsService lyricsService;


    public ArtistController(ArtistService artistService, TrackService trackService, SearchService searchService, LyricsService lyricsService) throws IOException {
        this.artistService = artistService;
        this.trackService = trackService;
        this.searchService = searchService;
        this.lyricsService = lyricsService;
    }

@GetMapping()
    public ModelAndView searchArtist(@RequestParam(value = "query", required = false) String query,
                                     @RequestParam(value = "queryTrackArtist", required = false) String queryTrackArtist,
                                     @RequestParam(value = "specificArtistQuery", required = false) String queryArtistDetails,
                                     @RequestParam(value = "fetchLyrics", required = false) boolean fetchLyrics ,
                                     @RequestParam(value = "trackName", required = false) String trackName,
                                     @RequestParam(value = "artistName", required = false) String artistName) throws IOException, MusixMatchException {

        if (fetchLyrics == true && query == null) {
            // Handle request to fetch lyrics here
            String lyrics = lyricsService.getTrackLyrics(trackName, artistName);
            List<Map<String, Object>> trackList = null;
            trackList = trackService.getArtistTracks(artistName);


            ModelAndView modelAndView = new ModelAndView("index");
            modelAndView.addObject("lyrics", lyrics);
            modelAndView.addObject("chosenArtist", chosenArtist);
            modelAndView.addObject("chosenTrack", chosenTrack);
            modelAndView.addObject("track", trackList);
            System.out.println("got here " + fetchLyrics);
            return modelAndView;
        } else {
            String lyrics = lyricsService.getTrackLyrics(trackName, artistName);
            // Handle the initial search request here
            List<Map<String, Object>> artistList = artistService.getArtist(query);
            List<Map<String, Object>> trackList = null;
            List<String> artistDetails = null;
            String chosenArtist = LyricsService.getChosenArtist();

            if (queryTrackArtist != null) {
                artistDetails = searchService.searchArtistDetails(queryArtistDetails);
                trackList = trackService.getArtistTracks(queryTrackArtist); // Retrieve track profiles
            }

            if (artistList.isEmpty() && (trackList == null || trackList.isEmpty())) {
                System.out.println("Error 404");
                ModelAndView modelAndView = new ModelAndView("errorView");
                return modelAndView;
            } else {
                ModelAndView modelAndView = new ModelAndView("index");
                modelAndView.addObject("artist", artistList);
                modelAndView.addObject("track", trackList);
                modelAndView.addObject("specificArtist", artistDetails);
                modelAndView.addObject("lyrics", lyrics);
                modelAndView.addObject("chosenArtist", chosenArtist);
                System.out.println("chosenArtist: " + chosenArtist);
                modelAndView.addObject("chosenTrack", chosenTrack);
                System.out.println("chosenTrack: " + chosenTrack);
                System.out.println("Model and View shows!");
                return modelAndView;
            }
        }
    }




}
