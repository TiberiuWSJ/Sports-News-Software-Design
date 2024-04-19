package com.project.sportsnewsbackend.service.Notification;

import com.project.sportsnewsbackend.models.LocalUser;
import com.project.sportsnewsbackend.models.Notification;
import com.project.sportsnewsbackend.models.Stories;
import com.project.sportsnewsbackend.repository.LocalUser.LocalUserRepository;
import com.project.sportsnewsbackend.repository.Notification.NotificationRepository;
import com.project.sportsnewsbackend.repository.Tags.TagsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service responsible for sending notifications to users about new stories related to their interests.
 * This service focuses on notifying users who follow specific tags whenever a new story tagged with those tags is published.
 */
@Service
public class NotificationServiceHelper {

    private final LocalUserRepository localUserRepository;
    private final TagsRepository tagsRepository;
    private final NotificationRepository notificationRepository;

    /**
     * Constructs a NotificationServiceHelper with necessary dependencies.
     *
     * @param localUserRepository    Repository for accessing {@link LocalUser} entities.
     * @param tagsRepository         Repository for accessing {@link Tags} entities.
     * @param notificationRepository Repository for accessing {@link Notification} entities;
     */
    @Autowired
    public NotificationServiceHelper(LocalUserRepository localUserRepository, TagsRepository tagsRepository, NotificationRepository notificationRepository) {
        this.localUserRepository = localUserRepository;
        this.tagsRepository = tagsRepository;
        this.notificationRepository = notificationRepository;
    }

    /**
     * Notifies all users following a specific tag about a new story associated with that tag.
     *
     * This method retrieves all followers of the specified tag and sends them a notification
     * about the new story. The method of notification can vary depending on the implementation
     * details (e.g., email, push notification).
     *
     * @param tagId The ID of the tag for which followers will be notified.
     * @param newStory The {@link Stories} entity about which the followers are being notified.
     */
    public void notifyFollowersOfTag(Long tagId, Stories newStory) {
        // Find all users following this tag
        List<LocalUser> followers = tagsRepository.findById(tagId)
                .orElseThrow(() -> new IllegalArgumentException("Tag not found with ID: " + tagId))
                .getFollowers();

        for (LocalUser follower : followers) {
            sendNotification(follower, newStory);
        }
    }

    /**
     * Sends a notification to a specific user about a new story.
     *
     * This method encapsulates the logic for sending a notification to a user. The actual implementation
     * of how the notification is sent (e.g., via email, SMS, push notification) is abstracted away and
     * can be customized based on requirements.
     *
     * @param user The {@link LocalUser} to whom the notification will be sent.
     * @param story The {@link Stories} entity about which the user is being notified.
     */
    private void sendNotification(LocalUser user, Stories story) {
        // Implement your notification logic here
        // For example, send an email to the user with the new story details
        System.out.println("Notifying " + user.getEmail() + " about new story: " + story.getTitle());
        Notification notification = new Notification();
        notification.setLocalUser(user);
        notification.setRelatedStory(story);
        notification.setContent("A new story has been posted on one of the tags you've followed: " + story.getTitle());
        notificationRepository.save(notification);

    }
}
