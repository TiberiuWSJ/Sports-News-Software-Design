package com.project.sportsnewsbackend.service.Stories;

import com.project.sportsnewsbackend.DTO.StoryCreationDTO;
import com.project.sportsnewsbackend.DTO.StoryUpdateDTO;
import com.project.sportsnewsbackend.models.LocalUser;
import com.project.sportsnewsbackend.models.Stories;
import com.project.sportsnewsbackend.models.StoryTag;
import com.project.sportsnewsbackend.models.Tags;
import com.project.sportsnewsbackend.repository.LocalUser.LocalUserRepository;
import com.project.sportsnewsbackend.repository.Stories.StoriesRepository;
import com.project.sportsnewsbackend.repository.StoryTag.StoryTagRepository;
import com.project.sportsnewsbackend.repository.Tags.TagsRepository;
import com.project.sportsnewsbackend.service.Notification.NotificationServiceHelper;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * Service for managing stories within the sports news platform.
 * Provides functionality to create, update, delete, and retrieve stories,
 * along with managing their associated tags and author information.
 */
@Service
@Transactional
@Component
public class StoriesService {

    private final StoriesRepository storiesRepository;
    private final TagsRepository tagsRepository;
    private final LocalUserRepository localUserRepository;
    private final StoryTagRepository storyTagRepository;
    @Autowired
    private NotificationServiceHelper notificationServiceHelper;

    @Autowired
    public StoriesService(StoriesRepository storiesRepository, TagsRepository tagsRepository,
                          LocalUserRepository localUserRepository, StoryTagRepository storyTagRepository,
                            NotificationServiceHelper notificationServiceHelper){
        this.storiesRepository = storiesRepository;
        this.tagsRepository = tagsRepository;
        this.localUserRepository = localUserRepository;
        this.storyTagRepository = storyTagRepository;
        this.notificationServiceHelper = notificationServiceHelper;
    }

    /**
     * Adds a new story based on provided information in the StoryCreationDTO.
     * Notifies followers of the story's tags upon successful creation.
     *
     * @param storyDTO The DTO containing the new story's information.
     * @return The newly created story entity.
     */
    @Transactional
    public Stories addStory(StoryCreationDTO storyDTO) {
        LocalUser author = localUserRepository.findById(storyDTO.getAuthorId())
                .orElseThrow(() -> new RuntimeException("Author not found with ID: " + storyDTO.getAuthorId()));

        Stories story = new Stories();
        story.setTitle(storyDTO.getTitle());
        story.setBody(storyDTO.getBody());
        story.setImageURL(storyDTO.getImageURL());
        story.setPublishedDate(storyDTO.getPublishedDate());
        story.setAuthor(author);

        Stories savedStory = storiesRepository.save(story); // Save to generate the ID

        List<Tags> tags = storyDTO.getTagNames().stream()
                .map(this::createOrGetTag)
                .collect(Collectors.toList());

        if (!tags.isEmpty()) {
            if (notificationServiceHelper == null) {
                System.out.println("NotificationServiceHelper is null");
            } else {
                attachTagsToStory(savedStory, tags);

                tags.forEach(tag -> notificationServiceHelper.notifyFollowersOfTag(tag.getTagID(), savedStory));
            }
        }



        return savedStory;
    }

    /**
     * Updates an existing story's information with the details provided in the StoryUpdateDTO.
     *
     * @param id The ID of the story to update.
     * @param storyDTO The DTO containing the updated story information.
     * @return The updated story entity.
     */
    @Transactional
    public Stories updateStory(Long id, StoryUpdateDTO storyDTO) {
        Stories story = storiesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Story not found with ID: " + id));

        story.setTitle(storyDTO.getTitle());
        story.setBody(storyDTO.getBody());
        story.setImageURL(storyDTO.getImageURL());
        story.setPublishedDate(storyDTO.getPublishedDate());

        List<Tags> tags = storyDTO.getTagNames().stream()
                .map(this::createOrGetTag)
                .collect(Collectors.toList());

        clearAndAttachTagsToStory(story, tags);

        return storiesRepository.save(story);
    }

    private Tags createOrGetTag(String tagName) {
        return tagsRepository.findByName(tagName)
                .orElseGet(() -> {
                    Tags tag = new Tags();
                    tag.setName(tagName);
                    return tagsRepository.save(tag);
                });
    }

    private void attachTagsToStory(Stories story, List<Tags> tags) {
        List<StoryTag> storyTags = tags.stream()
                .map(tag -> {
                    StoryTag storyTag = new StoryTag();
                    storyTag.setStory(story);
                    storyTag.setTag(tag);
                    return storyTag;
                })
                .collect(Collectors.toList());

        story.setTags(storyTags);
    }


    private void clearAndAttachTagsToStory(Stories story, List<Tags> newTags) {
        // First, clear the existing tags to remove them
        story.getTags().clear();

        // Then, add the new tags
        for (Tags tag : newTags) {
            StoryTag storyTag = new StoryTag();  // Create a new StoryTag instance
            storyTag.setStory(story);  // Set the story for the StoryTag
            storyTag.setTag(tag);  // Set the tag for the StoryTag
            story.getTags().add(storyTag);  // Add the StoryTag to the story's tags
        }
    }


    /**
     * Retrieves all existing stories.
     *
     * @return A list of all story entities.
     */
    public List<Stories> getAllStories() {
        return storiesRepository.findAll();
    }


    /**
     * Retrieves a specific story by its ID.
     *
     * @param id The ID of the story to retrieve.
     * @return An Optional containing the found story, if available.
     */
    public Optional<Stories> getStoryById(Long id) {
        return storiesRepository.findById(id);
    }


    /**
     * Deletes a story by its ID. If the story is not found, a {@link RuntimeException} is thrown.
     *
     * @param id The ID of the story to delete.
     */
    public void deleteStory(Long storyID) {
        Stories story = storiesRepository.findById(storyID)
                .orElseThrow(() -> new RuntimeException("Story not found"));

        storyTagRepository.deleteAllByStoryId(storyID);

        // Delete related notification
        notificationServiceHelper.deleteByRelatedStory(story);

        // Now delete the story
        storiesRepository.delete(story);
    }

    public List<Stories> searchStories(String keyword) {
        return storiesRepository.searchStories(keyword);
    }





}
