package at.ac.fhcampuswien.fhmdb.database;

import at.ac.fhcampuswien.fhmdb.Exceptions.DatabaseException;
import at.ac.fhcampuswien.fhmdb.pattern.observer.Observable;
import at.ac.fhcampuswien.fhmdb.pattern.observer.Observer;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;
import com.j256.ormlite.stmt.Where;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WatchlistRepository implements Observable {
    public List<Observer> observers;
    Dao<WatchlistMovieEntity, Long> dao;
    private static WatchlistRepository instance;

    private WatchlistRepository() throws DatabaseException {
        this.dao = Database.getDatabase().getWatchlistDao();
        this.observers = new ArrayList<>();
    }

    public static WatchlistRepository getWatchlistRepository() throws DatabaseException {
        if (instance == null) {
            instance = new WatchlistRepository();

        }
        return instance;
    }
        public List<WatchlistMovieEntity> getWatchlist () throws DatabaseException {
            try {
                return dao.queryForAll();
            } catch (SQLException sqle) {
                throw new DatabaseException();
            }
        }
    
        public void addToWatchlist(WatchlistMovieEntity movie) throws DatabaseException {
            try {
                if (dao.queryForMatching(movie).isEmpty()) {
                    notifyObservers("added movie");
                    dao.create(movie);
                } else {
                    notifyObservers("already in watchlist");
                }
            } catch (SQLException sqle) {
                throw new DatabaseException("error in addToWatchlist()", sqle);
            }
        }

        public void removeFromWatchlist (String apiId) throws DatabaseException {
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
                dao.delete(movie);
                notifyObservers("movie deleted");
            } catch (SQLException sqle) {
                throw new DatabaseException("error in removeFromWatchlist()", sqle);
            }
        }


    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
        System.out.println("Observer registered: " + observer);
        System.out.println("Current observers: " + observers.size());
    }

    @Override
    public void unregisterObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String msg) {
        System.out.println(observers.size());
        for (Observer observer : observers) {
            observer.update(msg);
        }
    }
}
