package at.ac.fhcampuswien.fhmdb.database;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;
import com.j256.ormlite.stmt.Where;

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

        if (dao.queryForMatching(movie).size() == 0){
            System.out.println("hi");
            return dao.create(movie);
        }else {
            System.out.println("Movie already exists in Watchlist");
            return 0;
        }
    }

    public int removeFromWatchlist(String apiId) throws SQLException{
        QueryBuilder<WatchlistMovieEntity, Long> queryBuilder = dao.queryBuilder();
        Where<WatchlistMovieEntity, Long> where = queryBuilder.where();
        SelectArg selectArg = new SelectArg();
        where.eq("apiId", selectArg);
        PreparedQuery<WatchlistMovieEntity> preparedQuery = queryBuilder.prepare();
        selectArg.setValue(apiId);

        WatchlistMovieEntity movie;
        movie = dao.queryForFirst(preparedQuery);
        return dao.delete(movie);
    }

    public WatchlistRepository(){
        this.dao = Database.getDatabase().getWatchlistDao();
    }

}
