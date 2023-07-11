package com.example.spotifyApp.model;

import java.util.List;

public class ArtistDTO {
    private String name;
    private int followers;
    private List<String> genres;
    private int popularity;
    private String artistID;
    private List<String> imageUrls;
    private List<Integer> imageHeights;
    private List<Integer> imageWidths;

    public ArtistDTO() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public String getArtistID() {
        return artistID;
    }

    public void setArtistID(String artistID) {
        this.artistID = artistID;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public List<Integer> getImageHeights() {
        return imageHeights;
    }

    public void setImageHeights(List<Integer> imageHeights) {
        this.imageHeights = imageHeights;
    }

    public List<Integer> getImageWidths() {
        return imageWidths;
    }

    public void setImageWidths(List<Integer> imageWidths) {
        this.imageWidths = imageWidths;
    }

    public ArtistDTO(String name, int followers, List<String> genres, int popularity,
                     String artistID, List<String> imageUrls, List<Integer> imageHeights,
                     List<Integer> imageWidths) {
        this.name = name;
        this.followers = followers;
        this.genres = genres;
        this.popularity = popularity;
        this.artistID = artistID;
        this.imageUrls = imageUrls;
        this.imageHeights = imageHeights;
        this.imageWidths = imageWidths;
    }

}
