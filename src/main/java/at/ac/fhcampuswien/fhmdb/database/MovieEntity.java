package at.ac.fhcampuswien.fhmdb.database;
import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
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

    public MovieEntity(String apiId, String title, String description, List<Genre> genres, int releaseYear, String imgUrl, int lengthInMinutes, double rating) {
        this.apiId = apiId;
        this.title = title;
        this.description = description;
        this.genres = genresToString(genres);
        this.releaseYear = releaseYear;
        this.imgUrl = imgUrl;
        this.lengthInMinutes = lengthInMinutes;
        this.rating = rating;
    }

    public MovieEntity() {
        //no-argument constructor is needed so the object can be returned by a query
    }

    protected MovieEntity(Movie movie) { //needed additional Constructor for MovieEntity.fromMovies
        this.id = Long.parseLong(movie.getId());
        this.apiId = "";
        this.title = movie.getTitle();
        this.description = movie.getDescription();
        this.genres = this.genresToString(movie.getGenres());
        this.releaseYear = movie.getReleaseYear();
        this.imgUrl = movie.getImgUrl();
        this.lengthInMinutes = movie.getLengthInMinutes();
        this.rating = movie.getRating();

    }

    public long getId() {
        return id;
    }

    public String getApiId() {
        return apiId;
    }

    public void setApiId(String apiId) {
        this.apiId = apiId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getLengthInMinutes() {
        return lengthInMinutes;
    }

    public void setLengthInMinutes(int lengthInMinutes) {
        this.lengthInMinutes = lengthInMinutes;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public static String genresToString(List<Genre> genres){
        if (genres.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (Genre genre : genres) {
            sb.append(genre).append(", ");
        }
        sb.deleteCharAt(sb.length() - 2);
        return sb.toString();
    }
    public static List<MovieEntity> fromMovies(List<Movie> movies){
        ArrayList<MovieEntity> movieEntityList = new ArrayList<MovieEntity>();

        for (Movie movie : movies) {
            movieEntityList.add(new MovieEntity(movie));
        }
        return movieEntityList;
    }
    public static List<Movie>toMovies(List<MovieEntity> movieEntities){

        ArrayList<Movie> movieList = new ArrayList<>();

        for (MovieEntity movieEntity : movieEntities) {
            Movie movie = new Movie(
                    movieEntity.getApiId(),
                    movieEntity.getTitle(),
                    stringToGenres(movieEntity.getGenres()),
                    movieEntity.getReleaseYear(),
                    movieEntity.getDescription(),
                    movieEntity.getImgUrl(),
                    movieEntity.getLengthInMinutes(),
                    movieEntity.getRating()
            );
            movieList.add(movie);
        }
        return movieList;
    }
    private static List<Genre> stringToGenres(String genres) {
        List<Genre> genreList = new ArrayList<>();
        if (genres == null || genres.isEmpty()) {
            return genreList;
        }
        String[] genreArray = genres.split(", ");
        for (String genre : genreArray) {
                genreList.add(Genre.valueOf(genre.trim()));
        }
        return genreList;
    }
}
