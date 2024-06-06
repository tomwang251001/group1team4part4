package at.ac.fhcampuswien.fhmdb.pattern.state;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import javafx.collections.ObservableList;

public class ManageState {
    private DefaultState currentState;

    public ManageState() {
        this.currentState = new UnsortedState();
    }

    public DefaultState getCurrentState() {
        return this.currentState;
    }

    public void setCurrentState(DefaultState newState) {
        this.currentState = newState;
    }

    public ObservableList<Movie> sort(ObservableList<Movie> observableMovies){
        return currentState.sort(observableMovies);
    }
}