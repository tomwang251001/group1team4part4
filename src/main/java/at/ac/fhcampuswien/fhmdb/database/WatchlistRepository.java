package at.ac.fhcampuswien.fhmdb.database;

import at.ac.fhcampuswien.fhmdb.Exceptions.DatabaseException;
import at.ac.fhcampuswien.fhmdb.models.Movie;
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
import java.util.List;

public class WatchlistRepository {

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
                return dao.create(movie);
            } else {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Movie already exists in database", ButtonType.OK);
                errorAlert.show();
                return 0;
            }
        } catch (SQLException sqle) {
            throw new DatabaseException("error in addToWatchlist()", sqle);
        }

    }

    public int removeFromWatchlist(String apiId) throws DatabaseException {
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
    public WatchlistRepository(){
        try {
            this.dao = Database.getDatabase().getWatchlistDao();
        }catch (DatabaseException dbe){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Error establishing database connection", ButtonType.OK);
            errorAlert.show();
        }
    }

}
