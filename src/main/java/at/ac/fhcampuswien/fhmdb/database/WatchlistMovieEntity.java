package at.ac.fhcampuswien.fhmdb.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import at.ac.fhcampuswien.fhmdb.models.Genre;

import java.io.Serializable;
import java.util.List;

@DatabaseTable(tableName = "watchlistMovie")
public class WatchlistMovieEntity {
    @DatabaseField(generatedId = true)
    private long id;
    @DatabaseField()
    private String apiId;

    public long getId() {
        return id;
    }
    public void setId(long id) {this.id = id;}

    public String getApiId() {
        return apiId;
    }

    public void setApiId(String apiId) {
        this.apiId = apiId;
    }


    public static String genresToString(List<Genre> genres){
        if (genres.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (Genre genre : genres) {
            sb.append(genre).append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }


    public WatchlistMovieEntity(String apiId) {
        this.apiId = apiId;
    }

    public WatchlistMovieEntity(){}

}
