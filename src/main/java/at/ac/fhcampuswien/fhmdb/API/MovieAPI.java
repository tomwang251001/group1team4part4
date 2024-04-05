package at.ac.fhcampuswien.fhmdb.API;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.google.gson.*;
import okhttp3.*;
import java.io.IOException;
import java.util.*;


public class MovieAPI{

    private final OkHttpClient client = new OkHttpClient();
    //public static final String CONNECTOR = "&";
    private static final String URL = "http://prog2.fh-campuswien.ac.at";

    public List<Movie> getRequest() throws IOException {
        Request request = new Request.Builder()
                .url(URL + "/movies")
                .build();

        Call call = client.newCall(request);
        Response response = call.execute();

        Gson gson = new Gson();

        List<Movie> movies = new ArrayList<>();
        JsonArray jArray = gson.fromJson(response.body().string(), JsonArray.class);

        for (JsonElement element : jArray) {
            Movie item = gson.fromJson(element, Movie.class);
            movies.add(item);
        }

        response.close();
        return movies;
    }

}
