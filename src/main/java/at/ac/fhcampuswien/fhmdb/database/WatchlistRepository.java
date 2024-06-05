package at.ac.fhcampuswien.fhmdb.database;

import at.ac.fhcampuswien.fhmdb.Exceptions.DatabaseException;
import at.ac.fhcampuswien.fhmdb.HomeController;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.pattern.observer.Observable;
import at.ac.fhcampuswien.fhmdb.pattern.observer.Observer;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;
import com.j256.ormlite.stmt.Where;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class WatchlistRepository implements Observable {
    public List<Observer> observers;

    public WatchlistRepository(){
        observers = new ArrayList<>();
        try {
            this.dao = Database.getDatabase().getWatchlistDao();
        }catch (DatabaseException dbe){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Error establishing database connection", ButtonType.OK);
            errorAlert.show();
        }
    }


    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
        System.out.println("Observer registered: " + observer);
        System.out.println("Current observers: " + observers.size());
    }
    @Override
    public void unregisterObserver(Observer observer){
        observers.remove(observer);
    }
    @Override
    public void notifyObservers(String msg){
        System.out.println(observers.size());
        for (Observer observer : observers) {
            observer.update(msg);
        }
    }

    Dao<WatchlistMovieEntity, Long> dao;

    public List<WatchlistMovieEntity> getWatchlist() throws DatabaseException {
        try {
            return dao.queryForAll();
        } catch (SQLException swqle) {
            throw new DatabaseException();
        }

    }

    public int addToWatchlist(WatchlistMovieEntity movie) throws DatabaseException {
        try {
            if (dao.queryForMatching(movie).size() == 0) {
                notifyObservers("added movie");
                return dao.create(movie);
            } else {
                notifyObservers("already in watchlist");
                return 0;
            }
        } catch (SQLException sqle) {
            throw new DatabaseException("error in addToWatchlist()", sqle);
        }

    }

    public int removeFromWatchlist(String apiId) throws DatabaseException {
        notifyObservers("removed from watchlist");
        try {
            QueryBuilder<WatchlistMovieEntity, Long> queryBuilder = dao.queryBuilder();
            Where<WatchlistMovieEntity, Long> where = queryBuilder.where();
            SelectArg selectArg = new SelectArg();
            where.eq("apiId", selectArg);
            PreparedQuery<WatchlistMovieEntity> preparedQuery = queryBuilder.prepare();
            selectArg.setValue(apiId);

            WatchlistMovieEntity movie;
            movie = dao.queryForFirst(preparedQuery);
            return dao.delete(movie);
        } catch (SQLException sqle) {
            throw new DatabaseException("error in removeFromWatchlist()", sqle);
        }
    }


}
