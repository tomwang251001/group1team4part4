package at.ac.fhcampuswien.fhmdb.pattern.state;

import at.ac.fhcampuswien.fhmdb.HomeController;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import javafx.collections.ObservableList;

public class manageState {
    private State currentState;

    public manageState() {
        this.currentState = new unsortedState();
    }

    public State getCurrentState() {
        return this.currentState;
    }

    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }

    public ObservableList<Movie> sort(ObservableList<Movie> observableMovies){
        return currentState.sort(observableMovies);
    }
}