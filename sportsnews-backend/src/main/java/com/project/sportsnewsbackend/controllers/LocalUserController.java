package com.project.sportsnewsbackend.controllers;

import com.project.sportsnewsbackend.models.LocalUser;
import com.project.sportsnewsbackend.service.LocalUser.LocalUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing local users within the sports news platform.
 * Provides endpoints for CRUD operations on {@link LocalUser} entities,
 * allowing for the retrieval, creation, update, and deletion of users.
 *
 * @author Rodanciuc Tiberiu-Gabriel
 */
@RestController
@RequestMapping("/users")
public class LocalUserController {

    private final LocalUserService localUserService;

    /**
     * Constructs the {@link LocalUserController} with the required {@link LocalUserService}.
     *
     * @param localUserService The service used for user operations.
     */
    @Autowired
    public LocalUserController(LocalUserService localUserService) {
        this.localUserService = localUserService;
    }

    /**
     * Retrieves all users.
     *
     * @return A {@link ResponseEntity} containing a list of {@link LocalUser} entities and the HTTP status.
     */
    @GetMapping
    public ResponseEntity<List<LocalUser>> getAllUsers() {
        List<LocalUser> users = localUserService.findAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * Retrieves a single user by their ID.
     *
     * @param id The ID of the user to retrieve.
     * @return A {@link ResponseEntity} containing the found {@link LocalUser} or a NOT_FOUND status.
     */
    @GetMapping("/{id}")
    public ResponseEntity<LocalUser> getUserById(@PathVariable Long id) {
        return localUserService.findUserById(id)
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Creates a new user.
     *
     * @param user The {@link LocalUser} entity to create.
     * @return A {@link ResponseEntity} containing the created user and the HTTP status.
     */
    @PostMapping
    public ResponseEntity<LocalUser> createUser(@RequestBody LocalUser user) {
        LocalUser newUser = localUserService.saveUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    /**
     * Updates an existing user identified by their ID with new details.
     *
     * @param id The ID of the user to update.
     * @param userDetails The new details for the user.
     * @return A {@link ResponseEntity} containing the updated user or a NOT_FOUND status.
     */
    @PutMapping("/{id}")
    public ResponseEntity<LocalUser> updateUser(@PathVariable Long id, @RequestBody LocalUser userDetails) {
        return localUserService.findUserById(id)
                .map(user -> {
                    user.setEmail(userDetails.getEmail());
                    user.setPassword(userDetails.getPassword()); // Ensure you handle password encoding if necessary
                    user.setFirstName(userDetails.getFirstName());
                    user.setLastName(userDetails.getLastName());
                    user.setIsJournalist(userDetails.getIsJournalist());
                    user.setIsModerator(userDetails.getIsModerator());
                    user.setFavoriteTeam(userDetails.getFavoriteTeam());
                    user.setFavoriteSportsman(userDetails.getFavoriteSportsman());
                    // Include other fields as necessary

                    // Save and return the updated user
                    LocalUser updatedUser = localUserService.saveUser(user);
                    return new ResponseEntity<>(updatedUser, HttpStatus.OK);
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Updates the user's favorite tag.
     *
     * @param userId The ID of the user.
     * @param tagId The ID of the tag to set as favorite.
     * @return A {@link ResponseEntity} containing the updated user or a NOT_FOUND status.
     */
    @PutMapping("/{userId}/favoriteTag/{tagId}")
    public ResponseEntity<LocalUser> updateUserFavoriteTag(@PathVariable Long userId, @PathVariable Long tagId) {
        return localUserService.setUserFavoriteTag(userId, tagId)
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Deletes a user by their ID.
     *
     * @param id The ID of the user to delete.
     * @return A {@link ResponseEntity} indicating the result of the deletion operation.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable Long id) {
        try {
            localUserService.deleteUserById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
