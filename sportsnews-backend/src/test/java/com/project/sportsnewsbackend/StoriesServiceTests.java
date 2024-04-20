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
import com.project.sportsnewsbackend.service.Stories.StoriesService;
import com.project.sportsnewsbackend.service.Notification.NotificationServiceHelper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

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
        this.storiesService = new StoriesService(storiesRepository, tagsRepository, localUserRepository, storyTagRepository, notificationServiceHelper);
    }

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

    @Test
    public void testDeleteStoryNotFound() {
        Long storyId = 1L;

        when(storiesRepository.findById(storyId)).thenReturn(Optional.empty());

        try {
            storiesService.deleteStory(storyId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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

    @Test
    public void testGetStoryById() {
        Long storyId = 1L;
        Stories story = new Stories();
        story.setStoryID(storyId);
        story.setTitle("Old Story");
        story.setBody("This is an old story");
        story.setTags(new ArrayList<>());

        when(storiesRepository.findById(storyId)).thenReturn(Optional.of(story));

        Optional<Stories> result = storiesService.getStoryById(storyId);

        assertNotNull(result);
    }

}
