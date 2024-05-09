package at.ac.fhcampuswien.fhmdb.database;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

public class WatchlistRepository {

    Dao<WatchlistMovieEntity, Long> dao;

    public List<WatchlistMovieEntity> getWatchlist(){
        //TODO methode
        return null;
    }

    public int addToWatchlist(WatchlistMovieEntity movie){
        //TODO methode
        try {
            dao.create(movie);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return (int)movie.getId();
    }

    public int removeFromWatchlist(String apiId){

        //TODO methode
        //dao.delete();
        return 0;
    }
}
