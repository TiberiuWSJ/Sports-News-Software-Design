package com.project.sportsnewsbackend.ObserverDP;

public interface Subject {
    void registerObserver(Observer o);
    void removeObserver(Observer o);
    void notifyObservers();
}
