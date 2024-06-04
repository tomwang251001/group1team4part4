package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.API.MovieAPI;
import at.ac.fhcampuswien.fhmdb.Exceptions.DatabaseException;
import at.ac.fhcampuswien.fhmdb.Exceptions.MovieApiException;
import at.ac.fhcampuswien.fhmdb.database.*;
import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.models.SortedState;
import at.ac.fhcampuswien.fhmdb.pattern.observer.Observer;
import at.ac.fhcampuswien.fhmdb.pattern.state.ascendingState;
import at.ac.fhcampuswien.fhmdb.pattern.state.defaultState;
import at.ac.fhcampuswien.fhmdb.pattern.state.descendingState;
import at.ac.fhcampuswien.fhmdb.pattern.state.manageState;
import at.ac.fhcampuswien.fhmdb.ui.MovieCell;
import com.google.gson.Gson;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import at.ac.fhcampuswien.fhmdb.models.ClickEventHandler;

public class HomeController implements Initializable, Observer {
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

    public static List<Movie> allMovies;

    protected ObservableList<Movie> observableMovies = FXCollections.observableArrayList();

    protected SortedState sortedState;
    protected manageState defaultSortState;

    MovieRepository movieRepository = new MovieRepository();
    MovieEntity movieEntity = new MovieEntity();

