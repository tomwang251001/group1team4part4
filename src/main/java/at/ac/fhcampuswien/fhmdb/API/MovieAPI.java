package at.ac.fhcampuswien.fhmdb.API;
import at.ac.fhcampuswien.fhmdb.Exceptions.MovieApiException;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.pattern.builder.urlBuilder;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import okhttp3.*;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;


public class MovieAPI{

    //public static final String CONNECTOR = "&";
    private static final String URL = "http://prog2.fh-campuswien.ac.at/movies";
    private final OkHttpClient httpClient;

    public MovieAPI(OkHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public String getRequest() throws MovieApiException {
        final OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(URL)
                .addHeader("User-Agent", "http.agent")
                .build();

        Call call = client.newCall(request);
        try {
            Response response = call.execute();
            return response.body().string();
        } catch (IOException ioe) {
            throw new MovieApiException("An error occurred while getRequest()", ioe);
        }

    }

        public static String searchMovies(String query, String genre, int releaseYear, double ratingFrom) throws MovieApiException  {

            String url = URL;

            if (query != null && !query.isEmpty()) {
                url += "?query=" + query.replace(" ", " ");
            }
            if (genre != null && !genre.isEmpty()) {
                url += (url.contains("?") ? "&" : "?") + "genre=" + genre;
            }
            if (releaseYear > 0) {
                url += (url.contains("?") ? "&" : "?") + "releaseYear=" + releaseYear;
            }
            if (ratingFrom >= 0) {
                url += (url.contains("?") ? "&" : "?") + "ratingFrom=" + ratingFrom;
            }

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .addHeader("User-Agent", "http.agent")
                    .build();
            try{
                Response response = client.newCall(request).execute();
                return response.body().string();
            } catch (IOException e) {
                throw new MovieApiException("error in searchMovies()", e);
            }
        }

    public List<Movie> get() throws MovieApiException {
        urlBuilder urlBuilder = new urlBuilder();
        return get();
    }

}
