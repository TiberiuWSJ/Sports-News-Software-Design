package com.project.sportsnewsbackend.controllers;

import com.project.sportsnewsbackend.DTO.StoryCreationDTO;
import com.project.sportsnewsbackend.DTO.StoryUpdateDTO;
import com.project.sportsnewsbackend.models.Stories;
import com.project.sportsnewsbackend.service.Stories.StoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for managing stories within the sports news platform.
 * <p>
 * This class provides endpoints for CRUD operations on {@link Stories} entities,
 * allowing for the creation, retrieval, updating, and deletion of stories.
 * </p>
 */
@RestController
@RequestMapping("/stories")
public class StoriesController {

    private final StoriesService storiesService;

    /**
     * Constructs the StoriesController with the required StoriesService.
     *
     * @param storiesService The service used for story operations.
     */
    @Autowired
    public StoriesController(StoriesService storiesService) {
        this.storiesService = storiesService;
    }

    /**
     * Retrieves all stories.
     * <p>
     * This endpoint returns a list of all {@link Stories} entities.
     * </p>
     *
     * @return A {@link ResponseEntity} containing a list of {@link Stories} entities and the HTTP status OK.
     */
    @GetMapping
    public ResponseEntity<List<Stories>> getAllStories() {
        List<Stories> stories = storiesService.getAllStories();
        return new ResponseEntity<>(stories, HttpStatus.OK);
    }

    /**
     * Retrieves a single story by its ID.
     * <p>
     * This endpoint returns a specific {@link Stories} entity identified by its ID.
     * </p>
     *
     * @param id The ID of the story to retrieve.
     * @return A {@link ResponseEntity} containing the found {@link Stories} entity or a NOT_FOUND status.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Stories> getStoryById(@PathVariable Long id) {
        return storiesService.getStoryById(id)
                .map(story -> new ResponseEntity<>(story, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Creates a new story.
     * <p>
     * This endpoint creates a new {@link Stories} entity based on the provided {@link StoryCreationDTO}.
     * </p>
     *
     * @param storyDTO The {@link StoryCreationDTO} containing the details for the new story.
     * @return A {@link ResponseEntity} containing the created story entity and the HTTP status CREATED.
     */
    @PostMapping
    public ResponseEntity<Stories> createStory(@RequestBody StoryCreationDTO storyDTO) {
        Stories newStory = storiesService.addStory(storyDTO);
        return new ResponseEntity<>(newStory, HttpStatus.CREATED);
    }

    /**
     * Updates an existing story identified by its ID.
     * <p>
     * This endpoint updates the details of an existing {@link Stories} entity with new information provided in the {@link StoryUpdateDTO}.
     * </p>
     *
     * @param id        The ID of the story to update.
     * @param storyDTO The new details for the story.
     * @return A {@link ResponseEntity} containing the updated story entity or a NOT_FOUND status.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Stories> updateStory(@PathVariable Long id, @RequestBody StoryUpdateDTO storyDTO) {
        try {
            Stories updatedStory = storiesService.updateStory(id, storyDTO);
            return new ResponseEntity<>(updatedStory, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Deletes a story by its ID.
     * <p>
     * This endpoint deletes an existing {@link Stories} entity identified by its ID.
     * </p>
     *
     * @param id The ID of the story to delete.
     * @return A {@link ResponseEntity} indicating the result of the deletion operation.
     * @throws Exception if the deletion process fails.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<List<Stories>> deleteStory(@PathVariable Long id) throws Exception {
        try {
            storiesService.deleteStory(id);
            List<Stories> stories = storiesService.getAllStories();
            return new ResponseEntity<>(stories, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<Stories>> searchStories(@RequestParam("keyword") String keyword) {
        List<Stories> stories = storiesService.searchStories(keyword);
        return ResponseEntity.ok(stories);
    }
}
