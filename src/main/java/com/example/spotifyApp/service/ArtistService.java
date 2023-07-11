package com.example.spotifyApp.service;

import com.example.spotifyApp.accessTokenManager;
import com.example.spotifyApp.model.ArtistDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class ArtistService {


    public ArtistDTO getArtist(String query) throws IOException {

        ArtistDTO artistDTO = new ArtistDTO();
        accessTokenManager atm = new accessTokenManager();
        String token = atm.requestAccessToken();
        String artistID = searchArtistID(query);
        String url = "https://api.spotify.com/v1/artists/"+artistID;

        HttpURLConnection connection = null;

        try {
            URL apiUrl = new URL(url);
            connection = (HttpURLConnection) apiUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Bearer " + token);

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode responseJson = objectMapper.readTree(connection.getInputStream());

                System.out.println("Response: " + responseJson.toString());

                // Access the contents of the response
                String artistName = responseJson.get("name").asText();
                int totalFollowers = responseJson.get("followers").get("total").asInt();
                int artistPopularity = responseJson.get("popularity").asInt();

                JsonNode imagesNode = responseJson.get("images");
                List<String> imageUrls = new ArrayList<>();
                List<Integer> imageHeights = new ArrayList<>();
                List<Integer> imageWidths = new ArrayList<>();

                for (JsonNode imageNode : imagesNode) {
                    String artistImageUrls = imageNode.get("url").asText();
                    int artistImageHeights = imageNode.get("height").asInt();
                    int artistImageWidths = imageNode.get("width").asInt();

                    imageUrls.add(artistImageUrls);
                    imageHeights.add(artistImageHeights);
                    imageWidths.add(artistImageWidths);
                }

                JsonNode genresNode = responseJson.get("genres");
                List<String> genres = new ArrayList<>();
                if (genresNode.isArray()) {
                    for (JsonNode genreNode : genresNode) {
                        genres.add(genreNode.asText());
                    }
                }
                System.out.println("Name: " + artistName);
                System.out.println("Followers: " + totalFollowers );
                System.out.println("Genres: " + genres);
                System.out.println("Popularity: " + artistPopularity);
                System.out.println("artistID : " + artistID);
                System.out.println("Artist Image URL: " + imageUrls);
                System.out.println("Artist Image Height: " + imageHeights);
                System.out.println("Artist Image Width: " + imageWidths);

                artistDTO.setName(artistName);
                artistDTO.setFollowers(totalFollowers);
                artistDTO.setGenres(genres);
                artistDTO.setPopularity(artistPopularity);
                artistDTO.setArtistID(artistID);
                artistDTO.setImageUrls(imageUrls);
                artistDTO.setImageHeights(imageHeights);
                artistDTO.setImageWidths(imageWidths);


            }else {
                System.out.println("Request failed with response code: " + responseCode);
            }
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        
        return artistDTO;
    }


    public String searchArtistID( String artistName) throws IOException {
        accessTokenManager atm = new accessTokenManager();
        String token = atm.requestAccessToken();
        String ArtistID = "";

        //API endpoint URL
        String queryURL = "https://api.spotify.com/v1/search?q="+ artistName + "&type=artist&limit=1";


        URL apiUrl = new URL(queryURL);
        HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", "Bearer " + token);

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode responseJson = objectMapper.readTree(connection.getInputStream());

            System.out.println("Response: " + responseJson.toString());

            JsonNode artistsNode = responseJson.get("artists");
            if (artistsNode != null && artistsNode.has("items")) {
                JsonNode itemsNode = artistsNode.get("items");
                for (JsonNode artistNode : itemsNode) {
                    String artistID = artistNode.get("id").asText();
                    String ArtistName = artistNode.get("name").asText();
//                    System.out.println("Artist ID: " + artistID);
//                    System.out.println("Artist Name: " + ArtistName);
                    ArtistID = artistID;
                }
            }

        } else {
            System.out.println("Request failed. Response Code: " + responseCode);
        }
        return ArtistID;
    }

}
