package at.ac.fhcampuswien.fhmdb.pattern.state;

import at.ac.fhcampuswien.fhmdb.HomeController;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import javafx.collections.ObservableList;

import java.util.Comparator;

public class ascendingState implements State {

    String btnTxt = "DES";
    @Override
    public String getBtnTxt(){
        return btnTxt;
    }
    @Override
    public void changeState(HomeController controller){
        controller.changeState(new descendingState());
    }
    @Override
    public ObservableList<Movie> sort(ObservableList<Movie> observableMovies) {
        observableMovies.sort(Comparator.comparing(Movie::getTitle));
        return observableMovies;
    }
}
