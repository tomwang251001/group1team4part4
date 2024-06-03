package at.ac.fhcampuswien.fhmdb.pattern.builder;

import at.ac.fhcampuswien.fhmdb.models.Genre;

public class urlBuilder {
    private static final String BASE_URL = "http://prog2.fh-campuswien.ac.at/movies";
    private String query;
    private Genre genre;
    private int releaseYear;
    private int ratingFrom;

    public urlBuilder() {
        this.query = null;
        this.genre = null;
        this.releaseYear = 0;
        this.ratingFrom = 0;
    }

    public String buildURL(){
        StringBuilder sbURL = new StringBuilder(BASE_URL);
        boolean firstParameter = true;

        if (query != null) {
            sbURL.append(firstParameter ? "?" : "&").append("query=").append(query);
            firstParameter = false;
        }
        if (genre != null) {
            sbURL.append(firstParameter ? "?" : "&").append("genre=").append(genre);
            firstParameter = false;
        }
        if (releaseYear != 0) {
            sbURL.append(firstParameter ? "?" : "&").append("releaseYear=").append(releaseYear);
            firstParameter = false;
        }
        if (ratingFrom != 0.0) {
            sbURL.append(firstParameter ? "?" : "&").append("ratingFrom=").append(ratingFrom);
        }

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
