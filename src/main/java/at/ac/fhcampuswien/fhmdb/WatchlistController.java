package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.database.MovieEntity;
import at.ac.fhcampuswien.fhmdb.database.MovieRepository;
import at.ac.fhcampuswien.fhmdb.database.WatchlistMovieEntity;
import at.ac.fhcampuswien.fhmdb.database.WatchlistRepository;
import at.ac.fhcampuswien.fhmdb.models.ClickEventHandler;
import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.models.SortedState;
import at.ac.fhcampuswien.fhmdb.ui.MovieCell;
import at.ac.fhcampuswien.fhmdb.ui.WatchlistCell;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.IntStream;



public class WatchlistController extends HomeController {
    WatchlistRepository watchlistRepository = new WatchlistRepository();
    MovieRepository movieRepository = new MovieRepository();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeLayout();
        initializeState();
    }

    public void loadHomePage() {
        setContentView("home-view.fxml");
    }

    @Override
    public void initializeState() {
        try {
            List<MovieEntity> movies = movieRepository.IdToMovie(watchlistRepository.getWatchlist());
            allMovies = MovieEntity.toMovies(movies);
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        observableMovies.clear();
        observableMovies.addAll(allMovies); // add all movies to the observable list
        sortedState = SortedState.NONE;
    }

    public void initializeLayout() {
        movieListView.setItems(observableMovies);   // set the items of the listview to the observable list
        movieListView.setCellFactory(movieListView -> {
            try {
                return new WatchlistCell(onDeleteFromWatchlistClicked);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }); // apply custom cells to the listview
        Object[] genres = Genre.values();   // get all genres
        genreComboBox.getItems().add("No filter");  // add "no filter" to the combobox
        genreComboBox.getItems().addAll(genres);    // add all genres to the combobox
        genreComboBox.setPromptText("Filter by Genre");

        releaseYearComboBox.setPromptText("Filter by Release Year");
        releaseYearComboBox.getItems().addAll(
                IntStream.rangeClosed(1946, 2023)
                        .boxed()
                        .collect(Collectors.toList())
        );


        ratingComboBox.setPromptText("Filter by Rating");
        ratingComboBox.getItems().addAll("No filter", 1, 2, 3, 4, 5, 6, 7, 8, 9);

    }

    private final ClickEventHandler onDeleteFromWatchlistClicked = (clickedItem) ->
    {
        if (clickedItem instanceof Movie){
            try {
                watchlistRepository.removeFromWatchlist(((Movie) clickedItem).id);
            }catch (SQLException sqle){
                sqle.printStackTrace();
            }
        }
    loadWatchlist();
    };

    public List<Movie> filterByReleaseYear(List<Movie> movies, String releaseYear) {
        if (releaseYear == null) {
            return movies;
        }

        if (movies == null) {
            throw new IllegalArgumentException("movies must not be null");
        }
        ;

        return movies.stream()
                .filter(Objects::nonNull)
                .filter(movie -> movie.getReleaseYear() == Integer.valueOf(releaseYear))
                .toList();
    }

    public List<Movie> filterByGenre(List<Movie> movies, Genre genre) {
        if (genre == null) return movies;

        if (movies == null) {
            throw new IllegalArgumentException("movies must not be null");
        }

        return movies.stream()
                .filter(Objects::nonNull)
                .filter(movie -> movie.getGenres().contains(genre))
                .toList();
    }

    public List<Movie> filterByQuery(List<Movie> movies, String query) {
        if (query == null || query.isEmpty()) return movies;

        if (movies == null) {
            throw new IllegalArgumentException("movies must not be null");
        }

        return movies.stream()
                .filter(Objects::nonNull)
                .filter(movie ->
                        movie.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                                movie.getDescription().toLowerCase().contains(query.toLowerCase())
                )
                .toList();
    }

    public List<Movie> getMoviesByRating(List<Movie> movies, int rating) {
        return movies.stream()
                .filter(movie -> movie
                        .getRating() >= rating)
                .collect(Collectors.toList());
    }

    @Override
    public void applyAllFilters(String searchQuery, Object genre, Object releaseYear, int rating) {
        List<Movie> filteredMovies = new ArrayList<>();
        filteredMovies = filterByQuery(allMovies, searchQuery);
        filteredMovies = filterByGenre(filteredMovies, (Genre) genre);
        if (releaseYear != null) {
            filteredMovies = filterByReleaseYear(filteredMovies, releaseYear.toString());
        }
        filteredMovies = getMoviesByRating(filteredMovies, rating);
        observableMovies.clear();
        observableMovies.addAll(filteredMovies);
    }

    public void clearBtnClicked(ActionEvent actionEvent) {
        genreComboBox.setValue(null);
        releaseYearComboBox.setValue(null);
        ratingComboBox.setValue(null);
        searchField.clear();

        observableMovies.clear();
        observableMovies.addAll(allMovies);
    }
}


