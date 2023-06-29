package com.example.spotifyApp;
import org.example.config.accessTokenManager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.net.HttpURLConnection;
import java.net.URL;


@SpringBootApplication
public class SpotifyApp {
    public static void main(String[] args) throws IOException {
        SpringApplication.run(SpotifyApp.class, args);
        System.out.println("Hello world!");
        SpotifyApp sApp = new SpotifyApp();

        URL url = new URL("https://api.spotify.com");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();

        int responseCode = connection.getResponseCode();

        if (responseCode != 200){
            throw new RuntimeException("Response code: " + responseCode);
        }else{
            System.out.println("Success!");
        }
        accessTokenManager atm = new accessTokenManager();
        String token = atm.requestAccessToken();
        System.out.println(token);

        String ArtistID = sApp.searchArtist(token, "Drake");
        sApp.GetArtist(token, ArtistID );
    }

    public void GetArtist(String token, String artistID) throws IOException {
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


                }else {
                    System.out.println("Request failed with response code: " + responseCode);
                }
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
    }

    public String searchArtist(String token, String artistName) throws IOException {
        String authorization = token;
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

