package at.ac.fhcampuswien.fhmdb.API;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.google.gson.*;
import okhttp3.*;
import java.io.IOException;
import java.util.*;


public class MovieAPI{

    //public static final String CONNECTOR = "&";
    private static final String URL = "http://prog2.fh-campuswien.ac.at/movies";

    public String getRequest(){
        final OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(URL)
                .addHeader("User-Agent", "http.agent")
                .build();

        Call call = client.newCall(request);
        try(Response response = call.execute()) {

            return response.body().string();
        } catch (IOException e){

            return ("Something went wrong");
        }


       /* Gson gson = new Gson();

        List<Movie> movies = new ArrayList<>();
        JsonArray jArray = gson.fromJson(response.body().string(), JsonArray.class);

        for (JsonElement element : jArray) {
            Movie item = gson.fromJson(element, Movie.class);
            movies.add(item);
        }

        response.close();
        return movies;

        */
    }
}
