package com.project.sportsnewsbackend.controllers;

import com.project.sportsnewsbackend.models.LocalUser;
import com.project.sportsnewsbackend.models.Notification;
import com.project.sportsnewsbackend.models.Stories;
import com.project.sportsnewsbackend.service.LocalUser.LocalUserService;
import com.project.sportsnewsbackend.service.Notification.NotificationService;
import com.project.sportsnewsbackend.service.Stories.StoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing notifications.
 * Provides endpoints for retrieving and managing user notifications.
 */
@RestController
@RequestMapping("/notifications")
public class NotificationsController {

    private final NotificationService notificationService;
    private final LocalUserService localUserService;
    private final StoriesService storiesService;

    @Autowired
    public NotificationsController(NotificationService notificationService, LocalUserService localUserService, StoriesService storiesService) {
        this.notificationService = notificationService;
        this.localUserService = localUserService;
        this.storiesService = storiesService;
    }

    /**
     * Retrieves all notifications for a specific user.
     *
     * @param userEmail The email of the user whose notifications are to be retrieved.
     * @return A ResponseEntity containing a list of notifications and the HTTP status.
     */
    @GetMapping("/user/{userEmail}")
    public ResponseEntity<List<Notification>> getNotificationsForUser(@PathVariable String userEmail) {
        Optional<LocalUser> user = localUserService.findUserByEmail(userEmail);
        if(user.isPresent()){
            List<Notification> notifications = notificationService.getNotificationsForUser(user.get().getId());
            return new ResponseEntity<>(notifications, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Creates a new notification for a user.
     *
     * @param userId The ID of the user to notify.
     * @param storyId The ID of the story the notification is about.
     * @param content The content of the notification.
     * @return A ResponseEntity containing the created notification and the HTTP status.
     */
    @PostMapping("/user/{userId}/story/{storyId}")
    public ResponseEntity<Notification> createNotificationForUser(@PathVariable Long userId, @PathVariable Long storyId, @RequestBody String content) {
        Optional<LocalUser> userOptional = localUserService.findUserById(userId);
        Optional<Stories> storyOptional = storiesService.getStoryById(storyId);

        if (userOptional.isPresent() && storyOptional.isPresent()) {
            LocalUser user = userOptional.get();
            Stories story = storyOptional.get();
            notificationService.createNotificationForUser(user, story, content);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Deletes a notification by its ID.
     *
     * @param notificationId The ID of the notification to delete.
     * @return A ResponseEntity with the HTTP status.
     */
    @DeleteMapping("/{notificationId}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long notificationId) {
        Optional<Notification> notificationOptional = notificationService.findNotificationById(notificationId);

        if (notificationOptional.isPresent()) {
            notificationService.deleteNotificationById(notificationId);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Uncomment and implement if needed
    // /**
    //  * Marks a notification as read.
    //  *
    //  * @param notificationId The ID of the notification to mark as read.
    //  * @return A ResponseEntity with the HTTP status.
    //  */
    // @PutMapping("/{notificationId}/read")
    // public ResponseEntity<Void> markNotificationAsRead(@PathVariable Long notificationId) {
    //     Optional<Notification> notificationOptional = notificationService.findNotificationById(notificationId);
    //
    //     if (notificationOptional.isPresent()) {
    //         Notification notification = notificationOptional.get();
    //         notification.setRead(true); // Assuming there's a 'read' field in Notification entity
    //         notificationService.save(notification);
    //         return new ResponseEntity<>(HttpStatus.OK);
    //     } else {
    //         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    //     }
    // }
}
