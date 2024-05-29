package com.project.sportsnewsbackend.controllers;

import com.project.sportsnewsbackend.models.LocalUser;
import com.project.sportsnewsbackend.models.Stories;
import com.project.sportsnewsbackend.service.LocalUser.LocalUserService;
import com.project.sportsnewsbackend.service.Tags.TagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    private final TagsService tagsService;

    /**
     * Constructs the {@link LocalUserController} with the required {@link LocalUserService}.
     *
     * @param localUserService The service used for user operations.
     */
    @Autowired
    public LocalUserController(LocalUserService localUserService, TagsService tagsService) {
        this.localUserService = localUserService;
        this.tagsService = tagsService;
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
    public ResponseEntity<?> createUser(@RequestBody LocalUser user) {
        try {
            LocalUser newUser = localUserService.saveUser(user);
            System.out.println(newUser.getEmail() + " " + newUser.getPassword() + " " + newUser.getFirstName() + " " +newUser.getLastName());
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            // Return a response entity with a client-friendly error message and a 409 Conflict status
            System.out.println(user.getEmail() + " " + user.getPassword() + " " + user.getFirstName() + " " +user.getLastName());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            // General error handling for other exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing your request.");
        }
//        LocalUser newUser = localUserService.saveUser(user);
//        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    /**
     * Updates an existing user identified by their ID with new details.
     *
     * @param id The ID of the user to update.
     * @param userDetails The new details for the user.
     * @return A {@link ResponseEntity} containing the updated user or a NOT_FOUND status.
     */
    @PutMapping("/{id}")
    public ResponseEntity<LocalUser> updateUser(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        return localUserService.findUserById(id)
                .map(user -> {
                    updates.forEach((key, value) -> {
                        switch (key) {
                            case "email":
                                user.setEmail((String) value);
                                break;
                            case "password":
                                user.setPassword((String) value); // Ensure you handle password encoding if necessary
                                break;
                            case "firstName":
                                user.setFirstName((String) value);
                                break;
                            case "lastName":
                                user.setLastName((String) value);
                                break;
                            case "isJournalist":
                                user.setIsJournalist((Boolean) value);
                                break;
                            case "isModerator":
                                user.setIsModerator((Boolean) value);
                                break;
                            case "favoriteTeam":
                                user.setFavoriteTeam((String) value);
                                tagsService.getAllTags().stream()
                                        .filter(tag -> tag.getName().equals(value))
                                        .findFirst()
                                        .ifPresent(user::setFollowedTag);
                                break;
                            case "favoriteSportsman":
                                user.setFavoriteSportsman((String) value);
                                tagsService.getAllTags().stream()
                                        .filter(tag -> tag.getName().equals(value))
                                        .findFirst()
                                        .ifPresent(user::setFollowedTag);
                                break;
                        }
                    });
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
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");

        LocalUser authenticatedUser = localUserService.authenticateUser(email, password);
        if (authenticatedUser != null) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "Login successful");
            responseBody.put("userId", authenticatedUser.getId());
            responseBody.put("firstName", authenticatedUser.getFirstName());
            responseBody.put("lastName", authenticatedUser.getLastName());
            responseBody.put("userEmail", authenticatedUser.getEmail());
            responseBody.put("isModerator", authenticatedUser.getIsModerator()); // include isModerator
            return ResponseEntity.ok(responseBody);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
    }

    /**
     * Retrieves a single user by their email.
     *
     * @param email The email of the user to retrieve.
     * @return A {@link ResponseEntity} containing the found {@link LocalUser} or a NOT_FOUND status.
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<LocalUser> getUserByEmail(@PathVariable String email) {
        System.out.println(email);
        return localUserService.findUserByEmail(email)
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }



}
