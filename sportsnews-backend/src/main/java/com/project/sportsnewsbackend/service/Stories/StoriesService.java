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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@Component
public class StoriesService {

    private final StoriesRepository storiesRepository;
    private final TagsRepository tagsRepository;
    private final LocalUserRepository localUserRepository;
    private final StoryTagRepository storyTagRepository;

    @Autowired
    public StoriesService(StoriesRepository storiesRepository, TagsRepository tagsRepository,
                          LocalUserRepository localUserRepository, StoryTagRepository storyTagRepository) {
        this.storiesRepository = storiesRepository;
        this.tagsRepository = tagsRepository;
        this.localUserRepository = localUserRepository;
        this.storyTagRepository = storyTagRepository;
    }

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

        attachTagsToStory(savedStory, tags);

        return savedStory;
    }

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



    public List<Stories> getAllStories() {
        return storiesRepository.findAll();
    }


    public Optional<Stories> getStoryById(Long id) {
        return storiesRepository.findById(id);
    }


    /**
     * Deletes a story by its ID. If the story is not found, a {@link RuntimeException} is thrown.
     *
     * @param id The ID of the story to delete.
     */

    public void deleteStory(Long id) throws Exception {
        Stories story = storiesRepository.findById(id).orElse(null);
        if (story != null) {
            storyTagRepository.deleteAllByStoryId(id);
            storiesRepository.delete(story);
        }else{
            throw new Exception("Something hasn't gone well!");
        }
    }


}
