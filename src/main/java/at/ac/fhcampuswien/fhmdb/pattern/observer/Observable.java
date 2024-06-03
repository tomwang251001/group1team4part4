package at.ac.fhcampuswien.fhmdb.pattern.observer;

public interface Observable {
    void add(Observer controller);
    void remove(Observer controller);
    void notifyObservers();
}
