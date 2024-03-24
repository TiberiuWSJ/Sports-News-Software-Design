package com.project.sportsnewsbackend.service.Stories;

import com.project.sportsnewsbackend.models.Stories;
import com.project.sportsnewsbackend.repository.Stories.StoriesRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing stories within the sports news platform.
 * Provides methods for CRUD operations on stories, utilizing the {@link StoriesRepository}.
 * This class acts as a bridge between the controller layer and the repository, ensuring
 * that business logic and data access are properly separated.
 *
 * @author Rodanciuc Tiberiu-Gabriel
 */
@Service
public class StoriesService {

    private final StoriesRepository storiesRepository;

    /**
     * Constructs a StoriesService with the necessary {@link StoriesRepository}.
     *
     * @param storiesRepository The repository used for story operations.
     */
    public StoriesService(StoriesRepository storiesRepository) {
        this.storiesRepository = storiesRepository;
    }

    /**
     * Adds a new story to the database.
     *
     * @param story The {@link Stories} entity to add.
     * @return The saved {@link Stories} entity.
     */
    public Stories addStory(Stories story) {
        return storiesRepository.save(story);
    }

    /**
     * Retrieves all stories from the database.
     *
     * @return A list of {@link Stories} entities.
     */
    public List<Stories> getAllStories() {
        return storiesRepository.findAll();
    }

    /**
     * Fetches a single story by its ID.
     *
     * @param id The ID of the story to retrieve.
     * @return An {@link Optional} containing the found story or empty if not found.
     */
    public Optional<Stories> getStoryById(Long id) {
        return storiesRepository.findById(id);
    }

    /**
     * Updates an existing story with new details. If the story with the specified ID
     * does not exist, a {@link RuntimeException} is thrown.
     *
     * @param id The ID of the story to update.
     * @param storyDetails The new details for the story.
     * @return The updated {@link Stories} entity.
     */
    public Stories updateStory(Long id, Stories storyDetails) {
        Stories story = storiesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Story not found for this id :: " + id));
        // Update logic
        return storiesRepository.save(story);
    }

    /**
     * Deletes a story by its ID. If the story is not found, a {@link RuntimeException} is thrown.
     *
     * @param id The ID of the story to delete.
     */
    public void deleteStory(Long id) {
        Stories story = storiesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Story not found for this id :: " + id));
        storiesRepository.delete(story);
    }

    // Additional methods as per business logic can be added here.
}
