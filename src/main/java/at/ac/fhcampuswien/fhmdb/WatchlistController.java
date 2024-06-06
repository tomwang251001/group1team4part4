package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.Exceptions.DatabaseException;
import at.ac.fhcampuswien.fhmdb.database.*;
import at.ac.fhcampuswien.fhmdb.models.ClickEventHandler;
import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.models.SortedState;
import at.ac.fhcampuswien.fhmdb.pattern.observer.Observer;
import at.ac.fhcampuswien.fhmdb.ui.MovieCell;
import at.ac.fhcampuswien.fhmdb.ui.WatchlistCell;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
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



public class WatchlistController extends HomeController implements Observer {
    WatchlistRepository watchlistRepository = WatchlistRepository.getInstance();
    MovieRepository movieRepository = MovieRepository.getMovieRepository();
    @Override
    public void update(String msg){
        if (msg == "Movie removed from Watchlist"){
            Alert errorAlert = new Alert(Alert.AlertType.CONFIRMATION, msg, ButtonType.OK);
            errorAlert.show();
        }
    };


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
        } catch (DatabaseException dbe) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Error loading watch-list", ButtonType.OK);
            errorAlert.show();
        }
        observableMovies.clear();
        observableMovies.addAll(allMovies); // add all movies to the observable list
        sortedState = SortedState.NONE;
    }

    public void initializeLayout() {
        movieListView.setItems(observableMovies);   // set the items of the listview to the observable list
        movieListView.setCellFactory(movieListView -> new WatchlistCell(onDeleteFromWatchlistClicked)); // apply custom cells to the listview
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
            }catch (DatabaseException dbe){
                Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Error removing movie", ButtonType.OK);
                errorAlert.show();
            }
        }
    loadWatchlist();
    };

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


