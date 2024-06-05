package at.ac.fhcampuswien.fhmdb.pattern.observer;

public interface Observable {
    void registerObserver(Observer observer);
    void unregisterObserver(Observer observer);
    void notifyObservers(String msg);
}
