package at.ac.fhcampuswien.fhmdb.database;

import at.ac.fhcampuswien.fhmdb.database.MovieRepository;
import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.j256.ormlite.dao.Dao;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import com.j256.ormlite.dao.Dao;
import org.junit.jupiter.api.TestInstance;

import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static javafx.beans.binding.Bindings.when;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MovieRepositoryTest {
    //Dao<MovieEntity, Long> dao;
    MovieRepository movieRepository = new MovieRepository();
    List<Movie> movies = new ArrayList<>();

    @BeforeAll
    public void setUp() {
        movies.add(new Movie("1", "Movie 1", List.of(), 2020, "Description 1", "img1.jpg", 120, 4.5));
        movies.add(new Movie("2", "Movie 2", List.of(), 2010, "Description 2", "img2.jpg", 110, 4.2));
        try {
            movieRepository.addAllMovies(movies);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void test_getAllMovies() throws SQLException {
        List<MovieEntity> moviesActual = new ArrayList<>();
        moviesActual.add(new MovieEntity("1", "Movie 1", "Description 1", List.of(), 2020, "img1.jpg", 120, 4.5));
        moviesActual.add(new MovieEntity("2", "Movie 2", "Description 2", List.of(), 2010, "img2.jpg", 110, 4.2));
        List<MovieEntity> moviesExpected = movieRepository.getAllMovies();

        assertNotNull(moviesExpected);
        assertEquals(2, moviesActual.size());
        //assertEquals(moviesExpected.get(0).getTitle(),moviesActual.get(0).getTitle());
    }

    @Test
    void test_removeAll() {
        try {
            movieRepository.removeAll();
            List<MovieEntity> movies = movieRepository.getAllMovies();
            assertNotNull(movies);
            assertEquals(0, movies.size());
        } catch (SQLException e) {
            e.printStackTrace();
            fail("SQLException thrown: " + e.getMessage());
        }
    }

    @Test
    void test_getMovie() {
        MovieEntity movie = null;
        try {
            movie = movieRepository.getMovie();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        assertNotNull(movie);
        assertEquals("1", movie.getApiId());
        assertEquals("Movie 1", movie.getTitle());
    }

    @Test
    void test_addAllMovies() {

        try {
            int rowsInserted = movieRepository.addAllMovies(movies);
            assertEquals(2, rowsInserted);
            List<MovieEntity> movieEntities = movieRepository.getAllMovies();

            MovieEntity movie1 = movieEntities.get(0);
            assertEquals("1", movie1.getApiId());
            assertEquals("Movie 1", movie1.getTitle());

            MovieEntity movie2 = movieEntities.get(1);
            assertEquals("2", movie2.getApiId());
            assertEquals("Movie 2", movie2.getTitle());


        } catch (SQLException e) {
            e.printStackTrace();
            fail("SQLException thrown: " + e.getMessage());
        }
    }
}