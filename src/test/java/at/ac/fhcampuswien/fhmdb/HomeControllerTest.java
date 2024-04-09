package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.models.SortedState;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HomeControllerTest {
    private static HomeController homeController;
    @BeforeAll
    static void init() {
        homeController = new HomeController();
    }

    @Test
    void at_initialization_allMovies_and_observableMovies_should_be_filled_and_equal() throws IOException {
        homeController.initializeState();
        assertEquals(homeController.allMovies, homeController.observableMovies);
    }

    @Test
    void if_not_yet_sorted_sort_is_applied_in_ascending_order() throws IOException {
        // given
        homeController.initializeState();
        homeController.sortedState = SortedState.NONE;

        // when
        homeController.sortMovies();

        // then
        List<Movie> expected = Arrays.asList(
                new Movie(
                        "Avatar",
                        "A paraplegic Marine dispatched to the moon Pandora on a unique mission becomes torn between following his orders and protecting the world he feels is his home.",
                        Arrays.asList(Genre.ANIMATION, Genre.DRAMA, Genre.ACTION)),
                new Movie(
                        "Life Is Beautiful",
                        "When an open-minded Jewish librarian and his son become victims of the Holocaust, he uses a perfect mixture of will, humor, and imagination to protect his son from the dangers around their camp." ,
                        Arrays.asList(Genre.DRAMA, Genre.ROMANCE)),
                new Movie(
                        "Puss in Boots",
                        "An outlaw cat, his childhood egg-friend, and a seductive thief kitty set out in search for the eggs of the fabled Golden Goose to clear his name, restore his lost honor, and regain the trust of his mother and town.",
                        Arrays.asList(Genre.COMEDY, Genre.FAMILY, Genre.ANIMATION)),
                new Movie(
                        "The Usual Suspects",
                        "A sole survivor tells of the twisty events leading up to a horrific gun battle on a boat, which begin when five criminals meet at a seemingly random police lineup.",
                        Arrays.asList(Genre.CRIME, Genre.DRAMA, Genre.MYSTERY)),
                new Movie(
                        "The Wolf of Wall Street",
                        "Based on the true story of Jordan Belfort, from his rise to a wealthy stock-broker living the high life to his fall involving crime, corruption and the federal government.",
                        Arrays.asList(Genre.DRAMA, Genre.ROMANCE, Genre.BIOGRAPHY))

        );

        assertEquals(expected, homeController.observableMovies);

    }

    @Test
    void if_last_sort_ascending_next_sort_should_be_descending() throws IOException {
        // given
        homeController.initializeState();
        homeController.sortedState = SortedState.ASCENDING;

        // when
        homeController.sortMovies();

        // then
        List<Movie> expected = Arrays.asList(
                new Movie(
                        "The Wolf of Wall Street",
                        "Based on the true story of Jordan Belfort, from his rise to a wealthy stock-broker living the high life to his fall involving crime, corruption and the federal government.",
                        Arrays.asList(Genre.DRAMA, Genre.ROMANCE, Genre.BIOGRAPHY)),
                new Movie(
                        "The Usual Suspects",
                        "A sole survivor tells of the twisty events leading up to a horrific gun battle on a boat, which begin when five criminals meet at a seemingly random police lineup.",
                        Arrays.asList(Genre.CRIME, Genre.DRAMA, Genre.MYSTERY)),
                new Movie(
                        "Puss in Boots",
                        "An outlaw cat, his childhood egg-friend, and a seductive thief kitty set out in search for the eggs of the fabled Golden Goose to clear his name, restore his lost honor, and regain the trust of his mother and town.",
                        Arrays.asList(Genre.COMEDY, Genre.FAMILY, Genre.ANIMATION)),
                new Movie(
                        "Life Is Beautiful",
                        "When an open-minded Jewish librarian and his son become victims of the Holocaust, he uses a perfect mixture of will, humor, and imagination to protect his son from the dangers around their camp." ,
                        Arrays.asList(Genre.DRAMA, Genre.ROMANCE)),
                new Movie(
                        "Avatar",
                        "A paraplegic Marine dispatched to the moon Pandora on a unique mission becomes torn between following his orders and protecting the world he feels is his home.",
                        Arrays.asList(Genre.ANIMATION, Genre.DRAMA, Genre.ACTION))
        );

        assertEquals(expected, homeController.observableMovies);
    }

    @Test
    void if_last_sort_descending_next_sort_should_be_ascending() throws IOException {
        // given
        homeController.initializeState();
        homeController.sortedState = SortedState.DESCENDING;

        // when
        homeController.sortMovies();

        // then
        List<Movie> expected = Arrays.asList(
                new Movie(
                        "Avatar",
                        "A paraplegic Marine dispatched to the moon Pandora on a unique mission becomes torn between following his orders and protecting the world he feels is his home.",
                        Arrays.asList(Genre.ANIMATION, Genre.DRAMA, Genre.ACTION)),
                new Movie(
                        "Life Is Beautiful",
                        "When an open-minded Jewish librarian and his son become victims of the Holocaust, he uses a perfect mixture of will, humor, and imagination to protect his son from the dangers around their camp." ,
                        Arrays.asList(Genre.DRAMA, Genre.ROMANCE)),
                new Movie(
                        "Puss in Boots",
                        "An outlaw cat, his childhood egg-friend, and a seductive thief kitty set out in search for the eggs of the fabled Golden Goose to clear his name, restore his lost honor, and regain the trust of his mother and town.",
                        Arrays.asList(Genre.COMEDY, Genre.FAMILY, Genre.ANIMATION)),
                new Movie(
                        "The Usual Suspects",
                        "A sole survivor tells of the twisty events leading up to a horrific gun battle on a boat, which begin when five criminals meet at a seemingly random police lineup.",
                        Arrays.asList(Genre.CRIME, Genre.DRAMA, Genre.MYSTERY)),
                new Movie(
                        "The Wolf of Wall Street",
                        "Based on the true story of Jordan Belfort, from his rise to a wealthy stock-broker living the high life to his fall involving crime, corruption and the federal government.",
                        Arrays.asList(Genre.DRAMA, Genre.ROMANCE, Genre.BIOGRAPHY))

        );

        assertEquals(expected, homeController.observableMovies);

    }

    @Test
    void query_filter_matches_with_lower_and_uppercase_letters() throws IOException {
        // given
        homeController.initializeState();
        String query = "IfE";

        // when
        List<Movie> actual = homeController.filterByQuery(homeController.observableMovies, query);

        // then
        List<Movie> expected = Arrays.asList(
                new Movie(
                        "Life Is Beautiful",
                        "When an open-minded Jewish librarian and his son become victims of the Holocaust, he uses a perfect mixture of will, humor, and imagination to protect his son from the dangers around their camp." ,
                        Arrays.asList(Genre.DRAMA, Genre.ROMANCE)),
                new Movie(
                        "The Wolf of Wall Street",
                        "Based on the true story of Jordan Belfort, from his rise to a wealthy stock-broker living the high life to his fall involving crime, corruption and the federal government.",
                        Arrays.asList(Genre.DRAMA, Genre.ROMANCE, Genre.BIOGRAPHY))
        );

        assertEquals(expected, actual);
    }

    @Test
    void query_filter_with_null_movie_list_throws_exception() throws IOException {
        // given
        homeController.initializeState();
        String query = "IfE";

        // when and then
        assertThrows(IllegalArgumentException.class, () -> homeController.filterByQuery(null, query));
    }

    @Test
    void query_filter_with_null_value_returns_unfiltered_list() throws IOException {
        // given
        homeController.initializeState();
        String query = null;

        // when
        List<Movie> actual = homeController.filterByQuery(homeController.observableMovies, query);

        // then
        assertEquals(homeController.observableMovies, actual);
    }

    @Test
    void genre_filter_with_null_value_returns_unfiltered_list() throws IOException {
        // given
        homeController.initializeState();
        Genre genre = null;

        // when
        List<Movie> actual = homeController.filterByGenre(homeController.observableMovies, genre);

        // then
        assertEquals(homeController.observableMovies, actual);
    }

    @Test
    void genre_filter_returns_all_movies_containing_given_genre() throws IOException {
        // given
        homeController.initializeState();
        Genre genre = Genre.DRAMA;

        // when
        List<Movie> actual = homeController.filterByGenre(homeController.observableMovies, genre);

        // then
        assertEquals(4, actual.size());
    }

    @Test
    void releaseYear_filter_with_null_value_returns_unfiltered_list(){

        homeController.initializeState();

        List<Movie>actualMovies = homeController.filterByReleaseYear(homeController.observableMovies, "1994");
        List<String>actualTitles = homeController.getTitle(actualMovies);

        List<Movie>expectedMovies = Arrays.asList(
                new Movie(
                        "16f94a79-7804-4d73-bab9-6cf415b30182",
                        "The Shawshank Redemption",
                        Arrays.asList(Genre.DRAMA, Genre.ROMANCE),
                        1994,
                        "Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.",
                        "https://m.media-amazon.com/images/M/MV5BNDE3ODcxYzMtY2YzZC00NmNlLWJiNDMtZDViZWM2MzIxZDYwXkEyXkFqcGdeQXVyNjAwNDUxODI@._V1_.jpg",
                        142,
                        Arrays.asList("Frank Darabont"),
                        Arrays.asList("Stephen King", "Frank Darabont"),
                        Arrays.asList("Tim Robbins", "Morgan Freeman", "Bob Gunton"),
                        9.3
                        ),
                new Movie(
                        "8135fa74-210e-4fe0-885a-45263aa2acd6",
                        "Pulp Fiction",
                        Arrays.asList(Genre.CRIME, Genre.DRAMA),
                        1994,
                        "The lives of two mob hit men, a boxer, a gangster's wife, and a pair of diner bandits intertwine in four tales of violence and redemption.",
                        "https://m.media-amazon.com/images/M/MV5BNGNhMDIzZTUtNTBlZi00MTRlLWFjM2ItYzViMjE3YzI5MjljXkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_.jpg",
                        154,
                        Arrays.asList("Quentin Tarantino"),
                        Arrays.asList("Quentin Tarantino", "Roger Avary"),
                        Arrays.asList("John Travolta", "Uma Thurman", "Samuel L. Jackson"),
                        8.9
                        ),
                new Movie(
                        "e5ba4b50-be0a-489a-a2e5-d4b5466f018c",
                        "Forrest Gump",
                        Arrays.asList(Genre.DRAMA, Genre.ROMANCE),
                        1994,
                        "The presidencies of Kennedy and Johnson, the Vietnam War, the Watergate scandal and other historical events unfold from the perspective of an Alabama man with an IQ of 75, whose only desire is to be reunited with his childhood sweetheart.",
                        "https://m.media-amazon.com/images/M/MV5BNWIwODRlZTUtY2U3ZS00Yzg1LWJhNzYtMmZiYmEyNmU1NjMzXkEyXkFqcGdeQXVyMTQxNzMzNDI@._V1_FMjpg_UX1000_.jpg",
                        144,
                        Arrays.asList("Robert Zemeckis"),
                        Arrays.asList("Eric Roth", "Winston Groom"),
                        Arrays.asList("Tom Hanks", "Robin Wright", "Gary Sinise"),
                        8.8
                        ),
                new Movie(
                        "5f74d045-786c-4e17-a0a7-d6569fe634c1",
                        "The Lion King",
                        Arrays.asList(Genre.ANIMATION, Genre.ADVENTURE, Genre.DRAMA, Genre.FAMILY, Genre.MUSICAL),
                        1994,
                        "Lion cub and future king Simba searches for his identity. His eagerness to please others and penchant for testing his boundaries sometimes gets him into trouble.",
                        "https://m.media-amazon.com/images/M/MV5BMjIwMjE1Nzc4NV5BMl5BanBnXkFtZTgwNDg4OTA1NzM@._V1_FMjpg_UX1000_.jpg",
                        88,
                        Arrays.asList("Roger Allers", "Rob Minkoff"),
                        Arrays.asList("Irene Mecchi", "Jonathan Roberts", "Linda Woolverton"),
                        Arrays.asList("Matthew Broderick", "Jeremy Irons", "James Earl Jones"),
                        8.5
                )
        );

        List<String>expectedTitles = homeController.getTitle(expectedMovies);

        assertEquals(expectedTitles, actualTitles);

    }

    @Test
    void no_filtering_ui_if_empty_query_or_no_genre_and_no_releaseYear_is_set() throws IOException {
        // given
        homeController.initializeState();

        // when
        homeController.applyAllFilters("", null);

        // then
        assertEquals(homeController.allMovies, homeController.observableMovies);
    }

    @Test
    public void test_Amount_of_Movies_by_GetMoviesBetweenYears() {
        // given; list of movies from API for testing
        List<Movie> movies = Movie.initializeMoviesFromAPI();
        HomeController controller = new HomeController();

        // when
        List<Movie> result = controller.getMoviesBetweenYears(movies, 1995, 1997);

        // then
        assertEquals(4, result.size()); // Expecting 2 movies between 1995 and 2015

    }
    @Test
    public void test_4_explicit_Movies_from_GetMoviesBetweenYears() {
        // given; list of movies from API for testing
        List<Movie> movies = Movie.initializeMoviesFromAPI();
        HomeController controller = new HomeController();

        // when
        List<Movie> result = controller.getMoviesBetweenYears(movies, 1995, 1997);

        // then
        assertTrue( result.stream().anyMatch(movie -> movie.getTitle().contains("Life Is Beautiful")));
        assertTrue( result.stream().anyMatch(movie -> movie.getTitle().contains("Seven")));
        assertTrue( result.stream().anyMatch(movie -> movie.getTitle().contains("The Usual Suspects")));
        assertTrue( result.stream().anyMatch(movie -> movie.getTitle().contains("Toy Story")));
    }

    @Test
    public void test_getLongestMovieTitle() {
        // given; list of movies from API for testing
        List<Movie> movies = Movie.initializeMoviesFromAPI();
        HomeController controller = new HomeController();

        // when
        int result = controller.getLongestMovieTitle(movies);

        // then
        assertEquals(result , 46);

    }

    @Test
    public void getTitle_method_returns_array_of_titles_as_strings(){
        List<Movie>movies = Arrays.asList(
                new Movie(
                        "Avatar",
                        "A paraplegic Marine dispatched to the moon Pandora on a unique mission becomes torn between following his orders and protecting the world he feels is his home.",
                        Arrays.asList(Genre.ANIMATION, Genre.DRAMA, Genre.ACTION)),
                new Movie(
                        "Life Is Beautiful",
                        "When an open-minded Jewish librarian and his son become victims of the Holocaust, he uses a perfect mixture of will, humor, and imagination to protect his son from the dangers around their camp." ,
                        Arrays.asList(Genre.DRAMA, Genre.ROMANCE)),
                new Movie(
                        "Puss in Boots",
                        "An outlaw cat, his childhood egg-friend, and a seductive thief kitty set out in search for the eggs of the fabled Golden Goose to clear his name, restore his lost honor, and regain the trust of his mother and town.",
                        Arrays.asList(Genre.COMEDY, Genre.FAMILY, Genre.ANIMATION)),
                new Movie(
                        "The Usual Suspects",
                        "A sole survivor tells of the twisty events leading up to a horrific gun battle on a boat, which begin when five criminals meet at a seemingly random police lineup.",
                        Arrays.asList(Genre.CRIME, Genre.DRAMA, Genre.MYSTERY)),
                new Movie(
                        "The Wolf of Wall Street",
                        "Based on the true story of Jordan Belfort, from his rise to a wealthy stock-broker living the high life to his fall involving crime, corruption and the federal government.",
                        Arrays.asList(Genre.DRAMA, Genre.ROMANCE, Genre.BIOGRAPHY))
        );

        List<String>actual = homeController.getTitle(movies);

        List<String>expected = Arrays.asList("Avatar", "Life Is Beautiful", "Puss in Boots", "The Usual Suspects", "The Wolf of Wall Street");

        assertEquals(expected, actual);
    }

}
