package com.example.spotifyApp.service;

import com.example.spotifyApp.accessTokenManager;
import com.example.spotifyApp.model.ArtistDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;



@Service
public class ArtistService {


        public List<Map<String, Object>> getArtist(String query) throws IOException{
            List<Map<String, Object>> profiles = new ArrayList<>();


        ArtistDTO artistDTO = new ArtistDTO();
        accessTokenManager atm = new accessTokenManager();
        String token = atm.requestAccessToken();
        String tenIDs = String.valueOf(searchArtistID(query));
        String artistID = "55zx7AihSM91XuqPqofoct";
//        String url = "https://api.spotify.com/v1/artists/"+artistID;
        String url = "https://api.spotify.com/v1/search?q="+ query + "&type=artist&limit=6";

        HttpURLConnection connection = null;

        try {
            URL apiUrl = new URL(url);
            connection = (HttpURLConnection) apiUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Bearer " + token);

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                Gson gson = new Gson();
                InputStreamReader reader = new InputStreamReader(connection.getInputStream());

                BufferedReader bufferedReader = new BufferedReader(reader);
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    response.append(line);
//                    System.out.println(line);
                }
                System.out.println("JSON Response: " + response);

                JsonObject responseObj = gson.fromJson(response.toString(), JsonObject.class);
                JsonObject artistsObj = responseObj.getAsJsonObject("artists");
                JsonArray itemsArray = artistsObj.getAsJsonArray("items");

                for (int i = 0; i < itemsArray.size(); i++) {
                    JsonObject artistObj = itemsArray.get(i).getAsJsonObject();

                    String name = artistObj.get("name").getAsString();
                    int popularity = artistObj.get("popularity").getAsInt();
                    String uri = artistObj.get("uri").getAsString();
                    int totalFollowers = artistObj.getAsJsonObject("followers").get("total").getAsInt();

                    JsonArray imagesArray = artistObj.getAsJsonArray("images");
                    List<String> imageUrls = new ArrayList<>();
                    for (int j = 0; j < imagesArray.size(); j++) {
                        JsonObject imageObj = imagesArray.get(j).getAsJsonObject();
                        String imageUrl = imageObj.get("url").getAsString();
                        if(imageUrl == null){
                            imageUrls.add("/static/assets/img/B5PP-IIIMAA-qem.png");
                        }else {
                            imageUrls.add(imageUrl);
                        }
                        System.out.println(imageUrl);
                    }

                    Map<String, Object> profile = new HashMap<>();
                    profile.put("name", name);
                    profile.put("popularity", popularity);
                    profile.put("uri", uri);
                    profile.put("imageUrls", imageUrls);
                    profile.put("followers", totalFollowers);
                    profiles.add(profile);
                }

            }else {
                System.out.println("Request failed with response code: " + responseCode);
            }
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return profiles;
    }


    public ArrayList<String> searchArtistID(String artistName) throws IOException {
        ArrayList<String>IDList = new ArrayList<>();
        accessTokenManager atm = new accessTokenManager();
        String token = atm.requestAccessToken();
        String ArtistID = "";

        //API endpoint URL
        String queryURL = "https://api.spotify.com/v1/search?q="+ artistName + "&type=artist&limit=5";


        URL apiUrl = new URL(queryURL);
        HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", "Bearer " + token);

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode responseJson = objectMapper.readTree(connection.getInputStream());

//            System.out.println("Response: " + responseJson.toString());

            JsonNode artistsNode = responseJson.get("artists");
            if (artistsNode != null && artistsNode.has("items")) {
                JsonNode itemsNode = artistsNode.get("items");
                for (JsonNode artistNode : itemsNode) {
                    System.out.println(artistNode.get("id").asText());
                    IDList.add(artistNode.get("id").asText());
                }
            }

        } else {
            System.out.println("Request failed. Response Code: " + responseCode);
        }
        return IDList;
    }

}