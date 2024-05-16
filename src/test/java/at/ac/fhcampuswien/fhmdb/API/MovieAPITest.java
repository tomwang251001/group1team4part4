package at.ac.fhcampuswien.fhmdb.API;

import at.ac.fhcampuswien.fhmdb.Exceptions.MovieApiException;
import at.ac.fhcampuswien.fhmdb.HomeController;
import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.BooleanSupplier;

import static org.junit.jupiter.api.Assertions.*;

class MovieAPITest {
    @Test
    public void test_getRequest() {
        //given
        MovieAPI movieAPI = new MovieAPI();

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