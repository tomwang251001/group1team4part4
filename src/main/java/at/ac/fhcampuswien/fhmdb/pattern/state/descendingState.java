package at.ac.fhcampuswien.fhmdb.pattern.state;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import javafx.collections.ObservableList;

import java.util.Collections;
import java.util.Comparator;

public class descendingState implements defaultState{
    @Override
    public ObservableList<Movie> sort(ObservableList<Movie> observableMovies) {
        observableMovies.sort(Comparator.comparing(Movie::getTitle));
        Collections.reverse(observableMovies);
        return observableMovies;
    }
}
