package at.ac.fhcampuswien.fhmdb.database;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.j256.ormlite.dao.Dao;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WatchlistRepository {

    Dao<WatchlistMovieEntity, Long> dao;

    public List<WatchlistMovieEntity> getWatchlist() throws SQLException{
        return dao.queryForAll();
    }

    public int addToWatchlist(WatchlistMovieEntity movie) throws SQLException{
        return dao.create(movie);
    }

    public int removeFromWatchlist(String apiId) throws SQLException{
        return dao.deleteById(Long.valueOf(apiId));
    }

    public WatchlistRepository(){
        this.dao = Database.getDatabase().getWatchlistDao();
    }

}
