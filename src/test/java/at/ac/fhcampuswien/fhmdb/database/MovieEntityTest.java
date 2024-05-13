package at.ac.fhcampuswien.fhmdb.database;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class MovieEntityTest {
    private static final List<Genre> genres = new ArrayList<>();
    private static final MovieEntity movieE = new MovieEntity("1",  "testTitle",
            "testDescription",  genres,  1999,  "imgUrl",  123,  5.5);
    private static final MovieEntity movieE2 = new MovieEntity("2",  "testTitle2",
            "testDescription2",  genres,  2000,  "imgUrl2",  181,  8.5);
    private static final MovieEntity movieE3 = new MovieEntity("3",  "testTitle3",
            "testDescription3",  genres,  2020,  "imgUrl3",  142,  9.4);

    private List<Genre> stringToList(String genres) { // needed for test
        List<Genre> genreList = new ArrayList<>();
        if (genres == null || genres.isEmpty()) {
            return genreList;
        }
        String[] genreArray = genres.split(", ");
        for (String genre : genreArray) {
            genreList.add(Genre.valueOf(genre));
        }
        return genreList;
    }
    @Test
    void genresToString_Empty_List() {
        List<Genre> genres = new ArrayList<>();

        String expected = "";
        String actual = MovieEntity.genresToString(genres);

        //Assert
        assertEquals(expected, actual);
    }
    @Test
    void genresToString_List_with_1_genre() {
        //List<Genre> genres = new ArrayList<>();
        genres.add(Genre.ADVENTURE);

        String expected = "ADVENTURE ";
        String actual = MovieEntity.genresToString(genres);

        assertEquals(expected, actual);
    }
    @Test
    void genresToString_List_with_3_genres() {
        List<Genre> genres = new ArrayList<>();
        genres.add(Genre.ADVENTURE);
        genres.add(Genre.FANTASY);
        genres.add(Genre.ACTION);

        String expected = "ADVENTURE, FANTASY, ACTION ";
        String actual = MovieEntity.genresToString(genres);

        assertEquals(expected, actual);
    }

    @Test
    public void test_fromMovies_returns_empty_List() {
        //given
        List<Movie> movies = new ArrayList<Movie>();

        //when
        List<MovieEntity> result = MovieEntity.fromMovies(movies);
        List<MovieEntity> expected = new ArrayList<MovieEntity>();

        //then
        assertEquals(result, expected);
    }
    @Test
    void test_fromMovies_return_1_movie() {
        List<Genre> genres = new ArrayList<>();
        // Arrange
        List<Movie> movies = new ArrayList<Movie>();
        movies.add(new Movie("1",  "testTitle",genres,
                199,  "testDescription",  "imgUrl",
                123, 5.5));

        // Act
        List<MovieEntity> result = MovieEntity.fromMovies(movies);
        List<MovieEntity> expected = new ArrayList<MovieEntity>();

        expected.add(movieE);

        // Assert
        assertEquals(movies.size(), result.size());
        //assertEquals(movies.get(i).getId(), result.get(i).getApiId());
        assertEquals(movies.get(0).getTitle(), result.get(0).getTitle());
        assertEquals(movies.get(0).getDescription(), result.get(0).getDescription());
        assertEquals(String.join(", ", movies.get(0).getGenres().stream().map(Enum::name)
                .collect(Collectors.toList())), result.get(0).getGenres());
        assertEquals(movies.get(0).getReleaseYear(), result.get(0).getReleaseYear());
        assertEquals(movies.get(0).getImgUrl(), result.get(0).getImgUrl());
        assertEquals(movies.get(0).getLengthInMinutes(), result.get(0).getLengthInMinutes());
        assertEquals(movies.get(0).getRating(), result.get(0).getRating());

    }

    @Test
    void test_fromMovies_return_more_movie() {
        List<Genre> genres = new ArrayList<>();
        // Arrange
        List<Movie> movies = new ArrayList<Movie>();
        movies.add(new Movie("1",  "testTitle",genres,
                1999,  "testDescription",  "imgUrl",
                123, 5.5));
        movies.add(new Movie("2",  "testTitle2",genres,
                2000,  "testDescription2",  "imgUrl2",
                181, 8.5));
        movies.add(new Movie("3",  "testTitle3",genres,
                2020,  "testDescription3",  "imgUrl3",
                142, 9.4));


        // Act
        List<MovieEntity> result = MovieEntity.fromMovies(movies);
        List<MovieEntity> expected = new ArrayList<MovieEntity>();

        expected.add(movieE);
        expected.add(movieE2);
        expected.add(movieE3);

        // Assert
        assertEquals(movies.size(), result.size());

        for (int i = 0; i < movies.size(); i++) {
            //assertEquals(movies.get(i).getId(), result.get(i).getApiId());
            assertEquals(movies.get(i).getTitle(), result.get(i).getTitle());
            assertEquals(movies.get(i).getDescription(), result.get(i).getDescription());
            assertEquals(String.join(", ", movies.get(i).getGenres().stream().map(Enum::name)
                    .collect(Collectors.toList())), result.get(i).getGenres());
            assertEquals(movies.get(i).getReleaseYear(), result.get(i).getReleaseYear());
            assertEquals(movies.get(i).getImgUrl(), result.get(i).getImgUrl());
            assertEquals(movies.get(i).getLengthInMinutes(), result.get(i).getLengthInMinutes());
            assertEquals(movies.get(i).getRating(), result.get(i).getRating());
        }
    }

    @Test
    public void test_ToMovies_convert_1_MovieEntities_to_List_of_movies() {
        // Arrange
        List<MovieEntity> movieEntities = new ArrayList<>();
        movieEntities.add(movieE);

        // Act
        List<Movie> result = MovieEntity.toMovies(movieEntities);

        // Assert
        assertEquals(movieEntities.size(), result.size());

        for (int i = 0; i < movieEntities.size(); i++) {
            // assertEquals(movieEntities.get(i).getApiId(), result.get(i).getId());
            assertEquals(movieEntities.get(i).getTitle(), result.get(i).getTitle());
            assertEquals(movieEntities.get(i).getDescription(), result.get(i).getDescription());
            assertEquals(stringToList(movieEntities.get(i).getGenres()), result.get(i).getGenres());
            assertEquals(movieEntities.get(i).getReleaseYear(), result.get(i).getReleaseYear());
            assertEquals(movieEntities.get(i).getImgUrl(), result.get(i).getImgUrl());
            assertEquals(movieEntities.get(i).getLengthInMinutes(), result.get(i).getLengthInMinutes());
            assertEquals(movieEntities.get(i).getRating(), result.get(i).getRating());
        }
    }
        @Test
        public void test_ToMovies_convert_more_MovieEntities_to_List_of_movies() {
            // Arrange
            List<MovieEntity> movieEntities = new ArrayList<>();
            movieEntities.add(movieE);
            movieEntities.add(movieE2);
            movieEntities.add(movieE3);

            // Act
            List<Movie> result = MovieEntity.toMovies(movieEntities);

            // Assert
            assertEquals(movieEntities.size(), result.size());

            for (int i = 0; i < movieEntities.size(); i++) {
                // assertEquals(movieEntities.get(i).getApiId(), result.get(i).getId());
                assertEquals(movieEntities.get(i).getTitle(), result.get(i).getTitle());
                assertEquals(movieEntities.get(i).getDescription(), result.get(i).getDescription());
                assertEquals(stringToList(movieEntities.get(i).getGenres()), result.get(i).getGenres());
                assertEquals(movieEntities.get(i).getReleaseYear(), result.get(i).getReleaseYear());
                assertEquals(movieEntities.get(i).getImgUrl(), result.get(i).getImgUrl());
                assertEquals(movieEntities.get(i).getLengthInMinutes(), result.get(i).getLengthInMinutes());
                assertEquals(movieEntities.get(i).getRating(), result.get(i).getRating());
            }
    }
}