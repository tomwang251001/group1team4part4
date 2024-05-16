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

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MovieRepository {
    Dao<MovieEntity, Long> dao;

    public List<MovieEntity> getAllMovies() throws DatabaseException {
        try {
            return dao.queryForAll();
        }catch (SQLException sqle){
            throw new DatabaseException("error in getAllMovies()", sqle);
        }
    }

    public int removeAll() throws DatabaseException{
        try {
            Collection<MovieEntity> moviesE = new ArrayList<>();
            for (MovieEntity movie : getAllMovies()){
                moviesE.add(movie);
            }
            return dao.delete(moviesE);
        }catch (SQLException sqle){
            throw new DatabaseException("error in removeAll()", sqle);
        }
    }

    public MovieEntity getMovie() throws DatabaseException{
        try {
            return dao.queryForFirst();
        }catch (SQLException sqle){
            throw new DatabaseException("error in getMovie()", sqle);
        }
    }
    public List<MovieEntity> IdToMovie(List<WatchlistMovieEntity> ids) throws DatabaseException {
        try {
            QueryBuilder<MovieEntity, Long> queryBuilder = dao.queryBuilder();
            Where<MovieEntity, Long> where = queryBuilder.where();
            SelectArg selectArg = new SelectArg();
            where.eq("apiId", selectArg);
            PreparedQuery<MovieEntity> preparedQuery = queryBuilder.prepare();

            List<MovieEntity> movies = new ArrayList<>();
            for (WatchlistMovieEntity id : ids){
                selectArg.setValue(id.getApiId());
                movies.add(dao.queryForFirst(preparedQuery));
            }
            return movies;
        }catch (SQLException sqle){
            throw new DatabaseException("error in IdToMovie()", sqle);
        }
    }

    public int addAllMovies(List<Movie> movies) throws DatabaseException {
        Collection<MovieEntity> moviesE = new ArrayList<>();
        for (Movie movie : movies){
            moviesE.add(new MovieEntity(movie.id, movie.title, movie.description, movie.genres, movie.releaseYear, movie.imgUrl, movie.lengthInMinutes, movie.rating));
        }
        try {
            return dao.create(moviesE);
        }catch (SQLException sqle){
            throw new DatabaseException("error in addAllMovies", sqle);
        }
    }
    public MovieRepository(){
        try {
            this.dao = Database.getDatabase().getMovieDao();
        }catch (DatabaseException dbe){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Error establishing database connection", ButtonType.OK);
            errorAlert.show();
        }

    }
}
