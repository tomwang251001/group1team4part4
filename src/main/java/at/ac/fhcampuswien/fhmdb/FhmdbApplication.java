package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.Exceptions.DatabaseException;
import at.ac.fhcampuswien.fhmdb.database.Database;
import at.ac.fhcampuswien.fhmdb.database.MovieRepository;
import at.ac.fhcampuswien.fhmdb.pattern.factory.MyFactory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.util.Objects;

public class FhmdbApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        MyFactory myFactory = new MyFactory();
        FXMLLoader fxmlLoader = new FXMLLoader(FhmdbApplication.class.getResource("home-view.fxml"));
        fxmlLoader.setControllerFactory(myFactory);
        Scene scene = new Scene(fxmlLoader.load(), 890, 620);
        scene.getStylesheets().add(Objects.requireNonNull(FhmdbApplication.class.getResource("styles.css")).toExternalForm());
        stage.setTitle("FHMDb!");
        stage.setScene(scene);
        stage.show();

        try {
            Database.getDatabase();
        }catch (DatabaseException dbe){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Error establishing database connection", ButtonType.OK);
            errorAlert.show();
        }

        try {
            if (Database.getDatabase() != null) {
                MovieRepository movieRepository = MovieRepository.getMovieRepository();
                movieRepository.removeAll();
                movieRepository.addAllMovies(HomeController.allMovies);
            }
        }catch(DatabaseException dbe){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Error loading movies", ButtonType.OK);
            errorAlert.show();
        }
    }

    public static void main(String[] args) { launch();}
}