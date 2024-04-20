package com.project.sportsnewsbackend;
import com.project.sportsnewsbackend.models.LocalUser;
import com.project.sportsnewsbackend.models.Notification;
import com.project.sportsnewsbackend.models.Stories;
import com.project.sportsnewsbackend.repository.LocalUser.LocalUserRepository;
import com.project.sportsnewsbackend.repository.Notification.NotificationRepository;
import com.project.sportsnewsbackend.repository.Tags.TagsRepository;
import com.project.sportsnewsbackend.service.Notification.NotificationService;
import com.project.sportsnewsbackend.service.Notification.NotificationServiceHelper;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;
public class NotificationServiceTest {


    @Mock
    private NotificationRepository notificationRepository;
    @Mock
    private LocalUserRepository localUserRepository;
    @Mock
    private TagsRepository tagsRepository;
    @Mock
    private NotificationServiceHelper notificationServiceHelper;
    @InjectMocks
    private NotificationService notificationService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateNotificationForUser() {
        // Setup
        LocalUser user = new LocalUser();
        Stories story = new Stories();
        String content = "You have a new story update!";

        // Execute
        notificationService.createNotificationForUser(user, story, content);

        // Verify
        verify(notificationRepository).save(any(Notification.class));
    }

    @Test
    public void testGetNotificationsForUser() {
        // Setup
        Long userId = 1L;

        // Execute
        notificationService.getNotificationsForUser(userId);

        // Verify
        verify(notificationRepository).findByLocalUserId(userId);
    }


}
