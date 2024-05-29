package com.project.sportsnewsbackend.service.LocalUser;

import com.project.sportsnewsbackend.models.LocalUser;
import com.project.sportsnewsbackend.models.Stories;
import com.project.sportsnewsbackend.models.Tags;
import com.project.sportsnewsbackend.repository.LocalUser.LocalUserRepository;
import com.project.sportsnewsbackend.repository.Notification.NotificationRepository;
import com.project.sportsnewsbackend.repository.Stories.StoriesRepository;
import com.project.sportsnewsbackend.repository.Tags.TagsRepository;
import jakarta.transaction.Transactional;
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
    private final NotificationRepository notificationRepository;
    private final StoriesRepository storiesRepository;
    /**
     * Constructs a new LocalUserService with the given {@link LocalUserRepository}.
     *
     * @param localUserRepository the repository used for user operations.
     * @param tagsRepository
     */
    @Autowired
    public LocalUserService(LocalUserRepository localUserRepository, TagsRepository tagsRepository, NotificationRepository notificationRepository, StoriesRepository storiesRepository) {
        this.localUserRepository = localUserRepository;
        this.tagsRepository = tagsRepository;
        this.notificationRepository = notificationRepository;
        this.storiesRepository = storiesRepository;
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
        Optional<LocalUser> existingUser = localUserRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent() && !existingUser.get().getId().equals(user.getId())) {
            throw new IllegalStateException("Email already in use.");
        }
        Optional<LocalUser> existingNameUser = localUserRepository.findByFirstNameAndLastName(user.getFirstName(), user.getLastName());
        if (existingNameUser.isPresent() && !existingNameUser.get().getId().equals(user.getId())) {
            throw new IllegalStateException("A user with the same first and last name already exists.");
        }
        return localUserRepository.save(user);
    }


    /**
     * Deletes a user by their ID.
     *
     * @param id the ID of the user to delete.
     */
    @Transactional
    public void deleteUserById(Long id) {
        LocalUser user = localUserRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));

        // Remove all notifications for this user
        notificationRepository.deleteAllByLocalUser(user);

        // Remove the user
        localUserRepository.delete(user);
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

    public Optional<LocalUser> findUserByEmail(String email) {
        return localUserRepository.findByEmail(email);
    }

    public boolean addStoryToFavorites(Long userId, Long storyId) {
        Optional<LocalUser> userOptional = localUserRepository.findById(userId);
        Optional<Stories> storyOptional = storiesRepository.findById(storyId);

        if (userOptional.isPresent() && storyOptional.isPresent()) {
            LocalUser user = userOptional.get();
            Stories story = storyOptional.get();
            user.getSavedStories().add(story);
            localUserRepository.save(user);
            return true;
        }
        return false;
    }


}
