package com.project.sportsnewsbackend.controllers;

import com.project.sportsnewsbackend.models.LocalUser;
import com.project.sportsnewsbackend.service.LocalUser.LocalUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class LocalUserController {

    private final LocalUserService localUserService;

    @Autowired
    public LocalUserController(LocalUserService localUserService) {
        this.localUserService = localUserService;
    }

    // GET endpoint to retrieve all users
    @GetMapping
    public ResponseEntity<List<LocalUser>> getAllUsers() {
        List<LocalUser> users = localUserService.findAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    // GET endpoint to retrieve a single user by ID
    @GetMapping("/{id}")
    public ResponseEntity<LocalUser> getUserById(@PathVariable Long id) {
        return localUserService.findUserById(id)
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // POST endpoint to create a new user
    @PostMapping
    public ResponseEntity<LocalUser> createUser(@RequestBody LocalUser user) {
        LocalUser newUser = localUserService.saveUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    // PUT endpoint to update an existing user
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
                    // If there are any other fields that need to be updated, include them here

                    // After setting all the fields, save the updated user
                    LocalUser updatedUser = localUserService.saveUser(user);

                    // Return the updated user
                    return new ResponseEntity<>(updatedUser, HttpStatus.OK);
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // DELETE endpoint to delete a user
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
