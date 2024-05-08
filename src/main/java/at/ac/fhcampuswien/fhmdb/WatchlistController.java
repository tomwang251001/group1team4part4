package at.ac.fhcampuswien.fhmdb;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class WatchlistController extends HomeController {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void loadHomePage(){
        setContentView("home-view.fxml");
    }

}
