package at.ac.fhcampuswien.fhmdb.database;
import at.ac.fhcampuswien.fhmdb.database.MovieRepository;
import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.j256.ormlite.dao.Dao;
import org.junit.jupiter.api.Test;
import com.j256.ormlite.dao.Dao;

import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static javafx.beans.binding.Bindings.when;
import static org.junit.jupiter.api.Assertions.*;

class MovieRepositoryTest {
    //Dao<MovieEntity, Long> dao;
    MovieRepository movieRepository = new MovieRepository();
    @Test
    void test_getAllMovies() {
        // Arrange
        List<MovieEntity> expectedMovies = new ArrayList<>();
        expectedMovies.add(new MovieEntity("1", "Movie 1", "Description 1", List.of(Genre.ACTION), 2022, "img1.jpg", 120, 4.5));
        expectedMovies.add(new MovieEntity("2", "Movie 2", "Description 2", List.of(Genre.COMEDY), 2023, "img2.jpg", 110, 4.2));

        // Mocking the Dao for testing purposes
        //Dao<MovieEntity, Long> mockDao = mock(Dao.class);
        //when(mockDao.queryForAll()).thenReturn(expectedMovies);

        // Creating the MovieRepository with the mocked Dao
        //MovieRepository movieRepository = new MovieRepository(mockDao);

        // Act
        List<MovieEntity> actualMovies;
        /*try {
            actualMovies = movieRepository.getAllMovies();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }*/

        // Assert
        //assertEquals(expectedMovies.size(), actualMovies.size());
        //assertEquals(expectedMovies.get(0), actualMovies.get(0));
        //assertEquals(expectedMovies.get(1), actualMovies.get(1));
    }

    @Test
    void test_removeAll() {
        //TODO @Sascha write methode
    }

    @Test
    void test_getMovie() {
        //TODO @Sascha write methode
    }

    @Test
    void test_addAllMovies() {
        //TODO @Sascha write methode
    }
}