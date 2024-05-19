package com.project.sportsnewsbackend.repository.Notification;

import com.project.sportsnewsbackend.models.LocalUser;
import com.project.sportsnewsbackend.models.Notification;
import com.project.sportsnewsbackend.models.Stories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByLocalUserId(Long userId);
    void deleteByRelatedStory(Stories relatedStory);

    void deleteAllByLocalUser(LocalUser user);
}