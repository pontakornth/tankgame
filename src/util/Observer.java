package util;

/**
 * Small version of Observer as java.util.Observer is deprecated.
 * @param <T> Message that can be notified. Use generic to avoid casting.
 */
public interface Observer<T> {
   void onNotify(T message);
}
