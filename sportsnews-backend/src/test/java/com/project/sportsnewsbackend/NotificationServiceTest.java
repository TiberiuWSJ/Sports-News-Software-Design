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

/**
 * Unit tests for {@link NotificationService} class.
 * These tests verify the behavior of the notification creation and retrieval functionalities.
 */
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

    /**
     * Sets up the test environment before each test method.
     * Initializes Mockito annotations for mocking dependencies.
     */
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Tests the {@link NotificationService#createNotificationForUser} method to ensure it correctly creates
     * and saves a notification using the provided LocalUser, story, and content.
     */
    @Test
    public void testCreateNotificationForUser() {
        // Setup
        LocalUser user = new LocalUser();
        Stories story = new Stories();
        String content = "You have a new story update!";

        // Execute
        notificationService.createNotificationForUser(user, story, content);

        // Verify that the notification repository's save method is called once with any notification.
        verify(notificationRepository).save(any(Notification.class));
    }

    /**
     * Tests the {@link NotificationService#getNotificationsForUser} method to ensure it correctly
     * retrieves notifications for a specific user based on the user's ID.
     */
    @Test
    public void testGetNotificationsForUser() {
        // Setup
        Long userId = 1L;

        // Execute
        notificationService.getNotificationsForUser(userId);

        // Verify that the notification repository's findByLocalUserId method is called with the correct ID.
        verify(notificationRepository).findByLocalUserId(userId);
    }
}
