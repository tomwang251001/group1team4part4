package at.ac.fhcampuswien.fhmdb.database;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MovieRepository {
    Dao<MovieEntity, Long> dao;

    public java.awt.List getAllMovies() throws SQLException {
        return dao.queryForAll();
    }

    public int removeAll() throws SQLException{
        Collection<MovieEntity> moviesE = new ArrayList<>();
        for (MovieEntity movie : getAllMovies()){
            moviesE.add(movie);
        }
        return dao.delete(moviesE);
    }

    public MovieEntity getMovie() throws SQLException{
        return dao.queryForFirst();
    }

    public int addAllMovies(List<Movie> movies) throws SQLException {
        Collection<MovieEntity> moviesE = new ArrayList<>();

        for (Movie movie : movies){
            moviesE.add(new MovieEntity(movie.id, movie.title, movie.description, movie.genres, movie.releaseYear, movie.imgUrl, movie.lengthInMinutes, movie.rating));
        }
        return dao.create(moviesE);
    }
    public MovieRepository(){
        this.dao = Database.getDatabase().getMovieDao();
    }
}
