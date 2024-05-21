package com.project.sportsnewsbackend.service.Notification;

import com.project.sportsnewsbackend.models.LocalUser;
import com.project.sportsnewsbackend.models.Notification;
import com.project.sportsnewsbackend.models.Stories;
import com.project.sportsnewsbackend.repository.LocalUser.LocalUserRepository;
import com.project.sportsnewsbackend.repository.Notification.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


/**
 * Service responsible for sending notifications to users about new stories related to their interests.
 * This service focuses on notifying users who follow specific tags whenever a new story tagged with those tags is published.
 */
@Service
public class NotificationService {

    /**
     * The repository for managing notifications.
     */
    private final NotificationRepository notificationRepository;
    private final LocalUserRepository localUserRepository;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository, LocalUserRepository localUserRepository) {
        this.notificationRepository = notificationRepository;
        this.localUserRepository = localUserRepository;
    }

    /**
     * Creates a notification for a user based on the story they should be notified about.
     *
     * @param user The user to notify.
     * @param story The story the notification is about.
     * @param content The content of the notification.
     */
    public void createNotificationForUser(LocalUser user, Stories story, String content) {
        Notification notification = new Notification();
        notification.setLocalUser(user);
        notification.setRelatedStory(story);
        notification.setContent(content);
        // Set any other necessary fields, like read status or timestamp
        notificationRepository.save(notification);
    }

    /**
     * Retrieves all notifications for a specific user.
     *
     * @param userId The ID of the user whose notifications are to be retrieved.
     * @return A list of Notifications.
     */
    public List<Notification> getNotificationsForUser(Long userId) {
        // This method assumes you have a custom query defined in NotificationRepository
        // to fetch notifications by user ID
        return notificationRepository.findByLocalUserId(userId);
    }

    public Optional<Notification> findNotificationById(Long id) {
        return notificationRepository.findById(id);
    }

    public void deleteNotificationById(Long id) {
        notificationRepository.deleteById(id);
    }
}
