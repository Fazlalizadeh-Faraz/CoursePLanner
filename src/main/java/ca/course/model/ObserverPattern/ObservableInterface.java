package ca.course.model.ObserverPattern;


public interface ObservableInterface {
    boolean addObserver(Watchers observer);

    boolean removeObserver(Watchers observer);

    void postNotification();

}
