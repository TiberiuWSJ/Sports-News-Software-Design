package com.project.sportsnewsbackend.controllers;

import com.project.sportsnewsbackend.models.LocalUser;
import com.project.sportsnewsbackend.models.Stories;
import com.project.sportsnewsbackend.service.LocalUser.LocalUserService;
import com.project.sportsnewsbackend.service.Stories.StoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/favorites")
public class FavoritesController {

    private final LocalUserService localUserService;
    private final StoriesService storiesService;

    @Autowired
    public FavoritesController(LocalUserService localUserService, StoriesService storiesService) {
        this.localUserService = localUserService;
        this.storiesService = storiesService;
    }

    /**
     * Retrieves all favorite stories for a specific user.
     *
     * @param userId The ID of the user whose favorite stories are to be retrieved.
     * @return A ResponseEntity containing a list of favorite stories and the HTTP status.
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Stories>> getUserFavorites(@PathVariable Long userId) {
        Optional<LocalUser> userOptional = localUserService.findUserById(userId);
        if (userOptional.isPresent()) {
            List<Stories> favorites = userOptional.get().getSavedStories();
            return new ResponseEntity<>(favorites, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Adds a story to a user's favorites.
     *
     * @param userId  The ID of the user.
     * @param storyId The ID of the story to be added to favorites.
     * @return A ResponseEntity with the HTTP status.
     */
    @PostMapping("/user/{userId}/story/{storyId}")
    public ResponseEntity<Void> addFavoriteStory(@PathVariable Long userId, @PathVariable Long storyId) {
        Optional<LocalUser> userOptional = localUserService.findUserById(userId);
        Optional<Stories> storyOptional = storiesService.getStoryById(storyId);

        if (userOptional.isPresent() && storyOptional.isPresent()) {
            LocalUser user = userOptional.get();
            Stories story = storyOptional.get();
            user.getSavedStories().add(story);
            localUserService.saveUser(user);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Removes a story from a user's favorites.
     *
     * @param userId  The ID of the user.
     * @param storyId The ID of the story to be removed from favorites.
     * @return A ResponseEntity with the HTTP status.
     */
    @DeleteMapping("/user/{userId}/story/{storyId}")
    public ResponseEntity<Void> removeFavoriteStory(@PathVariable Long userId, @PathVariable Long storyId) {
        Optional<LocalUser> userOptional = localUserService.findUserById(userId);
        Optional<Stories> storyOptional = storiesService.getStoryById(storyId);

        if (userOptional.isPresent() && storyOptional.isPresent()) {
            LocalUser user = userOptional.get();
            Stories story = storyOptional.get();
            user.getSavedStories().remove(story);
            localUserService.saveUser(user);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
