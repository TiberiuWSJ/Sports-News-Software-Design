package com.project.sportsnewsbackend;

import com.project.sportsnewsbackend.models.LocalUser;
import com.project.sportsnewsbackend.models.Notification;
import com.project.sportsnewsbackend.models.Stories;
import com.project.sportsnewsbackend.models.Tags;
import com.project.sportsnewsbackend.repository.LocalUser.LocalUserRepository;
import com.project.sportsnewsbackend.repository.Notification.NotificationRepository;
import com.project.sportsnewsbackend.repository.Tags.TagsRepository;
import com.project.sportsnewsbackend.service.Notification.NotificationServiceHelper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class NotificationServiceHelperTest {

    @Mock
    private LocalUserRepository localUserRepository;
    @Mock
    private TagsRepository tagsRepository;
    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private NotificationServiceHelper notificationServiceHelper;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testNotifyFollowersOfTag() {
        Long tagId = 1L;
        Stories newStory = new Stories();
        newStory.setTitle("Exciting News");
        LocalUser user1 = new LocalUser();
        user1.setEmail("user1@example.com");
        LocalUser user2 = new LocalUser();
        user2.setEmail("user2@example.com");

        Tags tag = new Tags();
        tag.setFollowers(Arrays.asList(user1, user2));

        when(tagsRepository.findById(tagId)).thenReturn(Optional.of(tag));

        notificationServiceHelper.notifyFollowersOfTag(tagId, newStory);

        verify(tagsRepository).findById(tagId);
        verify(notificationRepository, times(2)).save(any(Notification.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNotifyFollowersOfTag_TagNotFound() {
        Long tagId = 1L;
        Stories newStory = new Stories();

        when(tagsRepository.findById(tagId)).thenReturn(Optional.empty());

        notificationServiceHelper.notifyFollowersOfTag(tagId, newStory);
    }

    @Test
    public void testSendNotification() {
        LocalUser user = new LocalUser();
        user.setEmail("user@example.com");
        Stories story = new Stories();
        story.setTitle("Breaking News");

        notificationServiceHelper.sendNotification(user, story);

        verify(notificationRepository).save(any(Notification.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSendNotification_NullUser() {
        Stories story = new Stories();
        story.setTitle("Important Update");

        notificationServiceHelper.sendNotification(null, story);
    }
}
