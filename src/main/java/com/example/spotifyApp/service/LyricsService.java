package com.example.spotifyApp.service;

import org.jmusixmatch.MusixMatch;
import org.jmusixmatch.MusixMatchException;
import org.jmusixmatch.entity.lyrics.Lyrics;
import org.jmusixmatch.entity.track.Track;
import org.jmusixmatch.entity.track.TrackData;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LyricsService {

    public static String chosenArtist = "";
    public static String chosenTrack = "";
    public static String chosenArtistImage = "";


   public String getTrackLyrics (String trackName, String artistName) throws IOException, MusixMatchException {

       String apiKey = "78d2e876b78b465128709ed4490e6057";
       MusixMatch musixMatch = new MusixMatch(apiKey);


//        trackName = "Don't stop the Party";
//        artistName = "The Black Eyed Peas";

// Track Search [ Fuzzy ]

       System.out.println("track name->:" + trackName + " artist name->:" + artistName);
       if(artistName == null || trackName == null || artistName == "artist name" || trackName == "track name"){
//            trackName = "Don't stop the Party";
//            artistName = "The Black Eyed Peas";
            return "";
       }
       Track track = musixMatch.getMatchingTrack(trackName, artistName);
       TrackData data = track.getTrack();

//       System.out.println("AlbumID : "    + data.getAlbumId());
//       System.out.println("Album Name : " + data.getAlbumName());
//       System.out.println("Artist ID : "  + data.getArtistId());
//       System.out.println("Album Name : " + data.getArtistName());
//       System.out.println("Track ID : "   + data.getTrackId());

       int trackID = data.getTrackId();

       Lyrics lyrics = musixMatch.getLyrics(trackID);

//       System.out.println("Lyrics ID       : "     + lyrics.getLyricsId());
//       System.out.println("Lyrics Language : "     + lyrics.getLyricsLang());
//       System.out.println("Lyrics Body     : "     + lyrics.getLyricsBody());
//       System.out.println("Script-Tracking-URL : " + lyrics.getScriptTrackingURL());
//       System.out.println("Pixel-Tracking-URL : "  + lyrics.getPixelTrackingURL());
//       System.out.println("Lyrics Copyright : "    + lyrics.getLyricsCopyright());

       String lyricsBody = lyrics.getLyricsBody();
       chosenArtist = data.getArtistName();
       chosenTrack = trackName;

//           System.out.println("lyricsBody->:" + lyricsBody);

       return lyricsBody;
    }

    public static String getChosenArtist(){
       return chosenArtist;
    }
    public static String getChosenTrack(){
       return chosenTrack;
    }
    public static String getChosenArtistImage(){
       return chosenArtistImage;
    }

}
