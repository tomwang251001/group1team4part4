package at.ac.fhcampuswien.fhmdb.database;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

public class MovieRepository {
    Dao<MovieEntity, Long> dao;

    public List<MovieEntity> getAllMovies() throws SQLException {
        return dao.queryForAll();
    }

    public int removeAll(){
        //TODO methode
        return 0;
    }

    public MovieEntity getMovie(){

        //TODO methode
        return null;
    }

    public int addAllMovies(List<Movie> movies){
        //TODO methode
        return 0;
    }
    public MovieRepository(){
        this.dao = Database.getDatabase().getMovieDao();
    }
}
