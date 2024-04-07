package com.project.sportsnewsbackend.controllers;

import com.project.sportsnewsbackend.DTO.StoryCreationDTO;
import com.project.sportsnewsbackend.DTO.StoryUpdateDTO;
import com.project.sportsnewsbackend.models.Stories;
import com.project.sportsnewsbackend.service.Stories.StoriesService;
import com.project.sportsnewsbackend.service.StoryNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stories")
public class StoriesController {

    private final StoriesService storiesService;

    @Autowired
    public StoriesController(StoriesService storiesService) {
        this.storiesService = storiesService;
    }

    @GetMapping
    public ResponseEntity<List<Stories>> getAllStories() {
        List<Stories> stories = storiesService.getAllStories();
        return new ResponseEntity<>(stories, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Stories> getStoryById(@PathVariable Long id) {
        return storiesService.getStoryById(id)
                .map(story -> new ResponseEntity<>(story, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Stories> createStory(@RequestBody StoryCreationDTO storyDTO) {
        Stories newStory = storiesService.addStory(storyDTO);
        notificationService.notifyObservers(newStory.getStoryID());
        return new ResponseEntity<>(newStory, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Stories> updateStory(@PathVariable Long id, @RequestBody StoryUpdateDTO storyDTO) {
        try {
            Stories updatedStory = storiesService.updateStory(id, storyDTO);
            System.out.println(updatedStory);
            return new ResponseEntity<>(updatedStory, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStory(@PathVariable Long id) throws Exception {
        try {
            storiesService.deleteStory(id);
        } catch (Exception e) {
            throw new Exception("Story not found with ID: " + id + e.getMessage());
        }
    }

    @Autowired
    private StoryNotificationService notificationService;
}
