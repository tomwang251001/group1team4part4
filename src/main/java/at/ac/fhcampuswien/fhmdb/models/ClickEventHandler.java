package at.ac.fhcampuswien.fhmdb.models;

import at.ac.fhcampuswien.fhmdb.database.WatchlistMovieEntity;

@FunctionalInterface
public interface ClickEventHandler<T> {
     void onClick(T t);
}
