package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.ui.MovieCell;
import at.ac.fhcampuswien.fhmdb.ui.WatchlistCell;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class WatchlistController extends HomeController {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void loadHomePage(){
        setContentView("home-view.fxml");
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
}
