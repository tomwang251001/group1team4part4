package at.ac.fhcampuswien.fhmdb.models;

import at.ac.fhcampuswien.fhmdb.API.MovieAPI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.google.gson.*;


public class Movie {
    public final String title;
    public final String description;
    public final List<Genre> genres;
    public int id;
    public int releaseYear;
    public String imgUrl;
    public int lengthInMinutes;
    public List<String> directors;
    public List<String> writers;
    public List<String> mainCast;
    public double rating;

    public Movie(int id, String title, List<Genre> genres, int releaseYear, String description, String imgUrl, int lengthInMinutes, List<String> directors, List<String> writers, List<String> mainCast, double rating) {
        this.id = id;
        this.title = title;
        this.genres = genres;
        this.releaseYear = releaseYear;
        this.description = description;
        this.imgUrl = imgUrl;
        this.lengthInMinutes = lengthInMinutes;
        this.directors = directors;
        this.writers = writers;
        this.mainCast = mainCast;
        this.rating = rating;
    }
    public static List<Movie> initializeMovies(){
        MovieAPI movieAPI = new MovieAPI();
        Gson gson = new Gson();

        String json = movieAPI.getRequest();

        return List.of(gson.fromJson(json, Movie[].class));
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }
        if(obj == this) {
            return true;
        }
        if(!(obj instanceof Movie other)) {
            return false;
        }
        return this.title.equals(other.title) && this.description.equals(other.description) && this.genres.equals(other.genres);
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<Genre> getGenres() {
        return genres;
    }
    public int getId() {
        return id;
    }
    public int getReleaseYear() {
        return releaseYear;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public int getLengthInMinutes() {
        return lengthInMinutes;
    }

    public List<String> getDirectors() {
        return directors;
    }

    public List<String> getWriters() {
        return writers;
    }

    public List<String> getMainCast() {
        return mainCast;
    }

    public double getRating() {
        return rating;
    }

}