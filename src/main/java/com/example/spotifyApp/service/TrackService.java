package com.example.spotifyApp.service;


import com.example.spotifyApp.accessTokenManager;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

@Service
public class TrackService {

    public List<Map<String, Object>> getTrack(String query) throws IOException {

        accessTokenManager atm = new accessTokenManager();
        String token = atm.requestAccessToken();
        String url = "";

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
            }
            }finally{
                if (connection != null) {
                    connection.disconnect();
                }
            }
            return null;

    }
}