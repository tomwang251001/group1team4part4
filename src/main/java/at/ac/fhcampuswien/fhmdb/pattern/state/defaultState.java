package at.ac.fhcampuswien.fhmdb.pattern.state;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import javafx.collections.ObservableList;

public interface defaultState {
    ObservableList<Movie> sort(ObservableList<Movie> observableMovies);
}
