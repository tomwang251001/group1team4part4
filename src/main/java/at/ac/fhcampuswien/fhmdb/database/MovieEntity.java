package at.ac.fhcampuswien.fhmdb.database;
import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.List;

@DatabaseTable(tableName = "movie")
public class MovieEntity {
    @DatabaseField(generatedId = true)
    private long id;
    @DatabaseField()
    private String apiId;
    @DatabaseField()
    private String title;
    @DatabaseField()
    private String description;
    @DatabaseField()
    private String genres;
    @DatabaseField()
    private int releaseYear;
    @DatabaseField()
    private String imgUrl;
    @DatabaseField()
    private int lengthInMinutes;
    @DatabaseField()
    private double rating;

    public MovieEntity(String title, String description, String genres, int releaseYear, String imgUrl, int lengthInMinutes, double rating) {
        this.title = title;
        this.description = description;
        this.genres = genres;
        this.releaseYear = releaseYear;
        this.imgUrl = imgUrl;
        this.lengthInMinutes = lengthInMinutes;
        this.rating = rating;
    }

    public MovieEntity() {
        //no-argument constructor is needed so the object can be returned by a query
    }

    public static String genresToString(List<Genre> genres){
        //TODO write methode
        return null;
    }
    public static List<MovieEntity> fromMovies(List<Movie> movies){
        //TODO write methode
        return null;
    }
    public static List<Movie>toMovies(List<MovieEntity> movieEntities){
        //TODO write methode
        return null;
    }
}
