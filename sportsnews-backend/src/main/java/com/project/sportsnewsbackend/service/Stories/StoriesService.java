package com.project.sportsnewsbackend.service.Stories;

import com.project.sportsnewsbackend.models.Stories;
import com.project.sportsnewsbackend.repository.Stories.StoriesRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StoriesService {

    private final StoriesRepository storiesRepository;

    public StoriesService(StoriesRepository storiesRepository) {
        this.storiesRepository = storiesRepository;
    }

    // Add a new story
    public Stories addStory(Stories story) {
        return storiesRepository.save(story);
    }

    // Fetch all stories
    public List<Stories> getAllStories() {
        return storiesRepository.findAll();
    }

    // Fetch a single story by its id
    public Optional<Stories> getStoryById(Long id) {
        return storiesRepository.findById(id);
    }

    // Update a story
    public Stories updateStory(Long id, Stories storyDetails) {
        Stories story = storiesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Story not found for this id :: " + id));

        story.setTitle(storyDetails.getTitle());
        story.setBody(storyDetails.getBody());
        story.setImageURL(storyDetails.getImageURL());
        story.setPublishedDate(storyDetails.getPublishedDate());
        story.setAuthor(storyDetails.getAuthor());
        story.setTags(storyDetails.getTags());
        story.setUser(storyDetails.getUser());
        // Assume other necessary updates are handled similarly

        return storiesRepository.save(story);
    }

    // Delete a story
    public void deleteStory(Long id) {
        Stories story = storiesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Story not found for this id :: " + id));

        storiesRepository.delete(story);
    }
}
