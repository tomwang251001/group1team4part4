package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.database.MovieEntity;
import at.ac.fhcampuswien.fhmdb.database.MovieRepository;
import at.ac.fhcampuswien.fhmdb.database.WatchlistMovieEntity;
import at.ac.fhcampuswien.fhmdb.database.WatchlistRepository;
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
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.IntStream;



public class WatchlistController extends HomeController {
    @FXML
    public JFXButton searchBtn;
    @FXML
    public JFXButton clearBtn;

    @FXML
    public TextField searchField;

    @FXML
    public JFXListView movieListView;

    @FXML
    public JFXComboBox genreComboBox;

    @FXML
    public JFXComboBox<Integer> releaseYearComboBox;
    @FXML
    public JFXComboBox ratingComboBox;
    @FXML
    public JFXButton sortBtn;
    @FXML
    public BorderPane mainPane;
    protected ObservableList<Movie> observableMovies = FXCollections.observableArrayList();

    protected SortedState sortedState;
    WatchlistRepository watchlistRepository = new WatchlistRepository();
    MovieRepository movieRepository = new MovieRepository();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeLayout();
        initializeState();
    }

    public void loadHomePage(){
        setContentView("home-view.fxml");
    }
    @Override
    public void initializeState() {
        try {
            List<MovieEntity> movies = movieRepository.IdToMovie(watchlistRepository.getWatchlist());
            allMovies = MovieEntity.toMovies(movies);
            System.out.println("success");
        }catch(SQLException sqle){
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
                return new WatchlistCell();
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
        ratingComboBox.getItems().addAll("No filter",1,2,3,4,5,6,7,8,9);

    }

    @Override
    public void searchBtnClicked(ActionEvent actionEvent) {
        super.searchBtnClicked(actionEvent);
    }

    @Override
    public void clearBtnClicked(ActionEvent actionEvent) {
        super.clearBtnClicked(actionEvent);
    }

    @Override
    public void applyAllFilters(String searchQuery, Object genre, Object releaseYear, int rating) {
        super.applyAllFilters(searchQuery, genre, releaseYear, rating);
    }

    @Override
    public void sortBtnClicked(ActionEvent actionEvent) {
        super.sortBtnClicked(actionEvent);
    }

    @Override
    String getMostPopularActor(List<Movie> movies) {
        return super.getMostPopularActor(movies);
    }

    @Override
    int getLongestMovieTitle(List<Movie> movies) {
        return super.getLongestMovieTitle(movies);
    }

    @Override
    long countMoviesFrom(List<Movie> movies, String director) {
        return super.countMoviesFrom(movies, director);
    }

    @Override
    public List<Movie> getMoviesBetweenYears(List<Movie> movies, int startYear, int endYear) {
        return super.getMoviesBetweenYears(movies, startYear, endYear);
    }

    @Override
    public List<String> getTitle(List<Movie> movies) {
        return super.getTitle(movies);
    }

    @Override
    public ObservableList<Movie> getObservableMovies() {
        return observableMovies;
    }

    @Override
    public void sortMovies() {
        super.sortMovies();
    }

    @Override
    public void setContentView(String pathToView) {
        super.setContentView(pathToView);
    }

    @Override
    public void loadWatchlist() {
        super.loadWatchlist();
    }

    @Override
    public void sortMovies(SortedState sortDirection) {
        super.sortMovies(sortDirection);
    }
}

