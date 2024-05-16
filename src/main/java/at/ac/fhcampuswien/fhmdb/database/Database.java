package at.ac.fhcampuswien.fhmdb.database;

import at.ac.fhcampuswien.fhmdb.Exceptions.DatabaseException;
import at.ac.fhcampuswien.fhmdb.models.Genre;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.sql.Array;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Database {
    public static final String DB_URL= "jdbc:h2:file: ./db/moviedb";
    private static final String username = "user";
    private static final String password = "password";
    private static ConnectionSource conn;
    private Dao<MovieEntity, Long> movieDao;

    Dao<WatchlistMovieEntity, Long> watchlistDao;

    private static Database instance;

    private Database() throws DatabaseException{
        createConnectionSource();
        createTables();
        try {
            movieDao = DaoManager.createDao(conn, MovieEntity.class);
            watchlistDao = DaoManager.createDao(conn, WatchlistMovieEntity.class);
        }catch (SQLException sqle){
            throw new DatabaseException("error in creating dao", sqle);
        }
    }
    public static Database getDatabase() throws DatabaseException {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    /*public void testDB() throws SQLException {
        MovieEntity movie = new MovieEntity("a1","Title 1", "Desc", Arrays.asList(Genre.DOCUMENTARY), 1999, "URLURLURL", 180, 9.1 );
        movieDao.create(movie);
    }*/

    public void createConnectionSource() throws DatabaseException {
        try {
            conn = new JdbcConnectionSource(DB_URL, username, password);
        }catch (SQLException sqle){
            throw new DatabaseException("error in createConnectionSource()", sqle);
        }
    }

    public ConnectionSource getConnectionSource(){
         return conn;
    }

    public void createTables() throws DatabaseException {
        try {
            TableUtils.createTableIfNotExists(conn, MovieEntity.class);
            //needed for WatchlistMovieEntity table
            TableUtils.createTableIfNotExists(conn, WatchlistMovieEntity.class);
        }catch (SQLException sqle){
            throw new DatabaseException("error in createTables()", sqle);
        }
    }

    public Dao<MovieEntity, Long> getMovieDao() {
        return this.movieDao;
    }

    public Dao<WatchlistMovieEntity, Long> getWatchlistDao() {
        return this.watchlistDao;
    }
}
