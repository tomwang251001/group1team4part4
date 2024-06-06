package at.ac.fhcampuswien.fhmdb.pattern.state;

import at.ac.fhcampuswien.fhmdb.HomeController;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import javafx.collections.ObservableList;

public class unsortedState implements State{

    @Override
    public void changeState(HomeController controller){
        controller.changeState(new ascendingState());
    }

    @Override
    public ObservableList<Movie> sort(ObservableList<Movie> observableMovies) {
        return observableMovies;
    }
}