    public void update(){};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeState();
        initializeLayout();
    }

    public void initializeState() {
        try {
            allMovies = Movie.initializeMoviesFromAPI();
        }catch (MovieApiException mae){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Connection to API failed. Load movies from database", ButtonType.OK);
            errorAlert.show();
            try {
                allMovies = MovieEntity.toMovies(movieRepository.getAllMovies());
            }catch (DatabaseException dbe){
                Alert errorAlert2 = new Alert(Alert.AlertType.ERROR, "Movies failed to load", ButtonType.OK);
                errorAlert2.show();
            }
        }

        observableMovies.clear();
        observableMovies.addAll(allMovies); // add all movies to the observable list
        sortedState = SortedState.NONE;

    }

    public void initializeLayout() {
        movieListView.setItems(observableMovies);   // set the items of the listview to the observable list
            movieListView.setCellFactory(movieListView ->  new MovieCell(onAddToWatchlistClicked)); // apply custom cells to the listview
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
    protected List<Movie> getObservableMovies() {
        return observableMovies;
    }

    public void sortMovies(){
        if (sortedState == SortedState.NONE || sortedState == SortedState.DESCENDING) {
            sortMovies(SortedState.ASCENDING);
        } else if (sortedState == SortedState.ASCENDING) {
            sortMovies(SortedState.DESCENDING);
        }
    }

    public void setContentView(String pathToView){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(pathToView));

        try{
            mainPane.setCenter(fxmlLoader.load());
        } catch (IOException e){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Error loading view", ButtonType.OK);
            errorAlert.show();
        }
    }

    public void loadWatchlist(){
        setContentView("watchlist-view.fxml");
    }

    private final ClickEventHandler onAddToWatchlistClicked = (clickedItem) ->
    {
        if (clickedItem instanceof Movie movie){
            WatchlistMovieEntity watchlistMovieEntity = new WatchlistMovieEntity(
                    movie.getId()
            );

            WatchlistRepository watchlistRepository = new WatchlistRepository();
            try {
                watchlistRepository.addToWatchlist(watchlistMovieEntity);
            }catch (DatabaseException dbe){
                Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Error adding movie to watchlist", ButtonType.OK);
                errorAlert.show();
            }

        }
    };
    // sort movies based on sortedState
    // by default sorted state is NONE
    // afterwards it switches between ascending and descending
    public void sortMovies(SortedState sortDirection) {
        if (sortDirection == SortedState.ASCENDING) {
            observableMovies.sort(Comparator.comparing(Movie::getTitle));
            sortedState = SortedState.ASCENDING;
        } else {
            observableMovies.sort(Comparator.comparing(Movie::getTitle).reversed());
            sortedState = SortedState.DESCENDING;
        }
    }
    //TODO try this in Code for GUI
    public ObservableList<Movie> sortMovies(ObservableList<Movie> observableMovies, SortedState sortDirection){
        if (sortDirection == SortedState.ASCENDING) {
            defaultSortState.setCurrentState(new ascendingState());
        } else {
            defaultSortState.setCurrentState(new descendingState());
        }
        return defaultSortState.sort(observableMovies);
    }

    public void applyAllFilters(String searchQuery, Object genre, Object releaseYear, int rating) {
        List<Movie> filteredMovies;


        String genrefilter = null;
        try{
            if (!(genre.toString().equals("No filter") || genre.toString().equals("Filter by Genre"))){
                genrefilter = genre.toString();
            } else {genre = null;}
        }catch(Exception e){}


        int year = 0;
        try {
            year = Integer.parseInt(releaseYear.toString());
        } catch (Exception e) {
            year = 0;
        }

        double ratingfilter = 0;
        try {
            ratingfilter = rating;
        } catch (Exception e) {
            rating = -1;
        }


        Gson gson = new Gson();
        try {
            filteredMovies = List.of(gson.fromJson(MovieAPI.searchMovies(searchQuery, genrefilter, year, ratingfilter), Movie[].class));
            observableMovies.clear();
            observableMovies.addAll(filteredMovies);
        }catch (MovieApiException mae){
            filteredMovies = filterByQuery(allMovies, searchQuery);
            filteredMovies = filterByGenre(filteredMovies, (Genre) genre);
            if (releaseYear != null) {
                filteredMovies = filterByReleaseYear(filteredMovies, releaseYear.toString());
            }
            filteredMovies = getMoviesByRating(filteredMovies, rating);
            observableMovies.clear();
            observableMovies.addAll(filteredMovies);
        }

    }

    public void searchBtnClicked(ActionEvent actionEvent) {
        String searchQuery = searchField.getText().trim().toLowerCase();
        Object genre = genreComboBox.getSelectionModel().getSelectedItem();
        Object releaseYear = releaseYearComboBox.getValue();

        int rating = 0;
        if (ratingComboBox.getValue() != null && !"No Filter".equals(ratingComboBox.getSelectionModel().getSelectedItem())) {
            // Cast the selected item to Integer
            rating = (Integer) ratingComboBox.getSelectionModel().getSelectedItem();
        }

        applyAllFilters(searchQuery, genre, releaseYear, rating);
        sortMovies(sortedState);
    }

    public void clearBtnClicked(ActionEvent actionEvent){
        genreComboBox.setValue(null);
        releaseYearComboBox.setValue(null);
        ratingComboBox.setValue(null);
        searchField.clear();

        observableMovies.clear();
        observableMovies.addAll(allMovies);
    }

    public void sortBtnClicked(ActionEvent actionEvent) {
        sortMovies();
    }

    String getMostPopularActor(List<Movie> movies){

        return movies.stream()
                .flatMap(movie -> movie.getMainCast().stream())
                .collect(Collectors.groupingBy(actor -> actor, Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("No main cast found");
    }

    int getLongestMovieTitle(List<Movie> movies){
        return movies.stream()
                .mapToInt(movie -> movie.getTitle().length())
                .max()
                .orElse(0);
    }

    long countMoviesFrom(List<Movie> movies, String director){

        return movies.stream()
                .filter(movie -> movie
                        .getDirectors().contains(director)).count();
    }

    public List<Movie> getMoviesBetweenYears(List<Movie> movies, int startYear, int endYear){
        return movies.stream()
                .filter(movie -> movie
                .getReleaseYear() >= startYear && movie
                .getReleaseYear() <= endYear)
                .collect(Collectors.toList());
    }



    public List<String> getTitle(List<Movie> movies){
        return movies.stream()
                .map(Movie::getTitle)
                .toList();
    }

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

}