package at.ac.fhcampuswien.fhmdb.pattern.state;

import at.ac.fhcampuswien.fhmdb.HomeController;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import javafx.collections.ObservableList;


public interface State {
    String getBtnTxt();
    void changeState(HomeController controller);
    ObservableList<Movie> sort(ObservableList<Movie> observableMovies);
}
