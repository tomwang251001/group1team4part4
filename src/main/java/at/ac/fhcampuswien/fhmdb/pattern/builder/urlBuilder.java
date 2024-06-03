package at.ac.fhcampuswien.fhmdb.pattern.builder;

import at.ac.fhcampuswien.fhmdb.models.Genre;

public class urlBuilder {
    private static final String URL = "http://prog2.fh-campuswien.ac.at/movies";
    private String query = null;
    private Genre genre = null;
    private int releaseYear = 0;
    private int ratingFrom = 0;

    public String buildURL(){
        StringBuilder sbURL = new StringBuilder(URL);

        if (query != null) sbURL.append("&query=").append(query);
        if (genre != null) sbURL.append("&genre=").append(genre);
        if (releaseYear != 0) sbURL.append("&releaseYear=").append(releaseYear);
        if (ratingFrom != 0) sbURL.append("&ratingFrom=").append(ratingFrom);

        return sbURL.toString();
    }

    public urlBuilder setQuery(String query){
        this.query = query;
        return this;
    }

    public urlBuilder setGenre(Genre genre){
        this.genre = genre;
        return this;
    }

    public urlBuilder setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
        return this;

    }

    public urlBuilder setRatingFrom(int ratingFrom) {
        this.ratingFrom = ratingFrom;
        return this;
    }

}
