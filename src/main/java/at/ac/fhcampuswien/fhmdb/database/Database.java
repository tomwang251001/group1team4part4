package at.ac.fhcampuswien.fhmdb.database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class Database {
    public static final String DB_URL= "jdbc:h2:file: ./db/moviedb";
    private static final String username = "user";
    private static final String password = "password";
    private static ConnectionSource conn;
    Dao<MovieEntity, Long> movieDao;

    Dao<WatchlistMovieEntity, Long> watchlistDao;

    private static Database instance;

    private Database(){
        try {
            createConnectionSource();
            createTables();
            movieDao = DaoManager.createDao(conn, MovieEntity.class);
        }catch (SQLException sqle){
            System.out.println("SQLException: " + sqle.getMessage());
        }
    }
    public static Database getDatabase(){
        if(instance == null){
            instance = new Database();
        }
        return instance;
    }

    public void testDB() throws SQLException {
        MovieEntity movie = new MovieEntity("Title 1", "Desc", "GENRE", 1999, "URLURLURL", 180, 9.1 );
        movieDao.create(movie);
    }

    //TODO methods
    public void createConnectionSource() throws SQLException {
        //TODO method
        conn = new JdbcConnectionSource(DB_URL, username, password);
    }

    public ConnectionSource getConnectionSource(){
         return conn;
    }

    public void createTables(){
        //TODO method
        try {
            TableUtils.createTableIfNotExists(conn, MovieEntity.class);
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }
    }

    /*
    public WatchlistDao getWatchlistDao(){

        //TODO method
        return null;
    }
    */
    /*
    public MovieDao getMovieDao(){
        //TODO method
        return null;
    }
    */
}
