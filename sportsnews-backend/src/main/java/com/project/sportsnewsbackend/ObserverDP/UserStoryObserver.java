package com.project.sportsnewsbackend.ObserverDP;

import com.project.sportsnewsbackend.service.LocalUser.LocalUserService;

public class UserStoryObserver implements Observer {
    private final LocalUserService userService;
    private final Long userId;

    public UserStoryObserver(LocalUserService userService, Long userId) {
        this.userService = userService;
        this.userId = userId;
    }

    @Override
    public void update(Subject subject, Object arg) {
        // Invoke methods on userService to notify the user
        userService.notifyUser(userId, "A new story has been published!");
    }
}