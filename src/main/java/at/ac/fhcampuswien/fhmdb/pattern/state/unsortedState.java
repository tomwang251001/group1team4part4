package at.ac.fhcampuswien.fhmdb.pattern.state;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import javafx.collections.ObservableList;

public class unsortedState implements defaultState{
    @Override
    public ObservableList<Movie> sort(ObservableList<Movie> observableMovies) {
        return observableMovies;
    }
}
