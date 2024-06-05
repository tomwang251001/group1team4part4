package at.ac.fhcampuswien.fhmdb.API;

import at.ac.fhcampuswien.fhmdb.Exceptions.MovieApiException;
import okhttp3.OkHttpClient;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MovieAPITest {
    @Test
    public void test_getRequest() {
        //given
        OkHttpClient httpClient = new OkHttpClient();
        MovieAPI movieAPI = new MovieAPI(httpClient);

        // when
        try {
            String result = movieAPI.getRequest();
            boolean success = !result.equals("Something went wrong");
            // then
            assertTrue(success, "Expected successful request");
        }catch (MovieApiException mae){
            System.out.println("API exception occurred");
        }

    }
}