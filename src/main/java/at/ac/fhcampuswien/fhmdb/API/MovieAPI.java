package at.ac.fhcampuswien.fhmdb.API;

import at.ac.fhcampuswien.fhmdb.Exceptions.MovieApiException;
import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.pattern.builder.urlBuilder;
import okhttp3.*;

import java.io.IOException;

public class MovieAPI {
    private static final String URL = "http://prog2.fh-campuswien.ac.at/movies";
    private final OkHttpClient httpClient;

    public MovieAPI(OkHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public String getRequest() throws MovieApiException {
        Request request = new Request.Builder()
                .url(URL)
                .addHeader("User-Agent", "http.agent")
                .build();

        Call call = httpClient.newCall(request);
        try {
            Response response = call.execute();
            return response.body().string();
        } catch (IOException ioe) {
            throw new MovieApiException("An error occurred while getRequest()", ioe);
        }
    }

    public static String searchMovies(String query, String genre, int releaseYear, double ratingFrom) throws MovieApiException {
        urlBuilder builder = new urlBuilder();
        builder.setQuery(query)
                .setGenre(Genre.valueOf(genre.toUpperCase()))
                .setReleaseYear(releaseYear)
                .setRatingFrom((int) ratingFrom);

        String url = builder.buildURL();

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .addHeader("User-Agent", "http.agent")
                .build();

        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            throw new MovieApiException("Error in searchMovies()", e);
        }
    }
}
