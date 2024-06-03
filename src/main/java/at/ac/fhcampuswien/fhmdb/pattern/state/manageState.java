package at.ac.fhcampuswien.fhmdb.pattern.state;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import javafx.collections.ObservableList;

public class manageState {
    private defaultState currentState;

    public manageState() {
        this.currentState = new unsortedState();
    }

    public defaultState getCurrentState() {
        return this.currentState;
    }

    public void setCurrentState(defaultState currentState) {
        this.currentState = currentState;
    }

    public ObservableList<Movie> sort(ObservableList<Movie> observableMovies){
        return currentState.sort(observableMovies);
    }
}