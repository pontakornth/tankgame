package util;


/**
 * Small version of observable since java.util.Observable is deprecated.
 * @param <T> Type of message to avoid casting.
 */
public interface Observable<T> {
    void addObservers(Observer<T> observer);
    void notifyObservers(T message);
}
