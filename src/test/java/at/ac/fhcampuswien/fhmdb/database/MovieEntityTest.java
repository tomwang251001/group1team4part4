package at.ac.fhcampuswien.fhmdb.database;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MovieEntityTest {

    @Test
    void genresToString_Empty_List() {
        List<Genre> genres = new ArrayList<Genre>();

        String result = MovieEntity.genresToString(genres);
        assertEquals("", result);
    }
    void genresToString_List_with_one_item() {
        List<Genre> genres = new ArrayList<Genre>();
        //genres.add(Genre "ACTION");
        String result = MovieEntity.genresToString(genres);
        assertEquals("ACTION", result);
    }
    void genresToString_List_with_more_items() {
        List<Genre> genres = new ArrayList<Genre>();
        /* I dont know what i'm doing here - help, i need sleep
        genres.add(Genre "ACTION");
        genres.add(Genre "DRAMA");
        genres.add(Genre "FAMILY");
         */
        String result = MovieEntity.genresToString(genres);
        assertEquals("ACTION,DRAMA,FAMILY", result);
    }


    @Test
    void fromMovies() {
    }

    @Test
    void toMovies() {
    }
}