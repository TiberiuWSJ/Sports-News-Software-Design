package com.project.sportsnewsbackend.service.LocalUser;

import com.project.sportsnewsbackend.models.LocalUser;
import com.project.sportsnewsbackend.models.Tags;
import com.project.sportsnewsbackend.repository.LocalUser.LocalUserRepository;
import com.project.sportsnewsbackend.repository.Tags.TagsRepository;
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
    private final TagsRepository tagsRepository;

    /**
     * Constructs a new LocalUserService with the given {@link LocalUserRepository}.
     *
     * @param localUserRepository the repository used for user operations.
     * @param tagsRepository
     */
    @Autowired
    public LocalUserService(LocalUserRepository localUserRepository, TagsRepository tagsRepository) {
        this.localUserRepository = localUserRepository;
        this.tagsRepository = tagsRepository;
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
        // Check if the email already exists
        if (localUserRepository.existsByEmail(user.getEmail())) {
            throw new IllegalStateException("Email already in use.");
        }
        // Check if the combination of first name and last name already exists
        if (localUserRepository.existsByFirstNameAndLastName(user.getFirstName(), user.getLastName())) {
            throw new IllegalStateException("A user with the same first and last name already exists.");
        }
        // Save the new user if no conflicts
        return localUserRepository.save(user);
        //return localUserRepository.save(user);
    }

    /**
     * Deletes a user by their ID.
     *
     * @param id the ID of the user to delete.
     */
    public void deleteUserById(Long id) {
        // Check if the user exists before deleting
        if (!localUserRepository.existsById(id)) {
            throw new IllegalArgumentException("User not found with ID: " + id);
        }
        localUserRepository.deleteById(id);
    }

    public Optional<LocalUser> setUserFavoriteTag(Long userId, Long tagId) {
        // First, try to find the user
        Optional<LocalUser> userOpt = localUserRepository.findById(userId);
        if (userOpt.isPresent()) {
            // Only if the user is found, then look for the tag
            Optional<Tags> tagOpt = tagsRepository.findById(tagId);
            if (tagOpt.isPresent()) {
                LocalUser user = userOpt.get();
                Tags tag = tagOpt.get();

                // Set the tag to the user and save
                user.setFollowedTag(tag);
                localUserRepository.save(user);
                return Optional.of(user);
            }
        }
        // If the user isn't found, or the tag isn't found, return empty
        return Optional.empty();
    }

    public LocalUser authenticateUser(String email, String password) {
        return localUserRepository.findByEmailAndPassword(email, password);
    }

}
