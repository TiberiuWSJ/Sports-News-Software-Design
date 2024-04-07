package com.project.sportsnewsbackend.service.LocalUser;

import com.project.sportsnewsbackend.models.LocalUser;
import com.project.sportsnewsbackend.repository.LocalUser.LocalUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing {@link LocalUser} entities.
 * This class provides methods to perform CRUD operations on users within the sports news platform,
 * utilizing the {@link LocalUserRepository} for database interactions.
 *
 * Services offered include finding all users, finding a user by ID, saving a user, and deleting a user by ID.
 * Additional business logic and user management functionalities can be added as needed.
 *
 * @author Rodanciuc Tiberiu-Gabriel
 */
@Service
public class LocalUserService {

    private final LocalUserRepository localUserRepository;

    /**
     * Constructs a new LocalUserService with the given {@link LocalUserRepository}.
     *
     * @param localUserRepository the repository used for user operations.
     */
    @Autowired
    public LocalUserService(LocalUserRepository localUserRepository) {
        this.localUserRepository = localUserRepository;
    }

    /**
     * Retrieves all users from the database.
     *
     * @return a list of {@link LocalUser} entities.
     */
    public List<LocalUser> findAllUsers() {
        return localUserRepository.findAll();
    }

    /**
     * Finds a user by their ID.
     *
     * @param id the ID of the user to find.
     * @return an {@link Optional} containing the found user, or empty if no user is found.
     */
    public Optional<LocalUser> findUserById(Long id) {
        return localUserRepository.findById(id);
    }

    /**
     * Saves a given user to the database. This method can be used for both creating new users
     * and updating existing ones.
     *
     * @param user the {@link LocalUser} entity to save.
     * @return the saved user entity.
     */
    public LocalUser saveUser(LocalUser user) {
        return localUserRepository.save(user);
    }

    /**
     * Deletes a user by their ID.
     *
     * @param id the ID of the user to delete.
     */
    public void deleteUserById(Long id) {
        localUserRepository.deleteById(id);
    }

    public void notifyUser(Long userId, String message) {
        // Logic to send a notification to the user.
        // This could be an email, a WebSocket message, or any other form of user notification.
        System.out.println("Notifying user " + userId + ": " + message);

        // Here you can integrate with your notification mechanism.
        // For example, if you have an EmailService, you could call emailService.sendNotificationEmail(userId, message);
    }

    // More methods can be added as per the business logic requirements.
}
