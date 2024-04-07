package com.project.sportsnewsbackend.service;

import com.project.sportsnewsbackend.ObserverDP.Observer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Servicegene
public class StoryNotificationService {
    private final Map<Long, List<Observer>> storyObservers = new ConcurrentHashMap<>();

    public void registerObserver(Long storyId, Observer observer) {
        List<Observer> observers = storyObservers.computeIfAbsent(storyId, k -> new CopyOnWriteArrayList<>());
        observers.add(observer);
    }

    public void removeObserver(Long storyId, Observer observer) {
        List<Observer> observers = storyObservers.getOrDefault(storyId, new CopyOnWriteArrayList<>());
        observers.remove(observer);
    }

    public void notifyObservers(Long storyId) {
        // Notify all observers interested in the new story
        // This assumes each user is interested in any new story
        // You might need to adjust logic here depending on your use case
        storyObservers.values().stream().flatMap(List::stream).forEach(observer -> observer.update(null, storyId));
    }
}

