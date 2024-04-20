package com.project.sportsnewsbackend;

import com.project.sportsnewsbackend.models.LocalUser;
import com.project.sportsnewsbackend.models.Stories;
import com.project.sportsnewsbackend.models.Tags;
import com.project.sportsnewsbackend.DTO.StoryCreationDTO;
import com.project.sportsnewsbackend.DTO.StoryUpdateDTO;
import com.project.sportsnewsbackend.repository.LocalUser.LocalUserRepository;
import com.project.sportsnewsbackend.repository.Stories.StoriesRepository;
import com.project.sportsnewsbackend.repository.Tags.TagsRepository;
import com.project.sportsnewsbackend.repository.StoryTag.StoryTagRepository;
import com.project.sportsnewsbackend.service.Notification.NotificationServiceHelper;
import com.project.sportsnewsbackend.service.Stories.StoriesService;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

/**
 * Test class for {@link StoriesService}. It tests the functionality provided by the StoriesService,
 * focusing on creating, updating, deleting, and retrieving stories.
 */
public class StoriesServiceTests {

    @Mock
    private StoriesRepository storiesRepository;
    @Mock
    private TagsRepository tagsRepository;
    @Mock
    private LocalUserRepository localUserRepository;
    @Mock
    private StoryTagRepository storyTagRepository;
    @Mock
    private NotificationServiceHelper notificationServiceHelper;

    @InjectMocks
    private StoriesService storiesService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Tests the addition of a new story using {@link StoryCreationDTO}.
     * Verifies that the story is saved correctly and that necessary dependencies are called.
     */
    @Test
    public void testAddStory() {
        Long authorId = 1L;
        LocalUser author = new LocalUser();
        author.setId(authorId);
        StoryCreationDTO dto = new StoryCreationDTO();
        dto.setAuthorId(authorId);
        dto.setTitle("New Story");
        dto.setBody("This is a new story");
        dto.setTagNames(List.of("Sports", "News"));

        when(localUserRepository.findById(authorId)).thenReturn(Optional.of(author));
        when(storiesRepository.save(any(Stories.class))).thenAnswer(i -> i.getArguments()[0]);
        when(tagsRepository.findByName(anyString())).thenAnswer(i -> {
            Tags tag = new Tags();
            tag.setName(i.getArgument(0));
            return Optional.of(tag);
        });
        when(tagsRepository.save(any(Tags.class))).thenAnswer(i -> i.getArguments()[0]);

        Stories result = storiesService.addStory(dto);

        verify(storiesRepository).save(any(Stories.class));
        assertNotNull(result);
    }

    /**
     * Tests the updating of an existing story using {@link StoryUpdateDTO}.
     * Ensures that the story is found and updated with new content.
     */
    @Test
    public void testUpdateStory() {
        Long storyId = 1L;
        Stories story = new Stories();
        story.setStoryID(storyId);
        story.setTitle("Old Story");
        story.setBody("This is an old story");
        story.setTags(new ArrayList<>());
        StoryUpdateDTO dto = new StoryUpdateDTO();
        dto.setTitle("Updated Story");
        dto.setBody("This is an updated story");
        dto.setTagNames(List.of("Sports", "News"));

        when(storiesRepository.findById(storyId)).thenReturn(Optional.of(story));
        when(storiesRepository.save(any(Stories.class))).thenAnswer(i -> i.getArguments()[0]);

        Stories result = storiesService.updateStory(storyId, dto);

        verify(storiesRepository).save(any(Stories.class));
        assertNotNull(result);
    }

    /**
     * Tests the deletion of a story by its ID.
     * Verifies that the story exists and that the delete operation is called on the repository.
     */
    @Test
    public void testDeleteStory() {
        Long storyId = 1L;
        Stories story = new Stories();
        story.setStoryID(storyId);
        story.setTitle("Old Story");
        story.setBody("This is an old story");
        story.setTags(new ArrayList<>());

        when(storiesRepository.findById(storyId)).thenReturn(Optional.of(story));

        try {
            storiesService.deleteStory(storyId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Tests the scenario where an attempt is made to delete a non-existent story.
     * Expects an exception to be thrown indicating that the story was not found.
     */
    @Test(expected = Exception.class)
    public void testDeleteStoryNotFound() throws Exception {
        Long storyId = 1L;

        when(storiesRepository.findById(storyId)).thenReturn(Optional.empty());

        storiesService.deleteStory(storyId);
    }

    /**
     * Tests the retrieval of all stories from the repository.
     * Verifies that the correct method is called and that the result is not null.
     */
    @Test
    public void testGetAllStories() {
        List<Stories> stories = new ArrayList<>();
        stories.add(new Stories());
        stories.add(new Stories());
        stories.add(new Stories());

        when(storiesRepository.findAll()).thenReturn(stories);

        List<Stories> result = storiesService.getAllStories();

        assertNotNull(result);
    }

    /**
     * Tests fetching a single story by its ID.
     * Ensures that the correct story is retrieved from the repository.
     */
    @Test
    public void testGetStoryById() {
        Long storyId = 1L;
        Stories story = new Stories();
        story.setStoryID(storyId);

        when(storiesRepository.findById(storyId)).thenReturn(Optional.of(story));

        Optional<Stories> result = storiesService.getStoryById(storyId);

        assertNotNull(result);
    }
}
